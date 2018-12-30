package com.monica.recipe.controllers;

import com.monica.recipe.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @RequestMapping("/recipe/show/{id}")
    public String getRecipeById(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)).orElse(null));

        return "recipe/show";
    }

}
