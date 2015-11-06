package com.fapse.mampf.model;

public class Condiment{
	private final String name;
	private final String amount;
	private final String unit;

	public Condiment(String name, String amount, String unit) {
		this.name = name;
		this.amount = amount;
		this.unit = unit;
	}
	public String getName() {
		return name;
	}
	String getAmount() {
		return amount;
	}
	String getUnit() {
		return unit;
	}
	@Override
	public String toString() {
		return name;
	}
}