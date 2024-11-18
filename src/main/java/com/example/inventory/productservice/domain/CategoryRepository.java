package com.example.inventory.productservice.domain;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(Long id);
    List<Category> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
    List<Category> findByIdIn(List<Long> ids);

}
