package com.fapse.mampf.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;
import com.fapse.mampf.util.ExceptionHandlerImpl;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import static com.fapse.mampf.model.MealPredicates.*;

public class MampfData {
	private static ExceptionHandlerImpl exceptionHandler = new ExceptionHandlerImpl();

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
		recipes.addAll(ResourceBuilder.loadRecipes());
		meals.addAll(MealStorage.loadMeals());
		meals.addListener(new ListChangeListener<Meal>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Meal> c) {
				MealStorage.saveMeals(meals);
			}
		});
	}
	private boolean isMealInList(Meal meal) {
		if (!meals.contains(meal)) {
			exceptionHandler.raise("model", "E3", "Meal " + meal.getUUID() + " could not be found in list: meals");
			return false;
		} else {
			return true;
		}
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
	public Set<Recipe> getRecipesPerCategory(String category) {
		Set<Recipe> categoryRecipes = new TreeSet<>();
		categoryRecipes = recipes.stream().filter(p -> p.getCategory().equals(category)).collect(Collectors.<Recipe>toSet());
		return Collections.unmodifiableSet(categoryRecipes);
	}
	public Set<String> getRecipeCategories() {
		Set<String> recipeCategories = new TreeSet<>();
		for (Recipe recipe : recipes) {
			recipeCategories.add(recipe.getCategory());
		}
		return Collections.unmodifiableSet(recipeCategories);
	}
	public Recipe getNextRecipe(Recipe recipe, int next) {
		// next < 0: previous recipe, next > 0: next recipe
		// number of recipes always > 0
		int index = recipes.indexOf(recipe);
		int size = recipes.size();
		index += next;
		if (index >= size) {
			return recipes.get(0);
		} else if (index < 0) {
			return recipes.get(size - 1);
		} else {
			return recipes.get(index);
		}
	}
	public List<Meal> getMeals(LocalDate date) {
		List<Meal> mealsOfTheDay = filterMeals(meals, hasDate(date));
        return Collections.unmodifiableList(mealsOfTheDay);
    }
	public void deleteMealDay(Meal meal, LocalDate date) {
		meal.removeDate(date);
		changedDates.add(changedDates.size(), date);
		if(meal.getDateCount() == 0) {
			meals.remove(meal); //item removed: meals saved automatically
		} else {
			MealStorage.saveMeals(meals);
			for (LocalDate tmpDate : meal.getDates()) {
				changedDates.add(changedDates.size(), tmpDate);
			}
		}
	}
	
	public void addMealDay(Meal meal, LocalDate date) {
		isMealInList(meal);
		meal.addDate(date);
		MealStorage.saveMeals(meals);
		changedDates.add(changedDates.size(), date);
	}
	
	public void addNewMeal(Recipe recipe, LocalDate date) {
		Meal meal = new Meal(recipe);
		meal.addDate(date);
		meals.add(meal);
		changedDates.add(changedDates.size(), date);
	}
	
	public void printShoppingList(String text) {
		Printer.printText(text);
	}
	
	public void setMealServing(Meal meal, int serving) {
		isMealInList(meal);
		meal.setServing(serving);
		MealStorage.saveMeals(meals);
		for (LocalDate date : meal.getDates()) {
			changedDates.add(changedDates.size(), date);
		}
	}
	
	public List<Condiment> getShoppingList(LocalDate date, int daysToShopFor) {
		List<Condiment> shoppingList = new ArrayList<>();
		List<LocalDate> dates = new ArrayList<>();
		List<Meal> mealsToShopFor = new ArrayList<>();
		for (int n = 0; n < daysToShopFor; n++) {
			dates.add(date.plusDays(n));
		}
		for (LocalDate dayToShopFor : dates) {
			mealsToShopFor.addAll(filterMeals(meals, isCookDay(dayToShopFor)));
		}
		for (Meal mealToShopFor : mealsToShopFor) {
			for (Condiment foundCond : mealToShopFor.getRecipe().getCondiments()) {
				if (shoppingList.contains(foundCond)) {
					int pos = shoppingList.indexOf(foundCond);
					Condiment oldListCond = shoppingList.get(pos);
					shoppingList.remove(pos);
					int amount;
					amount = Integer.parseInt(oldListCond.getAmount());
					amount += (Integer.parseInt(foundCond.getAmount())) * mealToShopFor.getServing();
					Condiment newListCond = new Condiment(oldListCond);
					newListCond.setAmount(String.valueOf(amount));
					shoppingList.add(newListCond);
				} else {
					Condiment tmpCondiment = new Condiment(foundCond);
					tmpCondiment.setAmount(String.valueOf(Integer.parseInt(foundCond.getAmount()) * mealToShopFor.getServing()));
					shoppingList.add(tmpCondiment);
				}
			}			
		}
		return Collections.unmodifiableList(shoppingList);
	}
}