package com.monica.recipe.services;

import com.monica.recipe.converters.RecipeCommandToRecipe;
import com.monica.recipe.converters.RecipeToRecipeCommand;
import com.monica.recipe.models.Recipe;
import com.monica.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);

    }

    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe returned = recipeService.findById(1L).orElse(null);

        assertNotNull("Null recipe returned", returned);
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository, never()).findAll();


    }

    @Test
    public void getAllRecipes() {

        Recipe recipe = new Recipe();

        List<Recipe> recipesData = new ArrayList<>();

        recipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getAllRecipes();

        assertEquals(recipes.size(), 1);

        verify(recipeRepository, times(1)).findAll();

    }


    @Test
    public void testDeleteById() {
        Long idToDelete = 1L;

        recipeService.deleteById(idToDelete);

        verify(recipeRepository).deleteById(anyLong());
    }
}