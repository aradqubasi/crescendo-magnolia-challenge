package com.crescendocollective.recipes;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RecipeClientTest {
	
	RecipeClient recipeClient;

	@Before
	public void setUp() throws Exception {
		recipeClient = new RecipeClient();
	}

	@Test
	public void testGetRecipes() {
		List<Recipe> recipies = recipeClient.getRecipes();
		System.out.println(recipies);
	}

}
