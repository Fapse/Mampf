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
	private final ObservableList<MealAction> mealActions = FXCollections.observableArrayList();
	private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();
	private final ObservableList<Condiment> condiments = FXCollections.observableArrayList();
	
	private static class MampfDataHolder{
		public static MampfData mampfData = new MampfData();
	}
	public static MampfData getMampfData() {
		return MampfDataHolder.mampfData;
	}
	private MampfData() {
		condiments.addAll(MampfStorage.loadCondiments());
		recipes.addAll(MampfStorage.loadRecipes());
		mealActions.addAll(MampfStorage.loadMealActions());
		mealActions.addListener(new ListChangeListener<MealAction>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends MealAction> c) {
				MampfStorage.saveMealActions(mealActions);				
			}
		});
		recipes.addListener(new ListChangeListener<Recipe>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Recipe> c) {
				MampfStorage.saveRecipes(recipes);				
			}
		});
		condiments.addListener(new ListChangeListener<Condiment>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Condiment> c) {
				MampfStorage.saveCondiments(condiments);				
			}
		});
	}
	public ReadOnlyListWrapper<MealAction> getMeals() {
		return new ReadOnlyListWrapper<>(mealActions);
	}
	public ReadOnlyListWrapper<Recipe> getRecipes() {
		return new ReadOnlyListWrapper<>(recipes);
	}
	public ReadOnlyListWrapper<Condiment> getCondiments() {
		return new ReadOnlyListWrapper<>(condiments);
	}
	public ReadOnlyListWrapper<Meal> getMeals(LocalDate date) {
		ObservableList<MealAction> mealActionsList = FXCollections.observableArrayList();
		mealActionsList = filterMeals(mealActions, hasDate(date));
        ObservableList<Meal> obsMeals = FXCollections.observableArrayList();
        for (MealAction mealAction : mealActionsList) {
        	obsMeals.add(mealAction.meal);
        }
        return new ReadOnlyListWrapper<Meal>(obsMeals);
    }
	public void deleteMeal(Meal meal) {
		ObservableList<MealAction> mealActionsList = FXCollections.observableArrayList();
		mealActionsList = MealActionPredicates.filterMeals(mealActions, isMeal(meal));
		for(MealAction mealAction : mealActionsList) {
			if (mealActionsList.contains(mealAction)) {
				mealActions.remove(mealAction);
			}
		}
	}
	public void addMealDay(Meal meal, LocalDate date) {
		for (MealAction mealAction : mealActions) {
			if (mealAction.isMeal(meal) && mealAction.date == date) {
				return;
			}
		}
		mealActions.add(new MealAction(meal, date));			
	}
	public void addNewMeal(Recipe recipe, LocalDate date) {
		Meal meal = new Meal(recipe);
		MealAction mealAction = new MealAction(meal, date);
		mealActions.add(mealAction);
		System.out.println("Neue Mahlzeit hinzugefügt");
	}
	public void addCondiment(Condiment condiment) {
		if (!condiments.contains(condiment.getName())) {
			condiments.add(condiment);
		}
	}
	public void deleteCondiment(Condiment condiment) {
		for (Condiment cond : condiments) {
			if (cond.getName().equals(condiment.getName())) {
				condiments.remove(cond);
				return;
			}
		}
	}
}