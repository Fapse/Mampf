package com.fapse.mampf.view;

import java.time.LocalDate;
import java.util.List;

import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.util.DateUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MealContextMenu {	
	public static ContextMenu getMealContextMenu(LocalDate date, Meal meal) {
		MampfData mampfData = MampfData.getMampfData();
		ContextMenu cm = new ContextMenu();
		MenuItem mi_delete = new MenuItem("Mahlzeit löschen");
		cm.getItems().add(mi_delete);
		mi_delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mampfData.deleteMealDay(meal, date);
			}
		});
		Menu mi_dates = new Menu("Speisetag hinzufügen");
		cm.getItems().add(mi_dates);
		List<LocalDate> tmpDates = meal.getRemainingMealDays(date);
		if (tmpDates.size() == 0) {
			mi_dates.setDisable(true);
		} else {
			for (LocalDate tmpDate : tmpDates) {
				MenuItem mi_day = new MenuItem(DateUtil.format(tmpDate));
				mi_dates.getItems().add(mi_day);
				mi_day.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						mampfData.addMealDay(meal, tmpDate);
					}
			});
		}
		/*for (int n = 1; n <= 5; n++) {
			LocalDate day = date.plusDays(n);
			MenuItem mi_day = new MenuItem(DateUtil.format(day));
			mi_dates.getItems().add(mi_day);
			mi_day.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mampfData.addMealDay(meal, day);
				}
			});*/
		}
		Menu mi_serving = new Menu("Anzahl Portionen");
		cm.getItems().add(mi_serving);
		for (int n = 1; n <= 5; n++) {
			final int m = n;
			MenuItem mi_servingAmount = new MenuItem(String.valueOf(n));
			mi_serving.getItems().add(mi_servingAmount);
			mi_servingAmount.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mampfData.setMealServing(meal, m);
				}
			});
		}
		return cm;
	}
}
