package com.fapse.mampf.model;

public class Condiment{
	private final String uid;
	private final String name;
	private String amount;
	private final String unit;
	private final CondimentCategory category;

	public Condiment(String... args) {
		int counter = 0;
		uid = args[counter++];
		name = args[counter++];
		amount = args[counter++];
		unit = args[counter++];
		category = CondimentCategory.valueOf(args[counter++]);
	}
	public Condiment(Condiment tmpCondiment) {
		uid = tmpCondiment.getUid();
		name = tmpCondiment.getName();
		amount = tmpCondiment.getAmount();
		unit = tmpCondiment.getUnit();
		category = tmpCondiment.getCategory();
	}
	public String getUid() {
		return uid;
	}
	public String getName() {
		return name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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