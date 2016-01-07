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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fapse.mampf.util.ExceptionHandlerImpl;

class MealStorage {
	private static ExceptionHandlerImpl exceptionHandler = new ExceptionHandlerImpl();
	private final static Path path = Paths.get(System.getProperty("user.home")
			+ File.separator + "Mampf"
			+ File.separator + "data");

	static List<Meal> loadMeals() {
		preparePath(path);
		List<Meal> meals = new ArrayList<>();
		File mealFile = new File(path + "/Meals.data");
		if(checkMealFile()) {
			meals = readMealsFromFile(mealFile);
		}
		for (Meal meal : meals) {
			setMealRecipe(meal);
		}
		return meals;
	}
	
	private static void preparePath(Path path) {
		if (!Files.exists(path)) {
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				exceptionHandler.handle("model", "E1", "Could not create path: " +
						path.toString(), e);
			}							
		}
	}
	
	private static boolean checkMealFile() {
		File mealFile = new File(path + "/Meals.data");
		return mealFile.exists() && mealFile.length() > 0;
	}
	
	private static List<Meal> readMealsFromFile(File file) {
		List<Meal> meals = new ArrayList<>();
		try (InputStream is = new FileInputStream(path + "/Meals.data");){
			ObjectInputStream ois = new ObjectInputStream(is);
			Meal[] meals_arr = (Meal[]) ois.readObject();
			ois.close();
			meals = Arrays.asList(meals_arr);
		} catch (IOException e) {
			exceptionHandler.handle("model", "E1", "Could not read from file: " +
					file.toString(), e);
		} catch (ClassNotFoundException e) {
			exceptionHandler.handle("model", "E1", "Could not create meals from file: " +
					file.toString(), e);			
		}
		return meals;
	}
	
	private static void setMealRecipe(Meal meal) {
		boolean recipeFound = false;
		List<Recipe> recipes  = ResourceBuilder.loadRecipes();
		for (Recipe recipe : recipes) {
			if (recipe.getUID().equals(meal.getRecipeUID())) {
				meal.setRecipe(recipe);
				recipeFound = true;
				break;
			}
		}
		if (!recipeFound) {
			exceptionHandler.raise("model", "E3", "Could not find recipe UID " +
					meal.getRecipeUID());
		}
	}

	static void saveMeals(List<Meal> meals) {
		preparePath(path);
		Meal[] mealsArray = mealListToArray(meals);
		try {
			OutputStream os = new FileOutputStream(path + "/Meals.data", false);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(mealsArray);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			exceptionHandler.handle("model", "E1", "Could not write to file: " +
					path.toString(), e);
		}
	}

	private static Meal[] mealListToArray(List<Meal> meals) {
		Meal[] mealsArray = new Meal[meals.size()];
		int counter = 0;
		for (Meal mealItem : meals) {
			Meal mealTemp = new Meal(mealItem.getRecipe(), mealItem.getRecipeUID());
			mealTemp.setDates(mealItem.getDates());
			mealTemp.setServing(mealItem.getServing());
			mealsArray[counter] = mealTemp;
			mealsArray[counter].setDates(mealTemp.getDates());
			mealsArray[counter].setRecipe(null);
			mealsArray[counter].setServing(mealTemp.getServing());
			counter++;
		}
		return mealsArray;
	}
}