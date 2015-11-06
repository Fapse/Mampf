package com.fapse.mampf.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.model.Recipe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class NewMealContextMenu {	
	public static ContextMenu getDayViewContextMenu(LocalDate date) {
		MampfData mampfData = MampfData.getMampfData();
		ContextMenu cm = new ContextMenu();
		Menu mi_main = new Menu("Mahlzeit hinzfügen");
		List<Recipe> mi_recipes = new ArrayList<>();
		mi_recipes.addAll(mampfData.getRecipes());
		cm.getItems().add(mi_main);
		for (Recipe recipe : mi_recipes) {
			MenuItem mi_recipe = new MenuItem(recipe.getName());
			mi_recipe.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mampfData.addNewMeal(recipe, date);
				}
			});
			mi_main.getItems().add(mi_recipe);
		}
		return cm;
	}
}
