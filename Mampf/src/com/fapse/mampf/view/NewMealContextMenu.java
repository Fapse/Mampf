package com.fapse.mampf.view;

import java.time.LocalDate;
import java.util.Set;

import com.fapse.mampf.Mampf;
import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.model.Recipe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class NewMealContextMenu {	
	public static ContextMenu getMealContextMenu(LocalDate date, Mampf mampf) {
		MampfData mampfData = MampfData.getMampfData();
		ContextMenu cm = new ContextMenu();
		Set<String> recipeCategories = mampfData.getRecipeCategories();
		for (String recipeCategory : recipeCategories) {
			Menu recipeCategoryMenu = new Menu(recipeCategory);
			for (Recipe recipe : mampfData.getRecipesPerCategory(recipeCategory)) {
				MenuItem mi_recipe = new MenuItem(recipe.getName());
				mi_recipe.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						mampfData.addNewMeal(recipe, date);
					}
				});
				recipeCategoryMenu.getItems().add(mi_recipe);
			}
			cm.getItems().add(recipeCategoryMenu);
		}
		cm.getItems().add(new SeparatorMenuItem());
		Menu mi_list = new Menu("Einkaufszettel zeigen");
		cm.getItems().add(mi_list);
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
