package com.monica.recipe.services;

import com.monica.recipe.commands.IngredientCommand;
import com.monica.recipe.converters.IngredientCommandToIngredient;
import com.monica.recipe.converters.IngredientToIngredientCommand;
import com.monica.recipe.models.Ingredient;
import com.monica.recipe.models.Recipe;
import com.monica.recipe.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final RecipeService recipeService;

    private final IngredientRepository ingredientRepository;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    private final UnitOfMeasureService unitOfMeasureService;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Override
    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {

        Recipe recipe = recipeService.findById(recipeId).orElse(null);

        if (recipe == null) {
            log.error("Recipe id not found " + recipeId);
            throw new RuntimeException("Recipe couldn't be found");
        }

        Set<Ingredient> ingredients = recipe.getIngredients();

        Optional<IngredientCommand> ingredientCommand = ingredients.stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if (!ingredientCommand.isPresent()) {
            log.error("Ingredient id not found " + id);
        }

        return ingredientCommand.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeService.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());
            ingredientFound.setUnitOfMeasure(unitOfMeasureService.findById(command.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
        } else {
            Ingredient ingredient = ingredientCommandToIngredient.convert(command);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        Recipe savedRecipe = recipeService.save(recipe);

        Optional<Ingredient> savedIngredientOptional1 = savedRecipe.getIngredients().stream()
                .filter(recipeIngredient -> recipeIngredient.getId().equals(command.getId()))
                .findFirst();

        //check by description
        if (!savedIngredientOptional1.isPresent()) {
            //not totally save but best guess
            System.out.println(savedRecipe.getIngredients().size());
            savedIngredientOptional1 = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> {
                        System.out.println(ingredient.getDescription());
                        System.out.println(command.getDescription());
                        return ingredient.getDescription().equals(command.getDescription());
                    })
                    .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                    .filter(ingredient -> ingredient.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
                    .findFirst();
        }

        //to check for final

        return ingredientToIngredientCommand.convert(savedIngredientOptional1.get());
    }
}
