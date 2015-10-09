package com.fapse.mampf.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Meal {
	private final Recipe recipe;
	private final StringProperty recipeName = new SimpleStringProperty();

	public Meal(Recipe recipe) {
		this.recipe = recipe;
		recipeName.bind(recipe.nameProperty());
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public StringProperty recipeNameProperty() {
		return recipeName;
	}
	public String getRecipeName() {
		return recipe.getName();
	}
}