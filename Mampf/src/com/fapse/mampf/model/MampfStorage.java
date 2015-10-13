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
import java.util.Arrays;
import java.util.List;

public class MampfStorage {
	private final static Path path = Paths.get(System.getProperty("user.home") + "/Desktop/Mampf");
	
	public static void saveMealActions(List<MealAction> mealActions) {
		System.out.println(path.toAbsolutePath());
		try {
			if (!Files.exists(path)) {
				Files.createDirectory(path);				
			}

		} catch (IOException e) {
			System.out.println("Fehler1");
		}
		try (
			OutputStream os = new FileOutputStream(path + "/MealActions.data", false);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			) {
			MealAction[] meals = mealActions.toArray(new MealAction[0]);
			oos.writeObject(meals);
			oos.flush();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	public static void saveRecipes(List<Recipe> recipes) {
		System.out.println(path.toAbsolutePath());
		try {
			if (!Files.exists(path)) {
				Files.createDirectory(path);				
			}

		} catch (IOException e) {
			System.out.println("Fehler1");
		}
		try (
			OutputStream os = new FileOutputStream(path + "/Recipes.data", false);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			) {
			Recipe[] recipes_arr = recipes.toArray(new Recipe[0]);
			oos.writeObject(recipes_arr);
			oos.flush();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	public static List<Recipe> loadRecipes() {
		List<Recipe> recipes = null;
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
		return recipes;
	}
	public static List<MealAction> loadMealActions() {
		List<MealAction> mealActions = null;
		try (
			InputStream is = new FileInputStream(path + "/MealActions.data");
			ObjectInputStream ois = new ObjectInputStream(is);
			) {
			MealAction[] meals_arr = (MealAction[]) ois.readObject();				
			System.out.println(meals_arr.length);
			mealActions = Arrays.asList(meals_arr);
		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());			
		}
		return mealActions;
	}	
}
