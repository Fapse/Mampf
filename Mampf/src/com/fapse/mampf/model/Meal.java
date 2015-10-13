package com.fapse.mampf.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Meal implements Serializable {
	private static final long serialVersionUID = 8128305838659559661L;
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