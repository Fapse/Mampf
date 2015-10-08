package com.fapse.mampf.view;

import java.time.LocalDate;
import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.util.DateUtil;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class OverviewController {
	private MampfData mampfData = MampfData.getMampfData();
	private ReadOnlyListWrapper<Meal> readMeals = mampfData.getMeals();
	@FXML
	private GridPane gridPane = new GridPane();
	
	public OverviewController() {
		readMeals.addListener(new ListChangeListener<Meal>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends Meal> c) {
				if (c.wasAdded()) {
					System.out.println("Soviel neu dabei: " + c.getAddedSubList().size());
				} else if (c.wasRemoved()) {
					System.out.println("Soviel gelöscht: " + c.getRemovedSize());				
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
					TableView<Meal> mealTable = new TableView<>();
					TableColumn<Meal, String> mealColumn = new TableColumn<>();
					LocalDate gridDay = day.withDayOfMonth(dayOfMonthCounter++);
					mealTable.setItems(mampfData.getMeals(gridDay));
					mealColumn.setCellValueFactory(cellData -> cellData.getValue().recipeNameProperty());
					mealColumn.setText(DateUtil.format(gridDay));
					mealTable.getColumns().add(mealColumn);
					gridPane.add(mealTable, col, row, 1, 1);
					mealTable.setPrefWidth(140);
					mealTable.setPrefHeight(160);
					mealTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
				}
			}
		}
	}
}
