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
                    .name(resultSet.getString("name"))
                    .quantity(resultSet.getString("quantity"))
                    .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                    .build();

    public List<Ingredient> findByRecipeId(int recipeId) {
        String sql = "SELECT r.id AS recipe_id, " +
                " r.title AS recipe_title, " +
                " i.id AS id, " +
                " i.name AS ingredient_name,"+
                " ri.quantity " +
                " FROM recipes r " +
                " JOIN recipe_ingredients ri ON ri.recipe_id = r.id " +
                " JOIN ingredients i ON i.id = ri.ingredient_id " +
                " WHERE r.id = ?" +
                " ORDER BY r.id" ;
        return jdbcTemplate.query(sql, recipeRowMapper, recipeId) ;
    }

    public int save(Ingredient ingredient) {
        String getIdSql = "SELECT id FROM ingredients WHERE name = ?";
        Integer ingredientId = null;

        try {
            ingredientId = jdbcTemplate.queryForObject(getIdSql, Integer.class, ingredient.getName());
        } catch (EmptyResultDataAccessException e) {
            String insertSql = "INSERT INTO ingredients(name) VALUES (?) RETURNING id";
            ingredientId = jdbcTemplate.queryForObject(insertSql, Integer.class, ingredient.getName());
        }

        if (ingredient.getRecipeId() == null) {
            throw new IllegalArgumentException("recipeId is null. Must not be null.");
        }

        String sqlQuan = "INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sqlQuan,
                ingredient.getRecipeId(),
                ingredientId,
                ingredient.getQuantity() != null ? ingredient.getQuantity() : "");
    }


}
