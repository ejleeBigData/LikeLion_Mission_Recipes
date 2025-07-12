package com.leeej.mission_recipes.repository;


import com.leeej.mission_recipes.model.Ingredient;
import com.leeej.mission_recipes.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IngredientRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Ingredient> recipeRowMapper = (resultSet, rowNum) ->
            Ingredient.builder()
                    .recipeId(resultSet.getInt("recipe_id"))
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("ingredient_name"))
                    .quantity(resultSet.getString("quantity"))
                    .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                    .build();

    public List<Ingredient> findAllByRecipeId(int recipeId) {
        String sql = "SELECT r.id AS recipe_id, r.title , r.created_at, " +
                " i.id , i.name AS ingredient_name,"+
                " ri.quantity " +
                " FROM recipes r " +
                " JOIN recipe_ingredients ri ON ri.recipe_id = r.id " +
                " JOIN ingredients i ON i.id = ri.ingredient_id " +
                " WHERE r.id = ?" +
                " ORDER BY r.id" ;
        return jdbcTemplate.query(sql, recipeRowMapper, recipeId) ;
    }

    public Integer findIdByName(String name) {
        String sql = "SELECT id FROM ingredients WHERE name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer insertIngredient(String name) {
        String sql = "INSERT INTO ingredients(name) VALUES (?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Integer.class, name);
    }

    public int insertRecipeIngredient(Integer recipeId, Integer ingredientId, String quantity) {
        String sql = "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, recipeId, ingredientId, quantity);
    }

    public int updateRecipeIngredient(Integer recipeId, Integer ingredientId, String quantity) {
        String sql = "UPDATE recipe_ingredients SET quantity = ? WHERE recipe_id = ? AND ingredient_id = ?";
        return jdbcTemplate.update(sql, quantity, recipeId, ingredientId);
    }

    public int countRecipeIngredient(Integer recipeId, Integer ingredientId) {
        String sql = "SELECT COUNT(*) FROM recipe_ingredients WHERE recipe_id = ? AND ingredient_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, recipeId, ingredientId);
    }
}
