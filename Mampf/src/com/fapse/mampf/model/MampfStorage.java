package com.fapse.mampf.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MampfStorage {
	private final static Path path = Paths.get(System.getProperty("user.home") + "/Desktop/Mampf");
	private static void checkPath() {
		try {
			if (!Files.exists(path)) {
				Files.createDirectory(path);				
			}

		} catch (IOException e) {
			System.out.println("Fehler: Pfad nicht gefunden / nicht anlegbar");
		}		
	}
	private static boolean checkFile(Path path) {
		if (Files.exists(path)) {
			return true;
		} else {
			return false;
		}
	}
	public static void saveMeals(List<Meal> meals) {
		checkPath();
		try (
			OutputStream os = new FileOutputStream(path + "/Meals.data", false);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			) {
			Meal[] mealsArray = new Meal[meals.size()];
			int counter = 0;
			for (Meal mealList : meals) {
				Meal mealTemp = new Meal(mealList.getRecipe(), mealList.getRecipeUUID());
				mealsArray[counter] =  new Meal(mealTemp, mealList.getDate());
				mealsArray[counter].setRecipe(null);
				counter++;
			}
			oos.writeObject(mealsArray);
			oos.flush();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	public static List<Recipe> loadRecipes() {
		List<Recipe> recipes  = new ArrayList<>();
		if (checkFile(Paths.get(path + "/Recipes.data"))) {
			try (
				InputStream is = new FileInputStream(path + "/Recipes.data");
				ObjectInputStream ois = new ObjectInputStream(is);
				) {
				Recipe[] recipes_arr = (Recipe[]) ois.readObject();
				System.out.println(recipes_arr.length);
				recipes = Arrays.asList(recipes_arr);	
			} catch (IOException e) {
				System.out.println(e.toString());
			} catch (ClassNotFoundException e) {
				System.out.println(e.toString());			
			}
		}
		return recipes;
	}
	public static List<Meal> loadMeals() {
		List<Meal> meals = new ArrayList<>();
		List<Recipe> recipes  = loadRecipes();
		if (checkFile(Paths.get(path + "/Meals.data"))) {
			try (
				InputStream is = new FileInputStream(path + "/Meals.data");
				ObjectInputStream ois = new ObjectInputStream(is);
				) {
				Meal[] meals_arr = (Meal[]) ois.readObject();				
				meals = Arrays.asList(meals_arr);
			} catch (IOException e) {
				System.out.println(e.toString());
			} catch (ClassNotFoundException e) {
				System.out.println(e.toString());			
			}
		}
		for (Meal meal : meals) {
			for (Recipe recipe : recipes) {
				if (recipe.getUUID().equals(meal.getRecipeUUID())) {
					meal.setRecipe(recipe);
					break;
				}
			}
		}
		return meals;
	}		
}
