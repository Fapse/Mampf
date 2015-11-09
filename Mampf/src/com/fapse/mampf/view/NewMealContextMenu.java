package com.fapse.mampf.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fapse.mampf.Mampf;
import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.model.Recipe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class NewMealContextMenu {	
	public static ContextMenu getMealContextMenu(LocalDate date, Mampf mampf) {
		MampfData mampfData = MampfData.getMampfData();
		ContextMenu cm = new ContextMenu();
		Menu mi_meal = new Menu("Mahlzeit hinzfügen");
		Menu mi_list = new Menu("Einkaufszettel zeigen");
		List<Recipe> mi_recipes = new ArrayList<>();
		mi_recipes.addAll(mampfData.getRecipes());
		cm.getItems().add(mi_meal);
		cm.getItems().add(mi_list);
		for (Recipe recipe : mi_recipes) {
			MenuItem mi_recipe = new MenuItem(recipe.getName());
			mi_recipe.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mampfData.addNewMeal(recipe, date);
				}
			});
			mi_meal.getItems().add(mi_recipe);
		}
		for (int n = 1; n < 5; n++) {
			MenuItem mi_day = new MenuItem(String.valueOf(n) + " Tag(e)");
			final int m = n;
			mi_day.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mampf.showShoppingList(date, m);
				}
			});
			mi_list.getItems().add(mi_day);
		}
		return cm;
	}
}
