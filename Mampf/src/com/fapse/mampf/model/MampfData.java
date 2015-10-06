package com.fapse.mampf.model;

import java.time.LocalDate;
import java.util.Random;
import com.fapse.mampf.model.DaySchedule;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;

import javafx.beans.property.ReadOnlySetWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class MampfData {
	private final ObservableSet<DaySchedule> daySchedules = FXCollections.observableSet();
	private final ReadOnlySetWrapper<DaySchedule> readOnlyDaySchedulesSet = new ReadOnlySetWrapper<>(daySchedules);
	
	private static class MampfDataHolder{
		public static MampfData mampfData = new MampfData();
	}
	
	public static MampfData getMampfData() {
		return MampfDataHolder.mampfData;
	}
		
	private MampfData() {
		generateTestData();
	}
	
	public DaySchedule getDaySchedule(LocalDate date) {
		DaySchedule daySchedule = null;
		for (DaySchedule schedule : daySchedules) {
			if (date.equals(schedule.getDate())) {
				daySchedule = schedule;
			}
		}
		return daySchedule;
	}
	
	public ReadOnlySetWrapper<DaySchedule> getDaySchedulesSetWrapper() {
		return readOnlyDaySchedulesSet;
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
