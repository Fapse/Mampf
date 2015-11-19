package com.fapse.mampf.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
			if (recipe.equals(tmpRecipe)) {
				position = recipes.indexOf(recipe);
				break;
			}
		}
		if (size == 0) {
			return null;
		}
		position += next;
		if (position >= size) {
			return recipes.get(0);
		} else if (position < 0) {
			return recipes.get(size - 1);
		} else {
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
				changedDates.add(changedDates.size(), date);
				if(tmpMeal.getDateCount() == 0) {
					meals.remove(tmpMeal);
				} else {
					MampfStorage.saveMeals(meals);
					for (LocalDate tmpDate : tmpMeal.getDates()) {
						changedDates.add(changedDates.size(), tmpDate);
					}
				}
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
	public void setMealServing(Meal meal, int serving) {
		for (Meal tmpMeal : meals) {
			if (tmpMeal.isMeal(meal)) {
				tmpMeal.setServing(serving);
				MampfStorage.saveMeals(meals);
				for (LocalDate date : tmpMeal.getDates()) {
					changedDates.add(changedDates.size(), date);
				}
				break;
			}
		}
		return;
	}
	public List<Condiment> getShoppingList(LocalDate date, int day) {
		List<Condiment> shoppingList = new ArrayList<>();
		for (int n = 0; n < day; n++) {
			for (Meal meal : meals) {
				if(meal.isCookDay(date.plusDays(n))) {
					for (Condiment foundCond : meal.getRecipe().getCondiments()) {
						if (shoppingList.contains(foundCond)) {
							int pos = shoppingList.indexOf(foundCond);
							Condiment oldListCond = shoppingList.get(pos);
							shoppingList.remove(pos);
							int amount;
							amount = Integer.parseInt(oldListCond.getAmount());
							amount += Integer.parseInt(foundCond.getAmount());
							Condiment newListCond = new Condiment(oldListCond.getName(),
									String.valueOf(amount),
									oldListCond.getUnit(),
									oldListCond.getCategory());
							shoppingList.add(newListCond);
						} else {
							shoppingList.add(foundCond);
						}
					}
				}
			}
		}
		return Collections.unmodifiableList(shoppingList);
	}
}