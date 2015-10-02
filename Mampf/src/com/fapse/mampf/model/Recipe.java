package com.fapse.mampf.model;

public class Recipe {
	private String name;
	private String recipe;
	
	public Recipe() {
	}
	public Recipe(String name, String recipe) {
		this.name = name;
		this.recipe = recipe;
	}
	String getName() {
		return name;
	}
	void setName(String name) {
		this.name = name;
	}
	String getRecipe() {
		return recipe;
	}
	void setRecipe(String recipe) {
		this.recipe = recipe;
	}
}