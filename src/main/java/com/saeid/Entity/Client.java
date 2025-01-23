package com.saeid.Entity;

import com.saeid.model.ClientDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String clientSecret;

    @ElementCollection
    @CollectionTable(name = "client_scopes", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "scope")
    private Set<String> scopes;

    @Column(name =  "duration")
    private long duration;

    @Column(name= "refresh_token_duration")
    private long refreshTokenDuration;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<User> users = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getRefreshTokenDuration() {
        return refreshTokenDuration;
    }

    public void setRefreshTokenDuration(long refreshTokenDuration) {
        this.refreshTokenDuration = refreshTokenDuration;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScope(Set<String> scopes) {
        this.scopes = scopes;
    }

    public ClientDto toClientDto() {
        return new ClientDto(clientId, clientSecret, name, duration, refreshTokenDuration, scopes);
    }
}
