package com.example.inventory.productservice.infrastructure.web;

import com.example.inventory.productservice.application.CreateCategoryUseCase;
import com.example.inventory.productservice.application.DeleteCategoryUseCase;
import com.example.inventory.productservice.application.ListCategoriesUseCase;
import com.example.inventory.productservice.application.UpdateCategoryUseCase;
import com.example.inventory.productservice.domain.Category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    @Operation(summary = "Crear una nueva categoría", description = "Este endpoint permite crear una nueva categoría en el sistema.")
    @ApiResponse(responseCode = "200", description = "Categoría creada con éxito")
    @ApiResponse(responseCode = "400", description = "Error de validación en los datos enviados")
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.ok(createCategoryUseCase.createCategory(category));
    }

    @Operation(summary = "Listar todas las categorías", description = "Este endpoint devuelve una lista de todas las categorías disponibles en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<Category>> listCategories() {
        return ResponseEntity.ok(listCategoriesUseCase.listCategories());
    }

    @Operation(summary = "Obtener una categoría por ID", description = "Este endpoint permite obtener los detalles de una categoría específica usando su ID.")
    @ApiResponse(responseCode = "200", description = "Categoría encontrada")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return createCategoryUseCase.getCategoryById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar una categoría", description = "Este endpoint permite actualizar los detalles de una categoría existente.")
    @ApiResponse(responseCode = "200", description = "Categoría actualizada con éxito")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody Category updatedCategory) {
        return updateCategoryUseCase.updateCategory(id, updatedCategory)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una categoría", description = "Este endpoint permite eliminar una categoría existente del sistema.")
    @ApiResponse(responseCode = "204", description = "Categoría eliminada con éxito")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (deleteCategoryUseCase.deleteCategory(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
