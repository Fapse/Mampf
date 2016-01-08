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
	private static final long serialVersionUID = 6046956256366089350L;
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
		return recipe.getName();
	}

	public String getCookDayString() {
		if (recipe.getCondiments().isEmpty()) {
			return recipe.getName(); //no condiments, no cooking, no servings
		} else {
			return recipe.getName() + " (" + serving + ")";					
		}
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
		return Collections.unmodifiableList(tmpDates);
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
		return date.equals(tmpList.get(0));
	}

	public List<LocalDate> getRemainingMealDays(LocalDate date) {
		LocalDate cookDate = getCookDate();
		List<LocalDate> remainingMealDays = new ArrayList<>();
		int bestBefore = Integer.parseInt(recipe.getBestBefore());
		for (int n = 1; !cookDate.plusDays(bestBefore).isBefore(date.plusDays(n)); n++) {
			remainingMealDays.add(date.plusDays(n));
		}
		Collections.sort(remainingMealDays);
		return  Collections.unmodifiableList(remainingMealDays);
	}

	public LocalDate getCookDate() {
		List<LocalDate> tmpList = new ArrayList<>();
		tmpList.addAll(dates);
		Collections.sort(tmpList);		
		return tmpList.get(0);
	}
}