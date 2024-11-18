package com.example.inventory.productservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la categoría no puede estar vacío.")
    @Size(max = 100, message = "El nombre de la categoría no puede tener más de 100 caracteres.")
    private String name;

    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres.")
    private String description;
}
