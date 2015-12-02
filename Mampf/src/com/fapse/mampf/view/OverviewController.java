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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

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
	}
	public void setMampf(Mampf mampf) {
		this.mampf = mampf;
		LocalDate day = LocalDate.now();
		LocalDate gridDay = day.minusDays(day.getDayOfWeek().getValue() +6);
		int rowCount = 6, colCount = 7;
		Label[] dayLabel = new Label[7];
		dayLabel[0] = new Label("Montag");
		dayLabel[1] = new Label("Dienstag");
		dayLabel[2] = new Label("Mittwoch");
		dayLabel[3] = new Label("Donnerstag");
		dayLabel[4] = new Label("Freitag");
		dayLabel[5] = new Label("Samstag");
		dayLabel[6] = new Label("Sonntag");
		for (int n = 0; n < 7; n++) {
			HBox pane = new HBox();
			dayLabel[n].getStyleClass().add("dayviewdatelabel");
			pane.getChildren().add(dayLabel[n]);
			pane.getStyleClass().add("dayviewweekdaypane");
			gridPane.add(pane, n, 0);
		}
		for (int row = 1; row < rowCount; row++) {
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