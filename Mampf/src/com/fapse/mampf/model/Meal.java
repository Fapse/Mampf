package com.fapse.mampf.model;

import java.io.Serializable;

public class Meal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6388134646283462893L;
	private final Recipe recipe;

	public Meal(Recipe recipe) {
		this.recipe = recipe;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public String getRecipeName() {
		return recipe.getName();
	}
}