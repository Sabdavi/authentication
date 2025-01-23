package com.saeid.model;

import java.time.Instant;

public class TokenDto {
    private final String token;
    private final Instant tokenExpireAt;
    private final String refreshToken;
    private final Instant refreshTokenExpireAt;
    private final Instant createdAt;


    public TokenDto(String token, Instant tokenExpireAt,
                    String refreshToken,
                    Instant refreshTokenExpireAt,
                    Instant createdAt ) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.tokenExpireAt = tokenExpireAt;
        this.refreshTokenExpireAt = refreshTokenExpireAt;
        this.createdAt = createdAt;
    }

    public String getToken() {
        return token;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getTokenExpireAt() {
        return tokenExpireAt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Instant getRefreshTokenExpireAt() {
        return refreshTokenExpireAt;
    }
}
