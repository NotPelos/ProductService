package com.example.inventory.productservice.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);
	boolean existsById(Long id);
	List<Product> findByStockLessThan(int stockThreshold); // Productos con bajo stock
    List<Product> findTop3ByOrderByPriceDesc(); // Productos más caros
    long countByCategoryId(Long categoryId); // Número de productos por categoría
}
