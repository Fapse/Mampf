package com.fapse.mampf.model;

import java.time.LocalDate;
import javafx.beans.property.ReadOnlySetWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class Meal {
	private Recipe recipe;
	private final StringProperty recipeName = new SimpleStringProperty();
	private final ObservableSet<LocalDate> datesSet = FXCollections.observableSet();
	private final ReadOnlySetWrapper<LocalDate> readOnlyDatesSet = new ReadOnlySetWrapper<>(datesSet);

	public Meal(Recipe recipe, LocalDate[] dates) {
		this.recipe = recipe;
		recipeName.bind(recipe.nameProperty());
		for (LocalDate date : dates) {
			datesSet.add(date);
		}
	}
	public boolean hasDate(LocalDate date) {
		if (datesSet.contains(date)) {
			return true;
		} else {
			return false;
		}
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
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
	public final ReadOnlySetWrapper<LocalDate> getDatesSetWrapper() {
		return readOnlyDatesSet;
	}
}