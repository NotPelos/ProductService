package com.example.inventory.productservice.infrastructure.web;

import com.example.inventory.productservice.application.ManageProvidersUseCase;
import com.example.inventory.productservice.domain.Provider;
import com.example.inventory.productservice.security.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProviderController.class)
@Import(TestSecurityConfig.class)
class ProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManageProvidersUseCase manageProvidersUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddProvider() throws Exception {
        Provider provider = new Provider();
        provider.setName("Provider 1");
        provider.setEmail("provider1@example.com");
        provider.setPhone("123456789");
        provider.setAddress("Address 1");

        when(manageProvidersUseCase.addProvider(any(Provider.class))).thenReturn(provider);

        mockMvc.perform(post("/providers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(provider)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Provider 1"))
                .andExpect(jsonPath("$.email").value("provider1@example.com"));
    }

    @Test
    void testListProviders() throws Exception {
        Provider provider = new Provider();
        provider.setName("Provider 1");

        when(manageProvidersUseCase.listProviders()).thenReturn(Collections.singletonList(provider));

        mockMvc.perform(get("/providers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Provider 1"));
    }

    @Test
    void testUpdateProvider() throws Exception {
        Provider updatedProvider = new Provider();
        updatedProvider.setName("Updated Name");

        when(manageProvidersUseCase.updateProvider(eq(1L), any(Provider.class))).thenReturn(Optional.of(updatedProvider));

        mockMvc.perform(put("/providers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProvider)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void testDeleteProvider() throws Exception {
        when(manageProvidersUseCase.deleteProvider(1L)).thenReturn(true);

        mockMvc.perform(delete("/providers/1"))
                .andExpect(status().isNoContent());
    }
}
