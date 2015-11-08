package com.fapse.mampf.model;

import java.time.LocalDate;

import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import static com.fapse.mampf.model.MealPredicates.*;

public class MampfData {
	private final ObservableList<Meal> meals = FXCollections.observableArrayList();
	private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();
	private final ObservableList<LocalDate> changedDates = FXCollections.observableArrayList();
	private static class MampfDataHolder{
		public static MampfData mampfData = new MampfData();
	}
	public static MampfData getMampfData() {
		return MampfDataHolder.mampfData;
	}
	private MampfData() {
		recipes.addAll(MampfStorage.loadRecipes());
		meals.addAll(MampfStorage.loadMeals());
		meals.addListener(new ListChangeListener<Meal>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Meal> c) {
				MampfStorage.saveMeals(meals);				
			}
		});
	}
	public ReadOnlyListWrapper<LocalDate> getChangedDates() {
		return new ReadOnlyListWrapper<>(changedDates);
	}
	public ReadOnlyListWrapper<Meal> getMeals() {
		return new ReadOnlyListWrapper<>(meals);
	}
	public ReadOnlyListWrapper<Recipe> getRecipes() {
		return new ReadOnlyListWrapper<>(recipes);
	}
	public Recipe getNextRecipe(Recipe recipe, int next) {
		int position = 0;
		int size = recipes.size();
		for (Recipe tmpRecipe : recipes) {
			System.out.println("Rezept: " + tmpRecipe.getName());
			if (recipe.equals(tmpRecipe)) {
				System.out.println("Gefunden");
				position = recipes.indexOf(recipe);
				break;
			}
		}
		if (size == 0) {
			return null;
		}
		System.out.println("Element gefunden bei " + position);
		position += next;
		System.out.println("Die gesuchte Position: " + position);
		System.out.println("Die Größe von recipes: " + size);
		if (position >= size) {
			System.out.println("Überlauf: gebe erstes Element zurück");
			return recipes.get(0);
		} else if (position < 0) {
			System.out.println("Unterlauf: gebe letztes Element zurück");
			return recipes.get(size - 1);
		} else {
			System.out.println("Gebe Element in der Mitte zurück");
			return recipes.get(position);
		}
	}
	public ReadOnlyListWrapper<Meal> getMeals(LocalDate date) {
		ObservableList<Meal> mealsList = FXCollections.observableArrayList();
		mealsList = filterMeals(meals, hasDate(date));
        ObservableList<Meal> obsMeals = FXCollections.observableArrayList();
        for (Meal meal : mealsList) {
        	obsMeals.add(meal);
        }
        return new ReadOnlyListWrapper<Meal>(obsMeals);
    }
	public void deleteMealDay(Meal meal, LocalDate date) {
		for (Meal tmpMeal : meals) {
			if (tmpMeal.isMeal(meal)) {
				tmpMeal.removeDate(date);
				if(tmpMeal.getDateCount() == 0) {
					meals.remove(tmpMeal);
				} else {
					MampfStorage.saveMeals(meals);
				}
				changedDates.add(changedDates.size(), date);
				break;
			}
		}
	}
	public void addMealDay(Meal meal, LocalDate date) {
		for (Meal tmpMeal : meals) {
			if (tmpMeal.isMeal(meal)) {
				tmpMeal.addDate(date);
				MampfStorage.saveMeals(meals);
				changedDates.add(changedDates.size(), date);
				break;
			}
		}
		return;
	}
	public void addNewMeal(Recipe recipe, LocalDate date) {
		Meal meal = new Meal(recipe);
		meal.addDate(date);
		meals.add(meal);
		changedDates.add(changedDates.size(), date);
	}
}