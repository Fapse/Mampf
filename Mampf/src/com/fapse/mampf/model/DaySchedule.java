package com.fapse.mampf.model;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlySetWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class DaySchedule {
	private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
	private final ObservableSet<Meal> mealsSet = FXCollections.observableSet();
	private final ReadOnlySetWrapper<Meal> readOnlyMealsSet = new ReadOnlySetWrapper<>(mealsSet);
	public DaySchedule(LocalDate date) {
		this.date.set(date);
	}	
	public DaySchedule(LocalDate date, Meal[] meals) {
		this.date.set(date);
		for (Meal meal : meals) {
			mealsSet.add(meal);
		}
	}
	public void addMeal(Meal meal) {
		mealsSet.add(meal);
	}
	public void removeMeal(Meal meal) {
		mealsSet.remove(meal);
	}
	public final ReadOnlySetWrapper<Meal> getMealsSetWrapper() {
		return readOnlyMealsSet;
	}
}
