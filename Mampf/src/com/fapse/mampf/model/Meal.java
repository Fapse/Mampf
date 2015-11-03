package com.fapse.mampf.model;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Meal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4385200737096306723L;
	private Recipe recipe;
	private final UUID recipeUUID;
	private Set<LocalDate> dates;

	public Meal(Recipe recipe) {
		this.recipe = recipe;
		this.recipeUUID = recipe.getUUID();
	}
	public Meal(Recipe recipe, UUID uuid) {
		this.recipe = recipe;
		this.recipeUUID = uuid;
	}
	public UUID getRecipeUUID() {
		return recipeUUID;
	}
	public void addDate(LocalDate date) {
		dates.add(date);
	}
	public void removeDate(LocalDate date) {
		dates.remove(date);
	}
	public Set<LocalDate> getDate() {
		return dates;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public String getRecipeName() {
		return recipe.getName();
	}
}