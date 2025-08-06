package com.sales.api.services.implementations;

import com.sales.api.dtos.ClientRequest;
import com.sales.api.entities.Client;
import com.sales.api.models.ClientModel;
import com.sales.api.repositories.ClientRepository;
import com.sales.api.services.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClientServiceImplTest {

    @Autowired
    private ClientServiceImpl clientService;

    @MockitoBean
    private ClientRepository clientRepository;

    @Test
    void getAllClients_returns_ClientModel_list() {

        when(clientRepository.findAll()).thenReturn(List.of(new Client(1L, "Gabi"), new Client(2L, "Nico"), new Client(3L, "Emi")));

        List<ClientModel> clients = clientService.getAllClients();

        assertEquals(ClientModel.class, clients.get(0).getClass());
    }

    @Test
    void getAllClient_returns_empty_list_if_there_are_no_clients() {
        when(clientRepository.findAll()).thenReturn(List.of());

        List<ClientModel> clients = clientService.getAllClients();

        assertEquals(0, clients.size());
    }

    @Test
    void createClient_throws_exception_if_already_exists() {
        when(clientRepository.findByName("Gabi")).thenReturn(new Client(1L, "Gabi"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> clientService.createClient(new ClientRequest("Gabi")));

        assertEquals("This name already exists", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void createClient_returns_created_client_as_ClientModel() {
        Client clientToSave = new Client(null, "Gabi");
        Client clientSaved = new Client(1L, "Gabi");
        when(clientRepository.findByName("Gabi")).thenReturn(null);
        when(clientRepository.save(any(Client.class))).thenReturn(clientSaved);

        ClientModel client = clientService.createClient(new ClientRequest("Gabi"));

        assertEquals(ClientModel.class, client.getClass());
        assertEquals("Gabi", client.getName());
        assertEquals(1L, client.getId());
    }
}