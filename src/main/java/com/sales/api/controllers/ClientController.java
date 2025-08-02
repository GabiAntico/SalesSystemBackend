package com.sales.api.controllers;

import com.sales.api.dtos.ClientRequest;
import com.sales.api.models.ClientModel;
import com.sales.api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping()
    public ResponseEntity<List<ClientModel>> getAllClients(){
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @PostMapping()
    public ResponseEntity<ClientModel> createNewClient(@RequestBody ClientRequest client){
        return ResponseEntity.ok(clientService.createClient(client));
    }
}
