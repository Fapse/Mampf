package com.fapse.mampf.model;

import java.time.LocalDate;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static com.fapse.mampf.model.MealPredicates.*;

public class MampfData {
	private final ObservableList<Meal> mealList = FXCollections.observableArrayList();
	private final ReadOnlyListWrapper<Meal> readOnlyMealList = new ReadOnlyListWrapper<>(mealList);
	
	private static class MampfDataHolder{
		public static MampfData mampfData = new MampfData();
	}
	public static MampfData getMampfData() {
		return MampfDataHolder.mampfData;
	}
	private MampfData() {
		generateTestData2();
	}
	public ReadOnlyListWrapper<Meal> getMeals() {
		return readOnlyMealList;
	}
	public ReadOnlyListWrapper<Meal> getMeals(LocalDate date) {
		return new ReadOnlyListWrapper<Meal>(filterMeals(mealList, hasDate(date)));
	}
	private void generateTestData2() {
		Recipe spatzen = new Recipe("Käsespatzen", "Rühren, hobeln, kochen");
		Recipe pommes = new Recipe("Pommes", "Schnippeln, fritieren");
		Recipe kuchen = new Recipe("Kuchen", "Rühren, backen");
		Recipe salat = new Recipe("Chefsalat", "Schneiden, anmachen");
		Recipe brotzeit = new Recipe("Brotzeit", "Schneiden, belegen");
		LocalDate day1 = LocalDate.of(2015, 10, 4);
		LocalDate day2 = LocalDate.of(2015, 10, 5);
		LocalDate day3 = LocalDate.of(2015, 10, 6);
		LocalDate day4 = LocalDate.of(2015, 10, 7);
		LocalDate day5 = LocalDate.of(2015, 10, 7);
		LocalDate day6 = LocalDate.of(2015, 10, 9);
		LocalDate[] days1 = {day1, day2, day3};
		LocalDate[] days2 = {day3, day4, day5, day6};
		LocalDate[] days3 = {day3, day5, day6};	
		Meal[] meals = {new Meal(spatzen, days1),
				new Meal(pommes, days2), 
				new Meal(kuchen, days3),
				new Meal(salat, days3), 
				new Meal(brotzeit, days1)};
		for (Meal meal : meals) {
			mealList.add(meal);
		}
	}
}