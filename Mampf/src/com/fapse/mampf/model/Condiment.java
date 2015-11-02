package com.fapse.mampf.model;

import java.io.Serializable;
import java.util.UUID;

public class Condiment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2997880288355151564L;
	private String name;
	private UUID uuid;
	private CondimentCategory category;
	private QuantityUnit quantityUnit;
	public Condiment() {
	}
	public Condiment(String name, CondimentCategory category, QuantityUnit quantityUnit) {
		this.uuid = UUID.randomUUID();
		this.name = name;
		this.category = category;
		this.quantityUnit = quantityUnit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UUID getUUID() {
		return uuid;
	}
	public CondimentCategory getCategory() {
		return category;
	}
	public void setCategory(CondimentCategory category) {
		this.category = category;
	}
	public QuantityUnit getQuantityUnit() {
		return quantityUnit;
	}
	public void setQuantityUnit(QuantityUnit quantityUnit) {
		this.quantityUnit = quantityUnit;
	}
	@Override
	public String toString() {
		return name;
	}
}