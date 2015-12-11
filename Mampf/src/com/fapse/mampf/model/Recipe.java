package com.fapse.mampf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Recipe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7277511326729507838L;
	private String uid;
	private String name;
	private String recipe;
	private String category;
	private String rating;
	private String effort;
	private String bestBefore;
	private String image;
	
	private List<Condiment> condiments = new ArrayList<>();
	
	public Recipe(List<Condiment> tmpCondiments, String... args) {
		if (((args.length - 8) % 2) == 0) {
			int counter = 0;
			uid = args[counter++];
			name = args[counter++];
			recipe = args[counter++];
			category = args[counter++];
			rating =  args[counter++];
			effort = args[counter++];
			bestBefore = args[counter++];
			image = args[counter++];
			while (counter < args.length) {
				String condimentName = args[counter++];
				String amount = args[counter++];
				for (Condiment condiment : tmpCondiments) {
					if (condimentName.equals(condiment.getName())) {
						Condiment tmpCondiment = new Condiment(condiment);
						tmpCondiment.setAmount(amount);
						condiments.add(tmpCondiment);
						break;
					}
				}
			}
		}
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	String getRating() {
		return rating;
	}
	String getEffort() {
		return effort;
	}
	String getBestBefore() {
		return bestBefore;
	}
	String getImage() {
		return image;
	}
	public boolean equals(Recipe recipe) {
		return this.uid.equals(recipe.getUID());
	}
	public String getUID() {
		return uid;
	}
	public String getName() {
		return name;
	}
	public String getRecipe() {
		return recipe;
	}
	public List<Condiment> getCondiments() {
		return Collections.unmodifiableList(condiments);
	}
}