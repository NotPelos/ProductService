package com.example.inventory.productservice.infrastructure.persistence;

import com.example.inventory.productservice.domain.Category;
import com.example.inventory.productservice.domain.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoryRepository extends JpaRepository<Category, Long>, CategoryRepository {
}
