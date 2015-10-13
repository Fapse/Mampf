package com.fapse.mampf.model;

import java.io.Serializable;

public class Recipe implements Serializable {
	private static final long serialVersionUID = -5448591540768775900L;
	private final String name;
	private final String recipe;
	
	public Recipe(String name) {
		this.name = name;
		this.recipe = "No recipe available!";
	}
	public Recipe(String name, String recipe) {
		this.name = name;
		this.recipe = recipe;
	}
	public String getName() {
		return name;
	}
	public String getRecipe() {
		return recipe;
	}
}