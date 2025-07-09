package com.leeej.mission_recipes.controller;

import com.leeej.mission_recipes.dto.RecipeDto;
import com.leeej.mission_recipes.model.Recipe;
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

    @GetMapping
    public String list(Model model) {

        List<Recipe> list = recipeRepository.findAll();
        model.addAttribute("recipes", list);

        return "recipe-list";
    }

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

    @GetMapping("/{id}")
    public String view(@PathVariable Integer id, Model model) {

        Recipe recipe = recipeRepository.findById(id);
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setTitle(recipe.getTitle());
        recipeDto.setDescription(recipe.getDescription());

        model.addAttribute("recipeDto", recipeDto);

        return "recipe-view";
    }
}
