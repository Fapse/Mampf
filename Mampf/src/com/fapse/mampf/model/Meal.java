package com.fapse.mampf.model;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class Meal {
	private Date cookDate;
	private Set<Date> eatDates = new TreeSet<>();
	private Recipe recipe;
	public Meal(){
	}
	public Meal(Recipe recipe, Date cookDate, Set<Date> eatDates) {
		this.recipe = recipe;
		this.cookDate = cookDate;
		this.eatDates = eatDates;
	}
	public Date getCookDate() {
		return cookDate;
	}
	public void setCookDate(Date cookDate) {
		this.cookDate = cookDate;
	}
	public Set<Date> getEatDates() {
		return eatDates;
	}
	public void setEatDates(Set<Date> eatDates) {
		eatDates.clear();
		this.eatDates = eatDates; 
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public String getMealName() {
		return recipe.getName();
	}
	public boolean isCookDate(Date cookDate) {
		return this.cookDate.equals(cookDate)?true:false;
	}
	public boolean isEatDate(Date eatDate) {
		return eatDates.contains(eatDate)?true:false;
	}
}