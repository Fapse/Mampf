package com.fapse.mampf.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FoodSchedule {
	private List<Meal> foodSchedule = new ArrayList<>();
	public FoodSchedule() {

	}
	
	public void addMeal(Meal meal) {
		if (meal != null) {
			foodSchedule.add(meal);
		}
	}
	
	public Set<Meal> getMeals(Date date) {
		HashSet<Meal> mealSet = new HashSet<>();
		for(Meal meal : mealSet) {
			if (date.equals(meal.getCookDate()) || meal.isCookDate(date)) {
				mealSet.add(meal);
			}
		}
		return mealSet;
	}
}
