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
	public static void saveMealActions(List<MealAction> mealActions) {
		checkPath();
		try (
			OutputStream os = new FileOutputStream(path + "/MealActions.data", false);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			) {
			/*MealAction[] meals = mealActions.toArray(new MealAction[0]);
			for (MealAction meal : meals) {
				meal.meal.setRecipe(null);
			}*/
			MealAction[] mealsArray = new MealAction[mealActions.size()];
			int counter = 0;
			for (MealAction mealActionList : mealActions) {
				Meal mealTemp = new Meal(mealActionList.meal.getRecipe(), mealActionList.meal.getRecipeUUID());
				mealsArray[counter] =  new MealAction(mealTemp, mealActionList.getDate());
				mealsArray[counter].meal.setRecipe(null);
				/*System.out.println("Datum von gespeicherter MealAction: " + mealsArray[counter].getDate());
				//System.out.println("RecipeUUID von gespeicherter MealAction: " + mealsArray[counter].meal.getRecipeUUID());
				if (mealsArray[counter].meal.getRecipe() == null) {
					System.out.println("recipe == null");						
				} else {
					System.out.println("Achtung: recipe != null");
				}*/
				counter++;
			}
			oos.writeObject(mealsArray);
			oos.flush();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	public static void saveRecipes(List<Recipe> recipes) {
		checkPath();
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
	public static void saveCondiments(List<Condiment> condiments) {
		checkPath();
		try (
			OutputStream os = new FileOutputStream(path + "/Condiments.data", false);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			) {
			Condiment[] condiments_arr = condiments.toArray(new Condiment[0]);
			oos.writeObject(condiments_arr);
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
	public static List<MealAction> loadMealActions() {
		List<MealAction> mealActions = new ArrayList<>();
		List<Recipe> recipes  = loadRecipes();
		System.out.println(recipes.size() + " Rezepte geladen");
		if (checkFile(Paths.get(path + "/MealActions.data"))) {
			try (
				InputStream is = new FileInputStream(path + "/MealActions.data");
				ObjectInputStream ois = new ObjectInputStream(is);
				) {
				MealAction[] meals_arr = (MealAction[]) ois.readObject();				
				//System.out.println(meals_arr.length);
				mealActions = Arrays.asList(meals_arr);
			} catch (IOException e) {
				System.out.println(e.toString());
			} catch (ClassNotFoundException e) {
				System.out.println(e.toString());			
			}
		}
		System.out.println(mealActions.size() + " MealActions geladen");
		for (MealAction mealAction : mealActions) {
			/*System.out.println("Jetzt die MealActions durchgehen");
			if (mealAction.getMeal() != null) {
				System.out.println("Test");
			}
			System.out.println("Suche Rezept für " + mealAction.getMeal().getRecipeUUID());*/
			for (Recipe recipe : recipes) {
				//System.out.println("Biete Rezept mit UUID " + recipe.getUUID());
				if (recipe.getUUID().equals(mealAction.getMeal().getRecipeUUID())) {
					//System.out.println("Rezept hinzugefügt");
					mealAction.meal.setRecipe(recipe);
					break;
				}
			}
		}
		System.out.println("Hallo von loadMealActions()");
		for (MealAction mealAction : mealActions) {
			System.out.println("Mahlzeit: " + mealAction.getMeal().getRecipeName());
		}
		return mealActions;
	}	
	public static List<Condiment> loadCondiments() {
		List<Condiment> condiments = new ArrayList<>();
		if (checkFile(Paths.get(path + "/Condiments.data"))) {
			try (
				InputStream is = new FileInputStream(path + "/Condiments.data");
				ObjectInputStream ois = new ObjectInputStream(is);
				) {
				Condiment[] condiments_arr = (Condiment[]) ois.readObject();				
				System.out.println(condiments_arr.length);
				condiments = Arrays.asList(condiments_arr);
			} catch (IOException e) {
				System.out.println(e.toString());
			} catch (ClassNotFoundException e) {
				System.out.println(e.toString());			
			}
		}
		return condiments;
	}	
}
