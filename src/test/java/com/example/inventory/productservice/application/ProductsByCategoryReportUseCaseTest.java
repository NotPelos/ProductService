package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Category;
import com.example.inventory.productservice.domain.CategoryRepository;
import com.example.inventory.productservice.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductsByCategoryReportUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductsByCategoryReportUseCase productsByCategoryReportUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductsByCategorySuccessfully() {
        // Mock de categorías válidas
        Category category1 = new Category();
        category1.setId(1L);
        Category category2 = new Category();
        category2.setId(2L);

        when(categoryRepository.findByIdIn(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(category1, category2));
        when(productRepository.countByCategoryId(1L)).thenReturn(10L);
        when(productRepository.countByCategoryId(2L)).thenReturn(5L);

        // Llamada al caso de uso
        Map<Long, Long> result = productsByCategoryReportUseCase.getProductsByCategory(Arrays.asList(1L, 2L));

        // Verificaciones
        assertEquals(2, result.size());
        assertEquals(10L, result.get(1L));
        assertEquals(5L, result.get(2L));

        verify(categoryRepository, times(1)).findByIdIn(Arrays.asList(1L, 2L));
        verify(productRepository, times(1)).countByCategoryId(1L);
        verify(productRepository, times(1)).countByCategoryId(2L);
    }

    @Test
    void testGetProductsByCategoryWithInvalidIds() {
        // Mock de categorías válidas
        Category category1 = new Category();
        category1.setId(1L);

        when(categoryRepository.findByIdIn(Arrays.asList(1L, 99L))).thenReturn(Arrays.asList(category1));

        // Verificación de excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                productsByCategoryReportUseCase.getProductsByCategory(Arrays.asList(1L, 99L))
        );

        assertEquals("Las categorías con IDs [99] no existen.", exception.getMessage());

        verify(categoryRepository, times(1)).findByIdIn(Arrays.asList(1L, 99L));
        verify(productRepository, never()).countByCategoryId(anyLong());
    }
}
