package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Category;
import com.example.inventory.productservice.domain.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateCategoryUseCaseTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CreateCategoryUseCase createCategoryUseCase;

    @BeforeEach
    void setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategorySuccessfully() {
        // Configuración del mock
        Category category = new Category();
        category.setName("Electrónica");
        category.setDescription("Dispositivos electrónicos");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Llamada al caso de uso
        Category createdCategory = createCategoryUseCase.createCategory(category);

        // Verificaciones
        assertNotNull(createdCategory);
        assertEquals("Electrónica", createdCategory.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testCreateCategoryWithEmptyName() {
        // Categoría inválida
        Category category = new Category();
        category.setName("");

        // Verificación de excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createCategoryUseCase.createCategory(category)
        );

        assertEquals("El nombre de la categoría no puede estar vacío.", exception.getMessage());
        verify(categoryRepository, never()).save(any(Category.class));
    }
}
