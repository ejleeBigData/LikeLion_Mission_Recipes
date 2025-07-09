package com.leeej.mission_recipes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeDto {
    private Integer id;

    @NotBlank(message = "레시피 제목을 입력하세요")
    private String title;

    private String description;
}
