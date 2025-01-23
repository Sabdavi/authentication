package com.saeid.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class ClientDto {

    @NotNull
    private final String clientId;
    @NotNull
    private final String clientSecret;
    private final String clientName;
    private final Long tokenDuration;
    private final Long refreshTokenDuration;
    private final Set<String> scopes;


    @JsonCreator
    public ClientDto(String clientId, String clientSecret, String clientName,
                     Long tokenDuration, Long refreshTokenDuration, Set<String> scopes) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.clientName = clientName;
        this.tokenDuration = tokenDuration;
        this.refreshTokenDuration = refreshTokenDuration;
        this.scopes = scopes;
    }

    public String getClientId() {
        return clientId;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public String getClientName() {
        return clientName;
    }
    public Long getTokenDuration() {
        return tokenDuration;
    }
    public Long getRefreshTokenDuration() {
        return refreshTokenDuration;
    }
    public Set<String> getScopes() {
        return scopes;
    }
}
