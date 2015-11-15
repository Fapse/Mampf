package com.fapse.mampf.model;

public class Condiment{
	private final String name;
	private final String amount;
	private final String unit;
	private final CondimentCategory category;

	public Condiment(String name, String amount, String unit, CondimentCategory category) {
		this.name = name;
		this.amount = amount;
		this.unit = unit;
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public String getAmount() {
		return amount;
	}
	public String getUnit() {
		return unit;
	}
	public CondimentCategory getCategory() {
		return category;
	}
	@Override
	public boolean equals(Object o) {
		if(o instanceof Condiment) {
			return this.name.equals(((Condiment) o).getName());
		} else {
			return false;
		}
	}
	@Override
	public String toString() {
		return amount + " " + unit + " " + name;
	}
}