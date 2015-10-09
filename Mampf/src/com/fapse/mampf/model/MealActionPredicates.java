package com.fapse.mampf.model;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MealActionPredicates {
    public static Predicate<MealAction> hasDate(LocalDate date) {
        return p -> p.hasDate(date) == true;
    }
     
    public static ObservableList<Meal> filterMeals (ObservableList<MealAction> mealActions, Predicate<MealAction> predicate) {
        List<MealAction> list = mealActions.stream().filter( predicate ).collect(Collectors.<MealAction>toList());
        ObservableList<MealAction> obsMealActions = FXCollections.observableArrayList();
        obsMealActions.addAll(list);
        ObservableList<Meal> obsMeals = FXCollections.observableArrayList();
        for (MealAction mealAction : obsMealActions) {
        	obsMeals.add(mealAction.meal);
        }
        return obsMeals;
    }
}