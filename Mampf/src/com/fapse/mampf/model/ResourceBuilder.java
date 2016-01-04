package com.fapse.mampf.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ResourceBuilder {
	static List<Recipe> loadRecipes() throws IOException {
		List<String> rows = CSVResourceLoader.loadCSVResource("/resources/Recipes.csv", true);
		List<Condiment> condiments = loadCondiments();
		List<Recipe> recipes  = new ArrayList<>();
		for (String row : rows) {
			recipes.add(new Recipe(condiments, getRowValues(row)));
		}
		return recipes;
	}

	private static List<Condiment> loadCondiments() throws IOException {
		List<String> rows = CSVResourceLoader.loadCSVResource("/resources/Condiments.csv", true);
		List<Condiment> condiments = new ArrayList<>();
		for (String row : rows) {
			condiments.add(new Condiment(getRowValues(row)));
		}
		return condiments;
	}
	
	private static String[] getRowValues(String row) {
		List<String> rowValues = Arrays.asList(row.split(";"));
		removeEmptyValues(rowValues);
		return rowValues.toArray(new String[0]);
	}
	
	private static void removeEmptyValues(List<String> rowValues) {
		rowValues.removeIf(p -> p.equals(""));	
	}
}
