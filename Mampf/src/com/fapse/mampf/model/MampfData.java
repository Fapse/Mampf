package com.fapse.mampf.model;

import java.time.LocalDate;

import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import static com.fapse.mampf.model.MealActionPredicates.*;

public class MampfData {
	private final ObservableList<Meal> meals = FXCollections.observableArrayList();
	private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();
	
	private static class MampfDataHolder{
		public static MampfData mampfData = new MampfData();
	}
	public static MampfData getMampfData() {
		return MampfDataHolder.mampfData;
	}
	private MampfData() {
		recipes.addAll(MampfStorage.loadRecipes());
		if (recipes.isEmpty()) {
			recipes.add(new Recipe("Käsespatzen", "Rühren, hobeln, kochen"));
			recipes.add(new Recipe("Brotzeit", "Schneiden, belegen"));
			recipes.add(new Recipe("Pommes", "Schnippeln, fritieren"));
			MampfStorage.saveRecipes(recipes);
		}
		meals.addAll(MampfStorage.loadMeals());
		meals.addListener(new ListChangeListener<MealAction>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends MealAction> c) {
				MampfStorage.saveMealActions(meals);				
			}
		});
		recipes.addListener(new ListChangeListener<Recipe>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Recipe> c) {
				MampfStorage.saveRecipes(recipes);				
			}
		});
	}
	public ReadOnlyListWrapper<Meal> getMeals() {
		return new ReadOnlyListWrapper<>(meals);
	}
	public ReadOnlyListWrapper<Recipe> getRecipes() {
		return new ReadOnlyListWrapper<>(recipes);
	}
	public ReadOnlyListWrapper<Meal> getMeals(LocalDate date) {
		ObservableList<MealAction> mealActionsList = FXCollections.observableArrayList();
		mealActionsList = filterMeals(meals, hasDate(date));
        ObservableList<Meal> obsMeals = FXCollections.observableArrayList();
        for (MealAction mealAction : mealActionsList) {
        	obsMeals.add(mealAction.meal);
        }
        return new ReadOnlyListWrapper<Meal>(obsMeals);
    }
	public void deleteMeal(Meal meal) {
		ObservableList<MealAction> mealActionsList = FXCollections.observableArrayList();
		mealActionsList = MealActionPredicates.filterMeals(meals, isMeal(meal));
		for(MealAction mealAction : mealActionsList) {
			if (mealActionsList.contains(mealAction)) {
				meals.remove(mealAction);
			}
		}
	}
	public void addMealDay(Meal meal, LocalDate date) {
		for (Meal tmpMeal : meals) {
			if (tmpMeal.isMeal(meal) && tmpMeal.date == date) {
				return;
			}
		}
		meals.add(new MealAction(meal, date));			
	}
	public void addNewMeal(Recipe recipe, LocalDate date) {
		Meal meal = new Meal(recipe);
		MealAction mealAction = new MealAction(meal, date);
		meals.add(mealAction);
		System.out.println("Neue Mahlzeit hinzugefügt");
	}
}