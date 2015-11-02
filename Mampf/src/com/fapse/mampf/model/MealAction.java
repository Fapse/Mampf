package com.fapse.mampf.model;

import java.io.Serializable;
import java.time.LocalDate;

public class MealAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8239159739829124735L;
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
	
	public Meal getMeal() {
		return meal;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public boolean isMeal(Meal meal) {
		if (this.meal.equals(meal)) {
			return true;
		} else {
			return false;
		}
	}
}
