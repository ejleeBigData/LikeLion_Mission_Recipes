package com.leeej.mission_recipes.controller;

import com.leeej.mission_recipes.dto.IngredientDto;
import com.leeej.mission_recipes.dto.RecipeDto;
import com.leeej.mission_recipes.model.Ingredient;
import com.leeej.mission_recipes.model.Recipe;
import com.leeej.mission_recipes.repository.IngredientRepository;
import com.leeej.mission_recipes.repository.RecipeRepository;
import com.leeej.mission_recipes.service.IngredientService;
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
    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;

    private void addRecipeDtoToModel(Integer id, Model model) {
        Recipe recipe = recipeRepository.findById(id);
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setTitle(recipe.getTitle());
        recipeDto.setDescription(recipe.getDescription());
        model.addAttribute("recipeDto", recipeDto);
    }


    @GetMapping("/ingredients/add")
    public String showIngredientAddForm(@PathVariable Integer id,Model model) {

        model.addAttribute("ingredientDto", new IngredientDto() );
        addRecipeDtoToModel(id, model);

        return "redirect:/recipes/{id}";
    }

    @PostMapping("/ingredients/add")
    public String add(
            @PathVariable Integer id,
            @Valid @ModelAttribute IngredientDto ingredientDto,
            BindingResult bindingResult,
            Model model
    ) {
        //재료 저장 실패를 추가
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredientDto", ingredientDto);
            addRecipeDtoToModel(id, model);

            model.addAttribute("ingredients", ingredientRepository.findAllByRecipeId(id));
            return "recipe-view";
        }


        ingredientDto.setRecipeId(id);

        Ingredient ingredient = Ingredient.builder()
                .id(ingredientDto.getId())
                .name(ingredientDto.getIngredient_name())
                .quantity(ingredientDto.getQuantity())
                .recipeId(ingredientDto.getRecipeId())
                .build();

        ingredientService.save(ingredient);

        return "redirect:/recipes/{id}";
    }

    @PostMapping("/ingredients/{ingredientId}/remove")
    public String removeIngredient(
            @PathVariable Integer id,
            @PathVariable Integer ingredientId
    ) {
        ingredientService.deleteByRecipeIdAndIngredientId(id, ingredientId);

        return "redirect:/recipes/{id}";
    }

}
