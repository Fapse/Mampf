package com.fapse.mampf.model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlySetWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class DaySchedule {
	private Recipe recipe;
	private final ObjectProperty<LocalDate> cookDate = new SimpleObjectProperty<>();
	private final ObservableSet<LocalDate> eatDatesSet = FXCollections.observableSet();
	private final ReadOnlySetWrapper<LocalDate> readOnlyDateSet = new ReadOnlySetWrapper<>(eatDatesSet);
	private final StringProperty recipeName = new SimpleStringProperty();
	public DaySchedule(Recipe recipe, LocalDate cookDate, LocalDate[] eatDates) {
		this.recipe = recipe;
		this.cookDate.set(cookDate);
		for (LocalDate date : eatDates) {
			eatDatesSet.add(date);
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
	public void setCookDate(LocalDate date) {
		cookDate.set(date);
	}
	public void addEatDate(LocalDate date) {
		eatDatesSet.add(date);
	}
	public final ReadOnlySetWrapper<LocalDate> getDateSetWrapper() {
		return readOnlyDateSet;
	}
}
