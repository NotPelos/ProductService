package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Product;
import com.example.inventory.productservice.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopPricedProductsUseCase {

    private final ProductRepository productRepository;

    public TopPricedProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getTopPricedProducts() {
        return productRepository.findTop3ByOrderByPriceDesc();
    }
}
