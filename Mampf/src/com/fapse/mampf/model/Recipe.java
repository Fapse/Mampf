package com.fapse.mampf.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Recipe {
	private final StringProperty name;
	private final StringProperty recipe;
	
	public Recipe(String name) {
		this.name = new SimpleStringProperty(name);
		this.recipe = new SimpleStringProperty("No recipe available!");
	}
	public Recipe(String name, String recipe) {
		this.name = new SimpleStringProperty(name);
		this.recipe = new SimpleStringProperty(recipe);
	}
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public String getRecipe() {
		return recipe.get();
	}
	public void setRecipe(String recipe) {
		this.recipe.set(recipe);
	}
	public StringProperty nameProperty() {
		return name;
	}
	public StringProperty recipeProperty() {
		return recipe;
	}
}