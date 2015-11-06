package com.fapse.mampf.view;

import java.time.LocalDate;

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
		for (int n = 1; n <= 5; n++) {
			LocalDate day = date.plusDays(n);
			MenuItem mi_day = new MenuItem(DateUtil.format(day));
			mi_dates.getItems().add(mi_day);
			mi_day.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mampfData.addMealDay(meal, day);
				}
			});
		}
		return cm;
	}
}
