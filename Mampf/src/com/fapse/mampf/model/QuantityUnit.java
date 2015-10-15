package com.fapse.mampf.model;

public enum QuantityUnit {
	GRAM ("Gramm"),
	KILOGRAM ("Kilo"),
	MILLILITER ("ml"), 
	LITER ("Liter"), 
	PIECE ("Stück");
	private final String quantityUnitName;
	
	QuantityUnit(String quantityUnitName) {
		this.quantityUnitName = quantityUnitName;
	}
	public String getCategoryName() {
		return quantityUnitName;
	}
	@Override
	public String toString() {
		return quantityUnitName;
	}
}
