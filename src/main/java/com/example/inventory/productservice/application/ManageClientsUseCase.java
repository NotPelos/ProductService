package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Client;
import com.example.inventory.productservice.domain.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManageClientsUseCase {

    private final ClientRepository clientRepository;

    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> listClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> updateClient(Long id, Client updatedClient) {
        return clientRepository.findById(id).map(client -> {
            client.setName(updatedClient.getName());
            client.setEmail(updatedClient.getEmail());
            client.setPhone(updatedClient.getPhone());
            client.setAddress(updatedClient.getAddress());
            return clientRepository.save(client);
        });
    }

    public boolean deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
