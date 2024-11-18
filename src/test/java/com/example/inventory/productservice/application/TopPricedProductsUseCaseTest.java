package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Product;
import com.example.inventory.productservice.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TopPricedProductsUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private TopPricedProductsUseCase topPricedProductsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTopPricedProducts() {
        // Mock de productos más caros
        Product product1 = new Product();
        product1.setName("Producto 1");
        product1.setPrice(1000.0);

        Product product2 = new Product();
        product2.setName("Producto 2");
        product2.setPrice(800.0);

        when(productRepository.findTop3ByOrderByPriceDesc()).thenReturn(Arrays.asList(product1, product2));

        // Llamada al caso de uso
        List<Product> result = topPricedProductsUseCase.getTopPricedProducts();

        // Verificaciones
        assertEquals(2, result.size());
        assertEquals("Producto 1", result.get(0).getName());
        assertEquals(1000.0, result.get(0).getPrice());
        assertEquals("Producto 2", result.get(1).getName());

        verify(productRepository, times(1)).findTop3ByOrderByPriceDesc();
    }
}
