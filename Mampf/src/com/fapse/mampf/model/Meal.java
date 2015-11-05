package com.fapse.mampf.model;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Meal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4385200737096306723L;
	private Recipe recipe;
	private String recipeUID;
	private Set<LocalDate> dates;

	public Meal(Recipe recipe) {
		this.recipe = recipe;
		this.recipeUID = recipe.getUID();
	}
	public Meal(Recipe recipe, String uid) {
		this.recipe = recipe;
		this.recipeUID = uid;
	}
	public String getRecipeUID() {
		return recipeUID;
	}
	public void addDate(LocalDate date) {
		dates.add(date);
	}
	public void setDates(List<LocalDate> dates) {
		if (dates != null) {
			this.dates = new HashSet<LocalDate>(dates);
		}
	}
	public void removeDate(LocalDate date) {
		dates.remove(date);
	}
	public List<LocalDate> getDates() {
		List<LocalDate> tmpDates = new ArrayList<>();
		tmpDates.addAll(dates);
		return tmpDates;
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