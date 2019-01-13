package com.monica.recipe.services;

import com.monica.recipe.commands.IngredientCommand;
import com.monica.recipe.models.Ingredient;

public interface IngredientService {

    Ingredient findById(Long id);

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand command);


}
