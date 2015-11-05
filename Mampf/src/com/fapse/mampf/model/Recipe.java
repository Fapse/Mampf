package com.fapse.mampf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable {
	private static final long serialVersionUID = -6503331288632008128L;
	private String uid;
	private String name;
	private String recipe;
	private List<Condiment> condiments = new ArrayList<>();
	
	public Recipe(String... args) {
		if (((args.length - 3) % 3) == 0) {
			int counter = 0;
			uid = args[counter++];
			name = args[counter++];
			recipe = args[counter++];
			while (counter < args.length) {
				condiments.add(new Condiment(args[counter++], args[counter++], args[counter++]));
			}
			
		}
	}
	@Override
	public boolean equals(Object o) {
		return this.uid.equals(o);
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
	List<Condiment> getCondiments() {
		return condiments;
	}
}