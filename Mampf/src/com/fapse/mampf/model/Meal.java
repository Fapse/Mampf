package com.fapse.mampf.model;

import java.time.LocalDate;
import javafx.beans.property.ReadOnlySetWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class Meal {
	private Recipe recipe;
	private final ObservableSet<LocalDate> dateSet = FXCollections.observableSet();
	private final ReadOnlySetWrapper<LocalDate> readOnlyDateSet = new ReadOnlySetWrapper<>(dateSet);
	private final StringProperty recipeName = new SimpleStringProperty();
	public Meal(Recipe recipe, LocalDate[] dates) {
		this.recipe = recipe;
		for (LocalDate date : dates) {
			dateSet.add(date);
		}
		recipeName.bind(recipe.nameProperty());
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
	public void addDate(LocalDate date) {
		dateSet.add(date);
	}
	public final ReadOnlySetWrapper<LocalDate> getDateSetWrapper() {
		return readOnlyDateSet;
	}
}
