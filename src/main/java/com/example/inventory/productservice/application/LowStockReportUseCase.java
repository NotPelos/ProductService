package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Product;
import com.example.inventory.productservice.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LowStockReportUseCase {

    private final ProductRepository productRepository;

    public LowStockReportUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getLowStockProducts(int stockThreshold) {
        return productRepository.findByStockLessThan(stockThreshold);
    }
}
