package com.sales.api.services.implementations;

import com.sales.api.dtos.ClientRequest;
import com.sales.api.entities.Client;
import com.sales.api.models.ClientModel;
import com.sales.api.repositories.ClientRepository;
import com.sales.api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<ClientModel> getAllClients() {
        List<Client> clients = clientRepository.findAll();

        List<ClientModel> clientModels = new ArrayList<>();

        if(clients.size() > 0){
            for(Client client : clients){
                clientModels.add(mapEntityIntoModel(client));
            }
        }
        return clientModels;
    }

    @Override
    public ClientModel createClient(ClientRequest client) {
        Client clientWithName = clientRepository.findByName(client.getName());

        if(clientWithName != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This name already exists");
        }

        Client clientEntity = new Client();

        clientEntity.setName(client.getName());
        return mapEntityIntoModel(clientRepository.save(clientEntity));
    }

    @Override
    public Client getClientById(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        return client;
    }

    private ClientModel mapEntityIntoModel(Client client){
        ClientModel clientModel = new ClientModel(client.getId(), client.getName());
        return clientModel;
    }
}
