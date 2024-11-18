package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.CategoryRepository;
import com.example.inventory.productservice.domain.Product;
import com.example.inventory.productservice.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public CreateProductUseCase(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(Product product) {
        // Verificar si la categoría existe
        Optional.ofNullable(product.getCategory())
                .flatMap(category -> categoryRepository.findById(category.getId()))
                .orElseThrow(() -> new IllegalArgumentException("La categoría especificada no existe."));

        // Validaciones de negocio
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        return productRepository.save(product);
    }
}
