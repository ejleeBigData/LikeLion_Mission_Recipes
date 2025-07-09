package com.leeej.mission_recipes.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Recipe {
    private Integer id;
    private String  title;
    private String  description;
    private LocalDateTime create_at;
}
