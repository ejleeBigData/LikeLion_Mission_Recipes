package com.leeej.mission_recipes.controller;

import com.leeej.mission_recipes.dto.IngredientDto;
import com.leeej.mission_recipes.dto.RecipeDto;
import com.leeej.mission_recipes.model.Ingredient;
import com.leeej.mission_recipes.model.Recipe;
import com.leeej.mission_recipes.repository.IngredientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipes/{id}")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientRepository ingredientRepository;

    @GetMapping("/ingredients/add")
    public String addForm(Model model) {
        model.addAttribute("ingredientDto", new IngredientDto() );

        return "recipe-view";
    }

    @PostMapping("/ingredients/add")
    public String add(
            @PathVariable Integer id,
            @Valid @ModelAttribute IngredientDto ingredientDto,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) return "recipe-view";

        ingredientDto.setRecipeId(id);

        Ingredient ingredient = Ingredient.builder()
                .id(ingredientDto.getId())
                .name(ingredientDto.getName())
                .quantity(ingredientDto.getQuantity())
                .recipeId(ingredientDto.getRecipeId())
                .build();

        ingredientRepository.save(ingredient);

        return "redirect:/recipes/" + id;
    }
}
