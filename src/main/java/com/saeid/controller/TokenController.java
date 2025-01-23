package com.saeid.controller;

import com.saeid.model.ClientDto;
import com.saeid.model.TokenDto;
import com.saeid.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class TokenController {

    TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenDto> getToken(@Validated @RequestBody ClientDto client, @RequestParam String username, @RequestParam String password) {
        TokenDto tokenDto = tokenService.generateToken(client.getClientId(), client.getClientSecret(), username, password);
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
        TokenDto tokenResponse = tokenService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        boolean isValid = tokenService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/revoke")
    public ResponseEntity<String> revokeToken(@RequestParam String token) {
        tokenService.revokeToken(token);
        return ResponseEntity.ok("Token revoked");
    }
}
