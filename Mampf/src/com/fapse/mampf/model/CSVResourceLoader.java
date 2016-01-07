package com.fapse.mampf.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fapse.mampf.util.ExceptionHandlerImpl;

class CSVResourceLoader {
	private static ExceptionHandlerImpl exceptionHandler = new ExceptionHandlerImpl();
	
	static List<String> loadCSVResource(String resource, boolean discardHeading) {
		List<String> rows = new ArrayList<>();
		rows.addAll(loadRows(resource));
		if (discardHeading) {
			discardHeading(rows);
		}
		return rows;
	}
	
	private static List<String> loadRows(String resource) {
		List<String> rows = new ArrayList<>();
		try {
			InputStream is = MealStorage.class.getResourceAsStream(resource);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bf = new BufferedReader(isr);
			rows.addAll( bf.lines().collect(Collectors.toList()));
		} catch (Exception e) {
			exceptionHandler.handle("model", "E1", "Could not read from resource: " +
					resource, e);
		}
		return rows;
	}
	
	private static void discardHeading(List<String> rows) {
		if (rows.size() > 0) {
			rows.remove(0);
		}
	}
}