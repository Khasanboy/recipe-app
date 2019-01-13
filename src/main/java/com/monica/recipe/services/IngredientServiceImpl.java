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

    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    private final UnitOfMeasureService unitOfMeasureService;

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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeService.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()){
            log.error("Recipe not found for id: "+command.getRecipeId());
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();

        if(ingredientOptional.isPresent()){
            Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());
            ingredientFound.setUnitOfMeasure(unitOfMeasureService.findById(command.getUnitOfMeasure().getId())
            .orElseThrow(()->new RuntimeException("UOM NOT FOUND")));
        }else{
            recipe.addIngredient(ingredientCommandToIngredient.convert(command));
        }

        Recipe savedRecipe = recipeService.save(recipe);

        //to check for final

        return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
        .filter(ingredient -> ingredient.getId().equals(command.getId()))
        .findFirst()
        .get());

    }
}
