package com.example.inventory.productservice.application;

import com.example.inventory.productservice.domain.Product;
import com.example.inventory.productservice.domain.ProductRepository;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ListProductsUseCase {

    private final ProductRepository productRepository;

    public List<Product> listProducts() {
        return productRepository.findAll();
    }
}
