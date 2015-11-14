package com.fapse.mampf.model;

public enum CondimentCategory {
	PRODUCE ("Obst & Gemüse"),
	DAIRYPRODUCT ("Milchprodukte"),
	MEATSAUSAGE ("Fleich & Wurst"),
	SPICE ("Gewürze"),
	BEVERAGE ("Getränke");
	
	private final String categoryName;
	
	CondimentCategory(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	@Override
	public String toString() {
		return categoryName;
	}
}