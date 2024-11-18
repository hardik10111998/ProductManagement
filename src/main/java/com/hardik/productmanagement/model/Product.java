package com.hardik.productmanagement.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Schema(
        name = "Product",
        description = "Schema to hold Product information"
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "id of the product", example = "test product 1"
    )
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    @Schema(
            description = "Name of the Product", example = "Hardik"
    )
    private String name;

    @Schema(
            description = "desc of the product", example = "desc"
    )
    private String description;

    @Schema(
            description = "price of the product", example = "desc"
    )
    private Double price;

    @Schema(
            description = "quantity of the product", example = "desc"
    )
    private Integer quantity;

    // Getters and Setters
}
