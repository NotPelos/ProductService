package com.example.inventory.productservice.infrastructure.web;

import com.example.inventory.productservice.application.ManageProvidersUseCase;
import com.example.inventory.productservice.domain.Provider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/providers")
@AllArgsConstructor
public class ProviderController {

    private final ManageProvidersUseCase manageProvidersUseCase;

    @PostMapping
    public ResponseEntity<Provider> addProvider(@RequestBody Provider provider) {
        return ResponseEntity.ok(manageProvidersUseCase.addProvider(provider));
    }

    @GetMapping
    public ResponseEntity<List<Provider>> listProviders() {
        return ResponseEntity.ok(manageProvidersUseCase.listProviders());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Provider> updateProvider(@PathVariable Long id, @RequestBody Provider provider) {
        return manageProvidersUseCase.updateProvider(id, provider)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        if (manageProvidersUseCase.deleteProvider(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
