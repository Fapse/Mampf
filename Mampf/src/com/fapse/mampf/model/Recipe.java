package com.fapse.mampf.model;

import java.io.Serializable;
import java.util.UUID;

public class Recipe implements Serializable {
	private static final long serialVersionUID = -6503331288632008128L;
	private UUID uuid;
	private final String name;
	private final String recipe;
	
	public Recipe(String name) {
		this.uuid = UUID.randomUUID();
		this.name = name;
		this.recipe = "No recipe available!";
	}
	public Recipe(String name, String recipe) {
		this.uuid = UUID.randomUUID();
		this.name = name;
		this.recipe = recipe;
	}
	public Recipe(String name, String recipe, UUID uuid) {
		this.uuid = uuid;
		this.name = name;
		this.recipe = recipe;
	}
	@Override
	public boolean equals(Object o) {
		return this.uuid.equals(o);
	}
	public UUID getUUID() {
		return uuid;
	}
	public String getName() {
		return name;
	}
	public String getRecipe() {
		return recipe;
	}
}