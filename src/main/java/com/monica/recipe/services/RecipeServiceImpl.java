package com.monica.recipe.services;

import com.monica.recipe.commands.RecipeCommand;
import com.monica.recipe.converters.RecipeCommandToRecipe;
import com.monica.recipe.converters.RecipeToRecipeCommand;
import com.monica.recipe.models.Recipe;
import com.monica.recipe.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public Set<Recipe> getAllRecipes() {

        log.debug("Getting all recipies");
        Set<Recipe> recipes = new HashSet<>();

        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {

        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Save RecipeId: "+savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isEmpty()) {
            throw new RuntimeException("Recipe is not found");
        }

        return recipeToRecipeCommand.convert(recipe.get());
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}
