package guru.springframework.services;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipesTest() {

        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void findRecipeByIdTest() {

        // given
        final Long RECIPE_ID = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        when(recipeRepository.findById(RECIPE_ID)).thenReturn(Optional.of(recipe));

        // when
        Recipe recipeActual = recipeService.findById(RECIPE_ID);

        // then
        assertEquals(recipe, recipeActual);
    }

    @Test
    public void deleteRecipeByIdTest() {
        // given
        final Long RECIPE_ID = Long.valueOf(2L);

        // when
        recipeService.deleteById(RECIPE_ID);

        // then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}