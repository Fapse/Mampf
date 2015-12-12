package com.fapse.mampf.model;

import java.io.File;
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
//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MampfStorage {
	private final static Path path = Paths.get("." + File.separator + "resources/data");
	
	public MampfStorage() {
		System.out.println(path);		
	}
	
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
				Meal mealTemp = new Meal(mealList.getRecipe(), mealList.getRecipeUID());
				mealTemp.setDates(mealList.getDates());
				mealTemp.setServing(mealList.getServing());
				mealsArray[counter] =  mealTemp;
				mealsArray[counter].setDates(mealTemp.getDates());
				mealsArray[counter].setRecipe(null);
				mealsArray[counter].setServing(mealTemp.getServing());
				counter++;
			}
			oos.writeObject(mealsArray);
			oos.flush();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	public static List<Recipe> loadRecipes() {
		List<Condiment> condiments = loadCondiments();
		List<Recipe> recipes  = new ArrayList<>();
		if (checkFile(Paths.get(path + "/Recipes.csv"))) {
			try {
				int counter = 0;
				List<String> rows = Files.readAllLines(Paths.get(path + "/Recipes.csv"));
				for (String row : rows) {
					List<String> values = new ArrayList<>();
					values = Arrays.asList(row.split(";"));
					values = values.stream().filter(s -> s != "").collect(Collectors.toList());
					String[] test = new String[values.size()];
					for (int n = 0; n < values.size(); n++) {
						test[n] = values.get(n);
					}
					if (counter++ != 0) {
						Recipe tmpRecipe = new Recipe(condiments, test);
						recipes.add(tmpRecipe);
					}
				}
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		return recipes;
	}
	private static List<Condiment> loadCondiments() {
		List<Condiment> condiments  = new ArrayList<>();
		if (checkFile(Paths.get(path + "/Condiments.csv"))) {
			try {
				int counter = 0;
				List<String> rows = Files.readAllLines(Paths.get(path + "/Condiments.csv"));
				for (String row : rows) {
					List<String> values = new ArrayList<>();
					values = Arrays.asList(row.split(";"));
					values = values.stream().filter(s -> s != "").collect(Collectors.toList());
					String[] test = new String[values.size()];
					for (int n = 0; n < values.size(); n++) {
						test[n] = values.get(n);
					}
					if (counter++ != 0) {
						Condiment tmpCondiment = new Condiment(test);
						condiments.add(tmpCondiment);
					}
				}
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		return condiments;
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
				if (recipe.getUID().equals(meal.getRecipeUID())) {
					meal.setRecipe(recipe);
					break;
				}
			}
		}
		return meals;
	}		
}