package com.fapse.mampf.model;

import java.io.Serializable;

public class Condiment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5100266241776353395L;
	public String name;
	public CondimentCategory category;
	public QuantityUnit quantityUnit;
	public Condiment() {
	}
	public Condiment(String name, CondimentCategory category, QuantityUnit quantityUnit) {
		this.name = name;
		this.category = category;
		this.quantityUnit = quantityUnit;
	}
	@Override
	public String toString() {
		return name + " " + category.getCategoryName() + " " + quantityUnit.getCategoryName();
	}
}