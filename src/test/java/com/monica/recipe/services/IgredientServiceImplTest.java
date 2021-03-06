package com.monica.recipe.services;

import com.monica.recipe.commands.IngredientCommand;
import com.monica.recipe.converters.IngredientCommandToIngredient;
import com.monica.recipe.converters.IngredientToIngredientCommand;
import com.monica.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.monica.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.monica.recipe.models.Ingredient;
import com.monica.recipe.models.Recipe;
import com.monica.recipe.repositories.IngredientRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IgredientServiceImplTest {

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    RecipeService recipeService; //= new RecipeServiceImpl(recipeRepository, )

    IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

    IngredientServiceImpl ingredientService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService = new IngredientServiceImpl(recipeService, ingredientRepository, ingredientToIngredientCommand, unitOfMeasureService, ingredientCommandToIngredient);

    }

    @Test
    public void findByRecipeIdAndId() {

        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        //when
        when(recipeService.findById(anyLong())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        assertNotNull(ingredientCommand);
        assertEquals(Long.valueOf(3), ingredientCommand.getId());
        assertEquals(Long.valueOf(1), recipe.getId());
        verify(recipeService).findById(anyLong());

    }


    @Test
    public void testSaveRecipeCommand() {

        //given
        Recipe recipe = new Recipe();
        recipe.setId(2L);

        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        //when
        when(recipeService.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeService.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeService).findById(anyLong());
        verify(recipeService).save(any(Recipe.class));
    }
}