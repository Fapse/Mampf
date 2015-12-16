package com.fapse.mampf.model;

import com.fapse.mampf.model.Meal;

public class RecipeNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8214606644454690572L;
	private String recipeName;
	private String recipeUID;

	public RecipeNotFoundException(Meal meal) {
		super("No recipe found for " + meal.getRecipeName() 
			+ " on " + meal.getCookDate().toString());
		recipeName = meal.getRecipeName();
		recipeUID = meal.getRecipeUID();
	}

	public String getRecipeName() {
		return recipeName;
	}

	public String getRecipeUID() {
		return recipeUID;
	}
}