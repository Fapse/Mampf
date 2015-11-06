package com.fapse.mampf.model;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MealPredicates {
    public static Predicate<Meal> hasDate(LocalDate date) {
        return p -> p.hasDate(date) == true;
    }
    
    public static Predicate<Meal> isMeal(Meal meal) {
    	return p -> p.isMeal(meal) == true;
    }
     
    public static ObservableList<Meal> filterMeals (ObservableList<Meal> mealActions, Predicate<Meal> predicate) {
        List<Meal> list = mealActions.stream().filter( predicate ).collect(Collectors.<Meal>toList());
        ObservableList<Meal> obsMealActions = FXCollections.observableArrayList();
        obsMealActions.addAll(list);
        return obsMealActions;
    }
}