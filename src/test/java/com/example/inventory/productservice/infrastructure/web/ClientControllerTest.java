package com.example.inventory.productservice.infrastructure.web;

import com.example.inventory.productservice.application.ManageClientsUseCase;
import com.example.inventory.productservice.domain.Client;
import com.example.inventory.productservice.security.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(ClientController.class)
@Import(TestSecurityConfig.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManageClientsUseCase manageClientsUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddClient() throws Exception {
        Client client = new Client();
        client.setName("Client 1");
        client.setEmail("client1@example.com");
        client.setPhone("123456789");
        client.setAddress("Address 1");

        when(manageClientsUseCase.addClient(any(Client.class))).thenReturn(client);

        mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Client 1"))
                .andExpect(jsonPath("$.email").value("client1@example.com"));
    }

    @Test
    void testListClients() throws Exception {
        Client client = new Client();
        client.setName("Client 1");

        when(manageClientsUseCase.listClients()).thenReturn(Collections.singletonList(client));

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Client 1"));
    }

    @Test
    void testUpdateClient() throws Exception {
        Client updatedClient = new Client();
        updatedClient.setName("Updated Name");

        when(manageClientsUseCase.updateClient(eq(1L), any(Client.class))).thenReturn(Optional.of(updatedClient));

        mockMvc.perform(put("/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedClient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void testDeleteClient() throws Exception {
        when(manageClientsUseCase.deleteClient(1L)).thenReturn(true);

        mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isNoContent());
    }
}
