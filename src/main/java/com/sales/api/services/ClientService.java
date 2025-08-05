package com.sales.api.services;

import com.sales.api.dtos.ClientRequest;
import com.sales.api.entities.Client;
import com.sales.api.models.ClientModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    List<ClientModel> getAllClients();
    ClientModel createClient(ClientRequest client);
    Client getClientById(Long clientId);
}
