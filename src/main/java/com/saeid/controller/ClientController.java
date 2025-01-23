package com.saeid.controller;

import com.saeid.model.ClientDto;
import com.saeid.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ClientController {

    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/client")
    public ResponseEntity<ClientDto> createClient(@Validated @RequestBody ClientDto clientDto) {
        ClientDto client = clientService.createClient(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }
}
