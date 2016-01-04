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

class MampfStorage {
	private final static Path path = Paths.get(System.getProperty("user.home") + File.separator + "Mampf"
			+ File.separator + "data");

	static List<Meal> loadMeals() throws IOException, 
			ClassNotFoundException {
		boolean recipeFound;
		List<Meal> meals = new ArrayList<>();
		List<Recipe> recipes  = ResourceBuilder.loadRecipes();
		File mealFile = new File(path + "/Meals.data");
		mealFile.createNewFile();
		InputStream is = new FileInputStream(path + "/Meals.data");
		if (mealFile.length() > 0) { // if file contains meals
			ObjectInputStream ois = new ObjectInputStream(is);
			Meal[] meals_arr = (Meal[]) ois.readObject();
			ois.close();
			meals = Arrays.asList(meals_arr);
			for (Meal meal : meals) {
				recipeFound = false;
				for (Recipe recipe : recipes) {
					if (recipe.getUID().equals(meal.getRecipeUID())) {
						meal.setRecipe(recipe);
						recipeFound = true;
						break;
					}
				}
				if (!recipeFound) {
					throw new RecipeNotFoundException(meal);
				}
			}
		}		
		return meals;
	}		

	static void saveMeals(List<Meal> meals) throws IOException {
		if (!Files.exists(path)) {
			Files.createDirectory(path);				
		}
		OutputStream os = new FileOutputStream(path + "/Meals.data", false);
		ObjectOutputStream oos = new ObjectOutputStream(os);
		Meal[] mealsArray = new Meal[meals.size()];
		int counter = 0;
		for (Meal mealList : meals) {
			Meal mealTemp = new Meal(mealList.getRecipe(), mealList.getRecipeUID());
			mealTemp.setDates(mealList.getDates());
			mealTemp.setServing(mealList.getServing());
			mealsArray[counter] = mealTemp;
			mealsArray[counter].setDates(mealTemp.getDates());
			mealsArray[counter].setRecipe(null);
			mealsArray[counter].setServing(mealTemp.getServing());
			counter++;
		}
		oos.writeObject(mealsArray);
		oos.flush();
		oos.close();
	}
}