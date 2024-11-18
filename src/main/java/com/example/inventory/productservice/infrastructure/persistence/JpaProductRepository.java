package com.example.inventory.productservice.infrastructure.persistence;

import com.example.inventory.productservice.domain.Product;
import com.example.inventory.productservice.domain.ProductRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, Long>, ProductRepository {
	List<Product> findByStockLessThan(int stockThreshold);
    List<Product> findTop3ByOrderByPriceDesc(); // Cambia el número según tus necesidades
    long countByCategoryId(Long categoryId);
}
