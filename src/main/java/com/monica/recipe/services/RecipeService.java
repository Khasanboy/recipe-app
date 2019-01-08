package com.monica.recipe.services;

import com.monica.recipe.commands.RecipeCommand;
import com.monica.recipe.models.Recipe;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    Set<Recipe> getAllRecipes();

    Optional<Recipe> findById(Long id);

    RecipeCommand findCommandById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    public void deleteById(Long id);

}
