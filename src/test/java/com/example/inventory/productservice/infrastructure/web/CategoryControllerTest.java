package com.example.inventory.productservice.infrastructure.web;

import com.example.inventory.productservice.application.CreateCategoryUseCase;
import com.example.inventory.productservice.application.ListCategoriesUseCase;
import com.example.inventory.productservice.application.UpdateCategoryUseCase;
import com.example.inventory.productservice.application.DeleteCategoryUseCase;
import com.example.inventory.productservice.domain.Category;
import com.example.inventory.productservice.security.TestSecurityConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import(TestSecurityConfig.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @MockBean
    private ListCategoriesUseCase listCategoriesUseCase;

    @MockBean
    private UpdateCategoryUseCase updateCategoryUseCase;

    @MockBean
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @Test
    void testGetCategories() throws Exception {
        when(listCategoriesUseCase.listCategories()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/categories")
                .header("Authorization", "Bearer mockedToken")) // Incluye el token
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testCreateCategory() throws Exception {
        Category category = new Category();
        category.setName("Electrónica");

        when(createCategoryUseCase.createCategory(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/categories")
                .header("Authorization", "Bearer mockedToken") // Incluye el token
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Electrónica\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electrónica"));
    }
}
