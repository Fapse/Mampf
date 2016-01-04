package com.fapse.mampf.model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;
import com.fapse.mampf.util.EnrichableException;
import com.fapse.mampf.util.ExceptionHandler;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import static com.fapse.mampf.model.MealPredicates.*;

public class MampfData {
	private final ObservableList<Meal> meals = FXCollections.observableArrayList();
	private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();
	private final ObservableList<LocalDate> changedDates = FXCollections.observableArrayList();

    protected ExceptionHandler exceptionHandler = new ExceptionHandler(){
        public void handle(String errorContext, String errorCode,
                           String errorText, Throwable t){

            if(! (t instanceof EnrichableException)){
                throw new EnrichableException(
                    errorContext, errorCode, errorText, t);
            } else {
                ((EnrichableException) t).addInfo(
                    errorContext, errorCode, errorText);
            }
        }

        public void raise(String errorContext, String errorCode,
                          String errorText){
            throw new EnrichableException(
                errorContext, errorCode, errorText);
        }
    };
	
	private static class MampfDataHolder{
		public static MampfData mampfData = new MampfData();
	}
	public static MampfData getMampfData() {
		return MampfDataHolder.mampfData;
	}
	private MampfData() {
		
		try {
			recipes.addAll(ResourceBuilder.loadRecipes());
		} catch (IOException e) {
			System.exit(1);
		}
		try {
			meals.addAll(MampfStorage.loadMeals());
		} catch (RecipeNotFoundException e) {
			this.exceptionHandler.handle("meals.addAll(MampfStorage.loadMeals())",
					"RecipeNotFoundException", "Could not find recipe", e);
			System.exit(1);			
		} catch (IOException e) {
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.exit(1);			
		}
		meals.addListener(new ListChangeListener<Meal>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Meal> c) {
				try {
					MampfStorage.saveMeals(meals);
				} catch (IOException e) {
					System.exit(1);
				}				
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
	public List<Meal> getMeals(LocalDate date) {
		List<Meal> mealsList = filterMeals(meals, hasDate(date));
        return Collections.unmodifiableList(mealsList);
    }
	public void deleteMealDay(Meal meal, LocalDate date) {
		for (Meal tmpMeal : meals) {
			if (tmpMeal.isMeal(meal)) {
				tmpMeal.removeDate(date);
				changedDates.add(changedDates.size(), date);
				if(tmpMeal.getDateCount() == 0) {
					meals.remove(tmpMeal);
				} else {
					try {
						MampfStorage.saveMeals(meals);
					} catch (IOException e) {
						System.exit(1);
					}
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
				try {
					MampfStorage.saveMeals(meals);
				} catch (IOException e) {
					System.exit(1);
				}
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
	public void printShoppingList(String text) {
		Printer.printText(text);
	}
	public void setMealServing(Meal meal, int serving) {
		for (Meal tmpMeal : meals) {
			if (tmpMeal.isMeal(meal)) {
				tmpMeal.setServing(serving);
				try {
					MampfStorage.saveMeals(meals);
				} catch (IOException e) {
					System.exit(1);
				}
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
							amount += (Integer.parseInt(foundCond.getAmount())) * meal.getServing();
							Condiment newListCond = new Condiment(oldListCond);
							newListCond.setAmount(String.valueOf(amount));
							shoppingList.add(newListCond);
						} else {
							Condiment tmpCondiment = new Condiment(foundCond);
							tmpCondiment.setAmount(String.valueOf(Integer.parseInt(foundCond.getAmount()) * meal.getServing()));
							shoppingList.add(tmpCondiment);
						}
					}
				}
			}
		}
		return Collections.unmodifiableList(shoppingList);
	}
}