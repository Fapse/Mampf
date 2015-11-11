package com.fapse.mampf.model;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javafx.collections.FXCollections;

public class Meal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8133898215487523746L;
	private final UUID uuid;
	private Recipe recipe;
	private String recipeUID;
	private Set<LocalDate> dates = FXCollections.observableSet();
	private int serving = 1;

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
	@Override
	public String toString() {
		return recipe.getName() + " (" + serving + ")";
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
	public int getServing() {
		return serving;
	}
	public void setServing(int serving) {
		this.serving = serving;
	}
	public boolean isMeal(Meal tmpMeal) {
		return this.uuid.equals(tmpMeal.getUUID());
	}
	public boolean isCookDay(LocalDate date) {
		List<LocalDate> tmpList = new ArrayList<>();
		tmpList.addAll(dates);
		Collections.sort(tmpList);
		System.out.println("Vergleiche " + date + " mit " + tmpList.get(0));
		return date.equals(tmpList.get(0));
	}
}