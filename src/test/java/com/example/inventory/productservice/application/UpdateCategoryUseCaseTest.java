package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Category;
import com.example.inventory.productservice.domain.CategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateCategoryUseCaseTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private UpdateCategoryUseCase updateCategoryUseCase;
    
    @BeforeEach
    void setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateCategorySuccessfully() {
        // Categoría existente
        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setName("Electrónica");
        existingCategory.setDescription("Dispositivos electrónicos");

        // Datos de actualización
        Category updatedCategory = new Category();
        updatedCategory.setName("Electrónica Avanzada");
        updatedCategory.setDescription("Gadgets avanzados");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        // Llamada al caso de uso
        Optional<Category> result = updateCategoryUseCase.updateCategory(1L, updatedCategory);

        // Verificaciones
        assertTrue(result.isPresent());
        assertEquals("Electrónica Avanzada", result.get().getName());
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    void testUpdateNonExistentCategory() {
        // Categoría inexistente
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamada al caso de uso
        Optional<Category> result = updateCategoryUseCase.updateCategory(1L, new Category());

        // Verificaciones
        assertFalse(result.isPresent());
        verify(categoryRepository, never()).save(any(Category.class));
    }
}
