package com.monica.recipe.controllers;

import com.monica.recipe.commands.RecipeCommand;
import com.monica.recipe.services.IngredientService;
import com.monica.recipe.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    @GetMapping("recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model){

        RecipeCommand recipeCommand =  recipeService.findCommandById(Long.valueOf(id));
        model.addAttribute("recipe", recipeCommand);

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";
    }


}
