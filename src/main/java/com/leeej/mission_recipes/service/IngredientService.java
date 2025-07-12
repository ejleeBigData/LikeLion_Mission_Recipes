package com.leeej.mission_recipes.service;

import com.leeej.mission_recipes.model.Ingredient;
import com.leeej.mission_recipes.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final JdbcTemplate jdbcTemplate;
    private final IngredientRepository ingredientRepository;

    @Transactional
    public void save(Ingredient ingredient) {
        Integer ingredientId = ingredientRepository.findIdByName(ingredient.getName());

        if (ingredientId == null) {
            ingredientId = ingredientRepository.insertIngredient(ingredient.getName());
        }

        if (ingredient.getRecipeId() == null) {
            throw new IllegalArgumentException("레시피 ID 누락");
        }

        int count = ingredientRepository.countRecipeIngredient(ingredient.getRecipeId(), ingredientId);

        if (count > 0) {
            ingredientRepository.updateRecipeIngredient(ingredient.getRecipeId(), ingredientId, ingredient.getQuantity());
        } else {
            ingredientRepository.insertRecipeIngredient(ingredient.getRecipeId(), ingredientId, ingredient.getQuantity());
        }
    }



    @Transactional
    public void deleteByRecipeIdAndIngredientId(Integer recipeId, Integer ingredientId) {
        String sqlByRecipeIdAndIngredientId = "DELETE FROM recipe_ingredients WHERE recipe_id = ? AND ingredient_id = ?";
        String sqlIngredientId = "DELETE FROM ingredients WHERE id = ?";

        jdbcTemplate.update(sqlByRecipeIdAndIngredientId, recipeId, ingredientId);
        jdbcTemplate.update(sqlIngredientId, ingredientId);
    }
}
