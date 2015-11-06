package com.fapse.mampf.model;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javafx.collections.FXCollections;

public class Meal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8995103666441202658L;
	private final UUID uuid;
	private Recipe recipe;
	private String recipeUID;
	private Set<LocalDate> dates = FXCollections.observableSet();

	public Meal(Recipe recipe) {
		this.uuid = UUID.randomUUID();
		this.recipe = recipe;
		this.recipeUID = recipe.getUID();
	}
	public Meal(Recipe recipe, String uid) {
		this.uuid = UUID.randomUUID();
		this.recipe = recipe;
		this.recipeUID = uid;
	}
	public String getRecipeUID() {
		return recipeUID;
	}
	public UUID getUUID() {
		return uuid;
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
	public int getDateCount() {
		return dates.size();
	}
	public boolean hasDate(LocalDate date) {
		return this.dates.contains(date);
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
	public boolean isMeal(Meal tmpMeal) {
		return this.uuid.equals(tmpMeal.getUUID());
	}
}