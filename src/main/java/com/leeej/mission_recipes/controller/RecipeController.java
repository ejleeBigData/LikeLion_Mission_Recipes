package com.leeej.mission_recipes.controller;

import com.leeej.mission_recipes.dto.RecipeDto;
import com.leeej.mission_recipes.model.Recipe;
import com.leeej.mission_recipes.repository.RecipeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeRepository recipeRepository;

    @GetMapping("/add")
    public String addForm(Model model) {
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
}
