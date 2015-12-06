package com.fapse.mampf.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.fapse.mampf.Mampf;
import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;
import com.fapse.mampf.util.Weekdays;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class OverviewController {
	private Mampf mampf;
	private MampfData mampfData = MampfData.getMampfData();
	private ReadOnlyListWrapper<LocalDate> changedDates = mampfData.getChangedDates();
	private List<DayView> dayViews = new ArrayList<>();
	private DayView focusDayView;
	@FXML
	private BorderPane borderPane = new BorderPane();
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
		for (int n = 0; n < 7; n++) {
			Label label = new Label(Weekdays.get(n));
			HBox pane = new HBox();
			label.getStyleClass().add("dayviewdatelabel");
			pane.getChildren().add(label);
			pane.getStyleClass().add("dayviewweekdaypane");
			gridPane.add(pane, n, 0);
		}
		fillGridPane(gridDay);
	}
	private void fillGridPane(LocalDate gridDay) {
		assert gridDay.getDayOfWeek().getValue() == 1;
		dayViews.clear();
		int rowCount = 6, colCount = 7;
		for (int row = 1; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
					ReadOnlyListWrapper<Meal> meals = mampfData.getMeals(gridDay);
					DayView dayView = new DayView(gridDay, meals, mampf, this);
					if (gridDay.equals(LocalDate.now())) {
						dayView.setFocus();
						focusDayView = dayView;
					}
					gridPane.add(dayView.getDayView(), col, row, 1, 1);
					dayViews.add(dayView);
					gridDay = gridDay.plusDays(1);
			}
		}		
	}
	/*private Optional<DayView> getGridDateView(LocalDate date) {
		Stream<DayView> allDayViews = dayViews.stream();
		Stream<DayView> someDayViews = allDayViews.filter(dayView -> dayView.getDate().equals(date));
		return someDayViews.findFirst();
	}
	public void requestDayFocus(LocalDate newDay) {
		DayView maybeDayView = getGridDateView(newDay).orElse(null);
		if(maybeDayView != null) {
			requestDayViewFocus(maybeDayView);
		} else if (newDay.isBefore(dayViews.stream().min((o1, o2) -> o1.compare(o1, o2)).get().getDate())){
			fillGridPane(newDay.minusDays(newDay.getDayOfWeek().getValue()));
			//requestDayViewFocus(dayViews.stream().findFirst())
		} else {

		}
	}*/
	public void requestDayViewFocus(DayView dayView) {
		if (dayViews.contains(dayView)) {
			focusDayView.resetStyle();
			dayView.setFocus();
			focusDayView = dayView;
		}
	}
	public void showRecipeBrowser(Recipe recipe) {
		mampf.showRecipeBrowser(recipe);
	}
}