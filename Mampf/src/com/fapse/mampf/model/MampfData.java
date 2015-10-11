package com.fapse.mampf.model;

import java.time.LocalDate;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static com.fapse.mampf.model.MealActionPredicates.*;

public class MampfData {
	private final ObservableList<MealAction> mealActions = FXCollections.observableArrayList();
	private final ReadOnlyListWrapper<MealAction> readOnlyMealActions = new ReadOnlyListWrapper<>(mealActions);
	
	private static class MampfDataHolder{
		public static MampfData mampfData = new MampfData();
	}
	public static MampfData getMampfData() {
		return MampfDataHolder.mampfData;
	}
	private MampfData() {
		generateTestData2();
	}
	public ReadOnlyListWrapper<MealAction> getMeals() {
		return readOnlyMealActions;
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
	
	public void addMealAction() {
		Recipe schnitzel = new Recipe("Schnitzel", "Klopfen, braten");
		LocalDate day1 = LocalDate.of(2015, 10, 8);
		LocalDate day2 = LocalDate.of(2015, 10, 9);
		Meal meal = new Meal(schnitzel);
		MealAction mealAction1 = new MealAction(meal, day1);
		MealAction mealAction2 = new MealAction(meal, day2);
		System.out.println("Jetzt neue Mahlzeit machen");
		mealActions.addAll(mealAction1, mealAction2);
		System.out.println("Neue Mahlzeit hinzugefügt");
	}
	private void generateTestData2() {
		Recipe spatzen = new Recipe("Käsespatzen", "Rühren, hobeln, kochen");
		Recipe pommes = new Recipe("Pommes", "Schnippeln, fritieren");
		Recipe kuchen = new Recipe("Kuchen", "Rühren, backen");
		Recipe salat = new Recipe("Chefsalat", "Schneiden, anmachen");
		Recipe brotzeit = new Recipe("Brotzeit", "Schneiden, belegen");
		Meal meal1 = new Meal(spatzen);
		Meal meal2 = new Meal(pommes);
		Meal meal3 = new Meal(kuchen);
		Meal meal4 = new Meal(salat);
		Meal meal5 = new Meal(brotzeit);
		LocalDate day1 = LocalDate.of(2015, 10, 4);
		LocalDate day2 = LocalDate.of(2015, 10, 5);
		LocalDate day3 = LocalDate.of(2015, 10, 6);
		LocalDate day4 = LocalDate.of(2015, 10, 7);
		LocalDate day5 = LocalDate.of(2015, 10, 7);
		LocalDate day6 = LocalDate.of(2015, 10, 9);
		MealAction mealAction1 = new MealAction(meal1, day1);
		MealAction mealAction2 = new MealAction(meal1, day2);
		MealAction mealAction3 = new MealAction(meal1, day3);
		MealAction mealAction4 = new MealAction(meal2, day3);
		MealAction mealAction5 = new MealAction(meal2, day4);
		MealAction mealAction6 = new MealAction(meal3, day4);
		MealAction mealAction7 = new MealAction(meal4, day5);
		MealAction mealAction8 = new MealAction(meal5, day5);
		MealAction mealAction9 = new MealAction(meal5, day6);
		mealActions.addAll(mealAction1, mealAction2, mealAction3, mealAction4, 
				mealAction5, mealAction6, mealAction7, mealAction8, mealAction9);
	}
}