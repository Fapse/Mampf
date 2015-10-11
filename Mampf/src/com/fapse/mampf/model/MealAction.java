package com.fapse.mampf.model;

import java.time.LocalDate;

public class MealAction {
	public final Meal meal;
	public final LocalDate date;
	
	MealAction(Meal meal, LocalDate date) {
		this.meal = meal;
		this.date = date;
	}
	
	public boolean hasDate(LocalDate date) {
		if (this.date.isEqual(date)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isMeal(Meal meal) {
		if (this.meal.equals(meal)) {
			return true;
		} else {
			return false;
		}
	}
}
