package com.example.inventory.productservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    @Size(max = 100, message = "El nombre del producto no puede tener más de 100 caracteres.")
    private String name;

    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres.")
    private String description;

    @NotNull(message = "El precio no puede ser nulo.")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0.")
    private Double price;

    @NotNull(message = "El stock no puede ser nulo.")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0.")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "El producto debe estar asociado a una categoría.")
    private Category category;
}
