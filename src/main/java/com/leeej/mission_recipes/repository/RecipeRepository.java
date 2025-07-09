package com.leeej.mission_recipes.repository;

import com.leeej.mission_recipes.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecipeRepository {
    private final JdbcTemplate jdbcTemplate;

    public int save(Recipe recipe) {
        String sql = " INSERT INTO recipes(title, description) VALUES(?, ?) ";

        return jdbcTemplate.update(sql, recipe.getTitle(), recipe.getDescription());
    }

    public int update(Recipe recipe) {
        String sql = " UPDATE recipes SET title = ? , "  +
                " description = ? WHERE id = ? ";

        return jdbcTemplate.update(sql, recipe.getTitle(), recipe.getDescription(), recipe.getId());
    }
}
