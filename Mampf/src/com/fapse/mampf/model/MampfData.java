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
	private final ReadOnlyListWrapper<MealAction> readOnlyMealActions = new ReadOnlyListWrapper<>(mealActions);
	private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();
	private final ReadOnlyListWrapper<Recipe> readOnlyRecipes = new ReadOnlyListWrapper<>(recipes);
	
	private static class MampfDataHolder{
		public static MampfData mampfData = new MampfData();
	}
	public static MampfData getMampfData() {
		return MampfDataHolder.mampfData;
	}
	private MampfData() {
		//generateTestData2();
		//MampfStorage.saveMealActions(mealActions);				
		//MampfStorage.saveRecipes(recipes);
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
	}
	public ReadOnlyListWrapper<MealAction> getMeals() {
		return readOnlyMealActions;
	}
	public ReadOnlyListWrapper<Recipe> getRecipes() {
		return readOnlyRecipes;
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
	private void generateTestData2() {
		Recipe spatzen = new Recipe("Käsespatzen", "Rühren, hobeln, kochen");
		Recipe pommes = new Recipe("Pommes", "Schnippeln, fritieren");
		Recipe kuchen = new Recipe("Kuchen", "Rühren, backen");
		Recipe salat = new Recipe("Chefsalat", "Schneiden, anmachen");
		Recipe brotzeit = new Recipe("Brotzeit", "Schneiden, belegen");
		recipes.addAll(spatzen, pommes, kuchen, salat, brotzeit);
	}
}