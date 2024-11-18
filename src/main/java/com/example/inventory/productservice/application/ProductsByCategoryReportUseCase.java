package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.CategoryRepository;
import com.example.inventory.productservice.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductsByCategoryReportUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductsByCategoryReportUseCase(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Map<Long, Long> getProductsByCategory(List<Long> categoryIds) {
        // Validar que las categorías existen
        List<Long> validCategoryIds = categoryRepository.findByIdIn(categoryIds)
                .stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());

        // Validar si hay categorías inexistentes
        List<Long> invalidCategoryIds = categoryIds.stream()
                .filter(id -> !validCategoryIds.contains(id))
                .collect(Collectors.toList());

        if (!invalidCategoryIds.isEmpty()) {
            throw new IllegalArgumentException("Las categorías con IDs " + invalidCategoryIds + " no existen.");
        }

        // Generar el reporte para las categorías válidas
        Map<Long, Long> report = new HashMap<>();
        for (Long categoryId : validCategoryIds) {
            long count = productRepository.countByCategoryId(categoryId);
            report.put(categoryId, count);
        }
        return report;
    }
}
