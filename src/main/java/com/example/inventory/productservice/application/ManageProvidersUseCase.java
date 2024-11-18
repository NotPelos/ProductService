package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Provider;
import com.example.inventory.productservice.domain.ProviderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManageProvidersUseCase {

    private final ProviderRepository providerRepository;

    public Provider addProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    public List<Provider> listProviders() {
        return providerRepository.findAll();
    }

    public Optional<Provider> updateProvider(Long id, Provider updatedProvider) {
        return providerRepository.findById(id).map(provider -> {
            provider.setName(updatedProvider.getName());
            provider.setEmail(updatedProvider.getEmail());
            provider.setPhone(updatedProvider.getPhone());
            provider.setAddress(updatedProvider.getAddress());
            return providerRepository.save(provider);
        });
    }

    public boolean deleteProvider(Long id) {
        if (providerRepository.existsById(id)) {
            providerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
