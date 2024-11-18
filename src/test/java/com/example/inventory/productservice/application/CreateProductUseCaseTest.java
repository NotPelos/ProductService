package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Category;
import com.example.inventory.productservice.domain.CategoryRepository;
import com.example.inventory.productservice.domain.Product;
import com.example.inventory.productservice.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductSuccessfully() {
        // Configuración del mock para la categoría
        Category category = new Category();
        category.setId(1L);
        category.setName("Electrónica");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Configuración del mock para el producto
        Product product = new Product();
        product.setName("Laptop");
        product.setDescription("Laptop de alta gama");
        product.setPrice(1200.0);
        product.setStock(10);
        product.setCategory(category);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Llamada al caso de uso
        Product createdProduct = createProductUseCase.createProduct(product);

        // Verificaciones
        assertNotNull(createdProduct);
        assertEquals("Laptop", createdProduct.getName());
        assertEquals(1200.0, createdProduct.getPrice());
        assertEquals(category, createdProduct.getCategory());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testCreateProductWithNonExistentCategory() {
        // Configuración del mock para categoría inexistente
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Producto de prueba
        Product product = new Product();
        product.setName("Laptop");
        product.setCategory(new Category());
        product.getCategory().setId(1L);

        // Verificación de excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createProductUseCase.createProduct(product)
        );

        assertEquals("La categoría especificada no existe.", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }
}
