package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Category;
import com.example.inventory.productservice.domain.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public UpdateCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(updatedCategory.getName());
                    existingCategory.setDescription(updatedCategory.getDescription());
                    return categoryRepository.save(existingCategory);
                });
    }
}
