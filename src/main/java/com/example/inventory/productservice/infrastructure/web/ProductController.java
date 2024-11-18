package com.example.inventory.productservice.infrastructure.web;

import com.example.inventory.productservice.application.*;
import com.example.inventory.productservice.domain.Product;
import com.example.inventory.productservice.domain.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final ProductRepository productRepository;

    private final LowStockReportUseCase lowStockReportUseCase;
    private final TopPricedProductsUseCase topPricedProductsUseCase;
    private final ProductsByCategoryReportUseCase productsByCategoryReportUseCase;

    @Operation(summary = "Crear un nuevo producto", description = "Este endpoint permite crear un nuevo producto en el sistema.")
    @ApiResponse(responseCode = "200", description = "Producto creado con éxito")
    @ApiResponse(responseCode = "400", description = "Error de validación en los datos enviados")
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = createProductUseCase.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @Operation(summary = "Listar todos los productos", description = "Este endpoint permite obtener una lista de todos los productos existentes en el inventario.")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<Product>> listProducts() {
        List<Product> products = listProductsUseCase.listProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Obtener producto por ID", description = "Este endpoint permite obtener la información de un producto específico basado en su ID.")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.of(productRepository.findById(id));
    }

    @Operation(summary = "Actualizar un producto existente", description = "Este endpoint permite actualizar los detalles de un producto existente en el sistema.")
    @ApiResponse(responseCode = "200", description = "Producto actualizado con éxito")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    product.setStock(updatedProduct.getStock());
                    product.setCategory(updatedProduct.getCategory());
                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un producto", description = "Este endpoint permite eliminar un producto existente del sistema.")
    @ApiResponse(responseCode = "204", description = "Producto eliminado con éxito")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Reporte de productos con bajo stock", description = "Este endpoint permite obtener una lista de productos cuyo stock es inferior a un umbral especificado.")
    @ApiResponse(responseCode = "200", description = "Lista de productos con bajo stock obtenida con éxito")
    @GetMapping("/reports/low-stock")
    public ResponseEntity<List<Product>> getLowStockProducts(@RequestParam(defaultValue = "5") int threshold) {
        return ResponseEntity.ok(lowStockReportUseCase.getLowStockProducts(threshold));
    }

    @Operation(summary = "Reporte de productos más caros", description = "Este endpoint devuelve una lista con los productos más caros.")
    @ApiResponse(responseCode = "200", description = "Lista de productos más caros obtenida con éxito")
    @GetMapping("/reports/top-priced")
    public ResponseEntity<List<Product>> getTopPricedProducts() {
        return ResponseEntity.ok(topPricedProductsUseCase.getTopPricedProducts());
    }

    @Operation(summary = "Reporte de productos por categoría", description = "Este endpoint devuelve el número total de productos agrupados por las categorías especificadas.")
    @ApiResponse(responseCode = "200", description = "Reporte de productos por categoría generado con éxito")
    @ApiResponse(responseCode = "400", description = "Error en los parámetros enviados")
    @GetMapping("/reports/products-by-category")
    public ResponseEntity<Map<Long, Long>> getProductsByCategory(@RequestParam List<Long> categoryIds) {
        return ResponseEntity.ok(productsByCategoryReportUseCase.getProductsByCategory(categoryIds));
    }
}
