package com.fapse.mampf.model;

import java.io.Serializable;

public class Condiment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2050686340269246787L;
	private String name;
	private CondimentCategory category;
	private QuantityUnit quantityUnit;
	public Condiment() {
	}
	public Condiment(String name, CondimentCategory category, QuantityUnit quantityUnit) {
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