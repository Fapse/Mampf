package com.fapse.mampf.view;

import java.time.LocalDate;

import com.fapse.mampf.model.DaySchedule;
import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.util.DateUtil;

import javafx.beans.property.ReadOnlySetWrapper;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MampfController {
	private MampfData mampfData = MampfData.getMampfData();
	private ReadOnlySetWrapper<DaySchedule> readDaySchedules = mampfData.getDaySchedulesSetWrapper();
	@FXML
	private GridPane gridPane = new GridPane();

	@FXML
	private ScrollPane scrollPane = new ScrollPane();
	
	public MampfController() {
		readDaySchedules.addListener(new SetChangeListener<DaySchedule>() {
			@Override
			public void onChanged(SetChangeListener.Change<? extends DaySchedule> c) {
				if (c.wasAdded()) {
					System.out.println("Neu dabei: " + c.getElementAdded().getDate());
				} else if (c.wasRemoved()) {
					System.out.println("Gelöscht: " + c.getElementRemoved().getDate());				
				}
			}
		});
	}
	
	@FXML
	private void initialize() {
		instantiateGridCells();
	}
	
	private void instantiateGridCells() {
		LocalDate day = LocalDate.now();
		int firstEntryCol = day.withDayOfMonth(1).getDayOfWeek().getValue();
		int dayOfMonthCounter = 1;
		boolean foundCol = false;
		int rowCount = 5, colCount = 7;
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				if (foundCol == false && col == firstEntryCol - 1) {
					foundCol = true;
				}
				if (foundCol && (dayOfMonthCounter < day.lengthOfMonth())) {
					VBox vBox = new VBox();
					vBox.setPadding(new Insets(5,5,5,5));
					LocalDate gridDay = day.withDayOfMonth(dayOfMonthCounter++);
					DaySchedule daySched = mampfData.getDaySchedule(gridDay);
					if (daySched != null) {
						Text dateText = new Text(DateUtil.format(daySched.getDate()));
						vBox.getChildren().add(dateText);
						((Text) vBox.getChildren().get(0)).setTextAlignment(TextAlignment.CENTER);
						ReadOnlySetWrapper<Meal> readMeals = daySched.getMealsSetWrapper();
						for (Meal meals : readMeals) {
							vBox.getChildren().add(new Text(meals.getRecipeName()));							
						}
					} else {
						vBox.getChildren().add(new Text("No data"));						
					}
					gridPane.add(vBox, col, row, 1, 1);
				}
			}
		}
	}
}
