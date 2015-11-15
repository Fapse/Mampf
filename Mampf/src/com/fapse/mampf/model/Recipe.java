package com.fapse.mampf.model;

import com.fapse.mampf.model.CondimentCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Recipe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1476949188820906150L;
	private String uid;
	private String name;
	private String recipe;
	private String rating;
	private String effort;
	private String bestBefore;
	private String image;
	
	private List<Condiment> condiments = new ArrayList<>();
	
	public Recipe(String... args) {
		if (((args.length - 7) % 4) == 0) {
			int counter = 0;
			uid = args[counter++];
			name = args[counter++];
			recipe = args[counter++];
			rating =  args[counter++];
			effort = args[counter++];
			bestBefore = args[counter++];
			image = args[counter++];
			while (counter < args.length) {
				condiments.add(new Condiment(args[counter++], args[counter++], args[counter++], CondimentCategory.valueOf(args[counter++])));
			}
		}
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