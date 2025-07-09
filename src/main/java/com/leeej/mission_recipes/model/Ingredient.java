package com.leeej.mission_recipes.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Ingredient {
    private Integer recipeId;

    private Integer id;
    private String  name;
    private LocalDateTime createdAt;

    private String  quantity;

}
