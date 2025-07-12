package com.leeej.mission_recipes.controller;

import com.leeej.mission_recipes.dto.IngredientDto;
import com.leeej.mission_recipes.dto.RecipeDto;
import com.leeej.mission_recipes.model.Recipe;
import com.leeej.mission_recipes.repository.IngredientRepository;
import com.leeej.mission_recipes.repository.RecipeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    @GetMapping
    public String list(Model model) {

        List<Recipe> list = recipeRepository.findAll();
        model.addAttribute("recipes", list);

        return "recipe-list";
    }

    @GetMapping("/add")
    public String showRecipeAddForm(Model model) {
        model.addAttribute("recipeDto", new RecipeDto() );

        return "recipe-form";
    }

    @PostMapping("/add")
    public String add(
        @Valid @ModelAttribute RecipeDto recipeDto,
        BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) return "recipe-form";

        Recipe recipe = Recipe.builder()
                .title(recipeDto.getTitle())
                .description(recipeDto.getDescription())
                .build();

        recipeRepository.save(recipe);

        return "redirect:/recipes";
    }

    @PostMapping("/{id}/edit")
    public String edit(
        @PathVariable Integer id,
        @Valid @ModelAttribute RecipeDto recipeDto
    ) {


    }

    @GetMapping("/{id}")
    public String view(
            @PathVariable Integer id,
            Model model )
    {

        Recipe recipe = recipeRepository.findById(id);

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setTitle(recipe.getTitle());
        recipeDto.setDescription(recipe.getDescription());
        recipeDto.setCreatedAt(recipe.getCreatedAt());

        model.addAttribute("recipeDto", recipeDto);
        model.addAttribute("ingredientDto", new IngredientDto() );

        model.addAttribute("ingredients", ingredientRepository.findAllByRecipeId(id));

        return "recipe-view";
    }

    @PostMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Integer id) {
        recipeRepository.delete(id);

        return "redirect:/";
    }
}
