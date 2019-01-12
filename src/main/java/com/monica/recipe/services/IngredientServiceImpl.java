package com.monica.recipe.services;

import com.monica.recipe.commands.IngredientCommand;
import com.monica.recipe.converters.IngredientToIngredientCommand;
import com.monica.recipe.models.Ingredient;
import com.monica.recipe.models.Recipe;
import com.monica.recipe.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final RecipeService recipeService;

    private final IngredientRepository ingredientRepository;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    @Override
    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {

        Recipe recipe = recipeService.findById(recipeId).orElse(null);

        if (recipe == null) {
            log.error("Recipe id not found "+ recipeId);
            throw new RuntimeException("Recipe couldn't be found");
        }

        Set<Ingredient> ingredients = recipe.getIngredients();

        Optional<IngredientCommand> ingredientCommand = ingredients.stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if(!ingredientCommand.isPresent()){
            log.error("Ingredient id not found "+ id );
        }

        return ingredientCommand.get();
    }
}
