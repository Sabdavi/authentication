package com.saeid.service;

import com.saeid.Entity.Client;
import com.saeid.Entity.Token;
import com.saeid.Entity.User;
import com.saeid.model.TokenDto;
import com.saeid.repository.ClientRepository;
import com.saeid.repository.TokenRepository;
import com.saeid.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final ClientService clientService;
    private final PasswordEncoder tokenEncoder;


    public TokenService(ClientService clientService,
                        TokenRepository tokenRepository,
                        UserService userService,
                        PasswordEncoder tokenEncoder) {
        this.tokenRepository = tokenRepository;
        this.userService = userService;
        this.clientService = clientService;
        this.tokenEncoder = tokenEncoder;
    }

    public TokenDto generateToken(String clientId, String clientSecret, String username, String password) {

        Client client = clientService.verifyClient(clientId, clientSecret);
        User user = userService.verifyUser(username, password);

        Instant now = Instant.now();
        Token newToken = new Token();
        newToken.setToken(tokenEncoder.encode(UUID.randomUUID().toString()));
        newToken.setExpiresAt(Instant.now().plusMillis(client.getDuration()));
        newToken.setRefreshToken(tokenEncoder.encode(UUID.randomUUID().toString()));
        newToken.setRefreshTokenExpiresAt(Instant.now().plusMillis(client.getRefreshTokenDuration()));
        newToken.setUser(user);
        newToken.setCreatedAt(now);

        newToken.setRevoked(false);
        newToken.setClient(client);
        tokenRepository.save(newToken);
        return newToken.toDto();
    }

    public TokenDto refreshAccessToken(String rawRefreshToken) {

        Token refreshToken = tokenRepository.
                findByRefreshToken(rawRefreshToken)
                .orElseThrow(() -> new SecurityException("Invalid refresh token"));

        if (refreshToken.isRevoked() || refreshToken.getRefreshTokenExpiresAt().isBefore(Instant.now())) {
            throw new SecurityException("Refresh token is expired or revoked");
        }

        refreshToken.setToken(tokenEncoder.encode(UUID.randomUUID().toString()));
        refreshToken.setRefreshToken(tokenEncoder.encode(UUID.randomUUID().toString()));
        refreshToken.setExpiresAt(Instant.now().plusMillis(refreshToken.getClient().getDuration()));
        refreshToken.setRefreshTokenExpiresAt(Instant.now().plusMillis(refreshToken.getClient().getRefreshTokenDuration()));

        tokenRepository.save(refreshToken);

        return refreshToken.toDto();
    }

    public boolean validateToken(String token) {
        Optional<Token> userToken = tokenRepository.findByToken(tokenEncoder.encode(token));
        if (userToken.isEmpty()) {
            throw new SecurityException("Token does not exist");
        } else {
            Token token1 = userToken.get();
            if (token1.isRevoked() || token1.getExpiresAt().isBefore(Instant.now())) {
                throw new SecurityException("Token is revoked or expired");
            }
            return true;
        }
    }

    public void revokeToken(String token) {
        Token userToken = tokenRepository.findByToken(token).orElseThrow(() -> new SecurityException("Token does not exist"));
        userToken.setRevoked(true);
        tokenRepository.save(userToken);
    }
}


