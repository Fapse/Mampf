package com.fapse.mampf.util;

public enum Weekdays {
	MONDAY ("Montag"),
	TUESDAY ("Dienstag"),
	WEDNESDAY ("Mittwoch"),
	THURSDAY ("Donnerstag"),
	FRIDAY ("Freitag"),
	SATURDAY ("Samstag"),
	SUNDAY ("Sonntag");

	private final String weekdayName;
	
	Weekdays(String weekdayName) {
		this.weekdayName = weekdayName;
	}
	
	public static String get(int i) {
		return values()[i].weekdayName;
	}
}