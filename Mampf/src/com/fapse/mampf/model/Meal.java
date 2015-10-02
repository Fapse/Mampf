package com.fapse.mampf.model;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Meal {
	private SimpleStringProperty recipeName;
	private Recipe recipe;
	private final ObjectProperty<LocalDate> date;
	public Meal(Recipe recipe, LocalDate date) {
		this.recipe = recipe;
		this.date = new SimpleObjectProperty<LocalDate>(date);
		StringProperty sp = recipe.nameProperty();
		System.out.println("Hier gehts: " + sp);
		this.recipeName.bind(recipe.nameProperty());
		System.out.println("Hier gehts nimmer!");
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
		return recipeName.get();
	}
	public ObjectProperty<LocalDate> dateProperty() {
		return date;
	}
	public String getDate() {
		return date.toString();
	}
	public void setDate(LocalDate date) {
		try {
			this.date.set(date);
		} catch (Exception e) {
			//ignore
		}
	}
}
