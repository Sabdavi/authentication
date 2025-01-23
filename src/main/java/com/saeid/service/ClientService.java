package com.saeid.service;

import com.saeid.Entity.Client;
import com.saeid.model.ClientDto;
import com.saeid.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ClientService {

    ClientRepository clientRepository;
    long tokenDefaultDuration = 86400000L;
    long refreshTokenDefaultDuration = 7*86400000L;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDto createClient(ClientDto clientDto) {
        Client client = new Client();
        client.setClientId(clientDto.getClientId());
        client.setClientSecret(clientDto.getClientSecret());
        if(clientDto.getTokenDuration() == null){
            client.setDuration(tokenDefaultDuration);
        }else {
            client.setDuration(clientDto.getTokenDuration());
        }
        if(clientDto.getRefreshTokenDuration() == null){
            client.setRefreshTokenDuration(refreshTokenDefaultDuration);
        }else {
            client.setRefreshTokenDuration(clientDto.getRefreshTokenDuration());
        }
        if(clientDto.getScopes() != null && !clientDto.getScopes().isEmpty()){
            client.setScope(clientDto.getScopes());
        } else {
            client.setScope(Set.of("read", "write"));
        }
        client.setName(clientDto.getClientName());
        clientRepository.save(client);
        return client.toClientDto();
    }

    public Client verifyClient(String clientId, String clientSecret) {
        return clientRepository.findByClientIdAndClientSecret(clientId, clientSecret)
                .orElseThrow(() -> new SecurityException("Invalid client id or secret"));
    }
}
