package com.fapse.mampf.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fapse.mampf.Mampf;
import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class OverviewController {
	private Mampf mampf;
	private MampfData mampfData = MampfData.getMampfData();
	private ReadOnlyListWrapper<LocalDate> changedDates = mampfData.getChangedDates();
	private List<DayView> dayViews = new ArrayList<>();
	@FXML
	private GridPane gridPane = new GridPane();
	
	public OverviewController() {
		changedDates.addListener(new ListChangeListener<LocalDate>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends LocalDate> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						for (LocalDate changedDate : c.getAddedSubList()) {
							ReadOnlyListWrapper<Meal> meals = mampfData.getMeals(changedDate);
							for (DayView dayView : dayViews) {
								if (dayView.getDate().equals(changedDate)) {
									dayView.updateMeals(meals);
								}
							}
						}
					}
				}
			}
		});
	}
	
	@FXML
	private void initialize() {
		/*LocalDate day = LocalDate.now();
		LocalDate gridDay = day.minusDays(day.getDayOfWeek().getValue() +6);
		int rowCount = 5, colCount = 7;
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
					ReadOnlyListWrapper<Meal> meals = mampfData.getMeals(gridDay);
					DayView dayView = new DayView(gridDay, meals, mampf);
					gridPane.add(dayView.getDayView(), col, row, 1, 1);
					dayViews.add(dayView);
					gridDay = gridDay.plusDays(1);
			}
		}*/
	}
	public void setMampf(Mampf mampf) {
		this.mampf = mampf;
		LocalDate day = LocalDate.now();
		LocalDate gridDay = day.minusDays(day.getDayOfWeek().getValue() +6);
		int rowCount = 5, colCount = 7;
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
					ReadOnlyListWrapper<Meal> meals = mampfData.getMeals(gridDay);
					DayView dayView = new DayView(gridDay, meals, mampf);
					gridPane.add(dayView.getDayView(), col, row, 1, 1);
					dayViews.add(dayView);
					gridDay = gridDay.plusDays(1);
			}
		}
	}
	public void showRecipeBrowser(Recipe recipe) {
		mampf.showRecipeBrowser(recipe);
	}
}