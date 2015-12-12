package com.fapse.mampf.model;

public enum CondimentCategory {
	BEVERAGE ("Getränke"),
	PRODUCE ("Obst & Gemüse"),
	PASTRIES ("Backwaren"),
	DAIRYPRODUCT ("Milchprodukte"),
	MEATSAUSAGE ("Fleich & Wurst"),
	SPICE ("Gewürze"),
	DEEPFROZEN ("Tiefkühlartikel"),
	CONFECTIONARY ("Süßwaren"),
	TINNEDFOOD ("Konserven");
	
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