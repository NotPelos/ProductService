package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Provider;
import com.example.inventory.productservice.domain.ProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManageProvidersUseCaseTest {

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ManageProvidersUseCase manageProvidersUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProvider() {
        Provider provider = new Provider();
        provider.setName("Provider 1");

        when(providerRepository.save(any(Provider.class))).thenReturn(provider);

        Provider savedProvider = manageProvidersUseCase.addProvider(provider);

        assertNotNull(savedProvider);
        assertEquals("Provider 1", savedProvider.getName());
        verify(providerRepository, times(1)).save(provider);
    }

    @Test
    void testListProviders() {
        Provider provider = new Provider();
        provider.setName("Provider 1");

        when(providerRepository.findAll()).thenReturn(List.of(provider));

        List<Provider> providers = manageProvidersUseCase.listProviders();

        assertFalse(providers.isEmpty());
        assertEquals(1, providers.size());
        assertEquals("Provider 1", providers.get(0).getName());
        verify(providerRepository, times(1)).findAll();
    }

    @Test
    void testUpdateProvider() {
        Provider provider = new Provider();
        provider.setName("Old Name");

        Provider updatedProvider = new Provider();
        updatedProvider.setName("New Name");

        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));
        when(providerRepository.save(any(Provider.class))).thenReturn(provider);

        Optional<Provider> result = manageProvidersUseCase.updateProvider(1L, updatedProvider);

        assertTrue(result.isPresent());
        assertEquals("New Name", result.get().getName());
        verify(providerRepository, times(1)).findById(1L);
        verify(providerRepository, times(1)).save(provider);
    }

    @Test
    void testDeleteProvider() {
        when(providerRepository.existsById(1L)).thenReturn(true);

        boolean result = manageProvidersUseCase.deleteProvider(1L);

        assertTrue(result);
        verify(providerRepository, times(1)).deleteById(1L);
    }
}
