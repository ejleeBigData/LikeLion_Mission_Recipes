package com.leeej.mission_recipes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDateTime;

@Getter
@Setter
public class IngredientDto {
    private Integer recipeId;
    private Integer id;

    @NotBlank(message = "재료명 입력.")
    private String ingredient_name;

    @NotBlank(message = "재료양 입력.")
    private String quantity;

    private LocalDateTime createdAt;
}
