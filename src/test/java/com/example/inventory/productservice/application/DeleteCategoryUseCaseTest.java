package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.CategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteCategoryUseCaseTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private DeleteCategoryUseCase deleteCategoryUseCase;
    
    @BeforeEach
    void setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteCategorySuccessfully() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        boolean result = deleteCategoryUseCase.deleteCategory(1L);

        assertTrue(result);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNonExistentCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        boolean result = deleteCategoryUseCase.deleteCategory(1L);

        assertFalse(result);
        verify(categoryRepository, never()).deleteById(1L);
    }
}
