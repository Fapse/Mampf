package com.fapse.mampf.model;

import java.time.LocalDate;
import java.util.Random;

import com.fapse.mampf.model.DaySchedule;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class MampfData {
	private final ObservableSet<DaySchedule> daySchedules = FXCollections.observableSet();
	
	public MampfData() {
		generateTestData();
	}
	
	
	
	private void generateTestData() {
		Recipe spatzen = new Recipe("Käsespatzen", "Rühren, hobeln, kochen");
		Recipe pommes = new Recipe("Pommes", "Schnippeln, fritieren");
		Recipe kuchen = new Recipe("Kuchen", "Rühren, backen");
		Recipe salat = new Recipe("Chefsalat", "Schneiden, anmachen");
		Recipe brotzeit = new Recipe("Brotzeit", "Schneiden, belegen");
		Meal[] meals = {new Meal(spatzen), new Meal(pommes), 
				new Meal(kuchen), new Meal(salat), 
				new Meal(brotzeit)};
		//generate daily schedules for each day in month
		LocalDate today = LocalDate.now();
		Random random = new Random();
		
		int monthLength = today.lengthOfMonth();
		for (int m = 1; m <= monthLength; m++) {
			LocalDate day = today.withDayOfMonth(m);
			DaySchedule daySchedule = new DaySchedule(day);
			for (int n = random.nextInt(5) + 1; n <= 5; n++) {
				daySchedule.addMeal(meals[random.nextInt(meals.length - 1)]);
			}
			daySchedules.add(daySchedule);
		}
	}
}
