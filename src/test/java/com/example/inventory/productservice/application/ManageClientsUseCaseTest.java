package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Client;
import com.example.inventory.productservice.domain.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManageClientsUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ManageClientsUseCase manageClientsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddClient() {
        Client client = new Client();
        client.setName("Client 1");

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client savedClient = manageClientsUseCase.addClient(client);

        assertNotNull(savedClient);
        assertEquals("Client 1", savedClient.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testListClients() {
        Client client = new Client();
        client.setName("Client 1");

        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<Client> clients = manageClientsUseCase.listClients();

        assertFalse(clients.isEmpty());
        assertEquals(1, clients.size());
        assertEquals("Client 1", clients.get(0).getName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testUpdateClient() {
        Client client = new Client();
        client.setName("Old Name");

        Client updatedClient = new Client();
        updatedClient.setName("New Name");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Optional<Client> result = manageClientsUseCase.updateClient(1L, updatedClient);

        assertTrue(result.isPresent());
        assertEquals("New Name", result.get().getName());
        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testDeleteClient() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        boolean result = manageClientsUseCase.deleteClient(1L);

        assertTrue(result);
        verify(clientRepository, times(1)).deleteById(1L);
    }
}
