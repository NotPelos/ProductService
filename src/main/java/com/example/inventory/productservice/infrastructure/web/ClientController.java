package com.example.inventory.productservice.infrastructure.web;

import com.example.inventory.productservice.application.ManageClientsUseCase;
import com.example.inventory.productservice.domain.Client;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ManageClientsUseCase manageClientsUseCase;

    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        return ResponseEntity.ok(manageClientsUseCase.addClient(client));
    }

    @GetMapping
    public ResponseEntity<List<Client>> listClients() {
        return ResponseEntity.ok(manageClientsUseCase.listClients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return manageClientsUseCase.updateClient(id, client)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        if (manageClientsUseCase.deleteClient(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
