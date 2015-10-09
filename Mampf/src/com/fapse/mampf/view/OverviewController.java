package com.fapse.mampf.view;

import java.time.LocalDate;
import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.MealAction;
import com.fapse.mampf.util.DateUtil;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class OverviewController {
	private MampfData mampfData = MampfData.getMampfData();
	private ReadOnlyListWrapper<MealAction> readMeals = mampfData.getMeals();
	@FXML
	private GridPane gridPane = new GridPane();
	
	public OverviewController() {
		readMeals.addListener(new ListChangeListener<MealAction>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends MealAction> c) {
				while (c.next()) {
					System.out.println("Da hat sich was geändert!");
					if (c.wasAdded()) {
						System.out.println("Und zwar gibt's mehr Essen!");
						System.out.println("Anzahl der Änderungen: " + c.getAddedSize());
						for (MealAction mealAction : c.getAddedSubList()) {
							System.out.println(mealAction.meal.getRecipeName());
						}
					} else if (c.wasRemoved()) {
						System.out.println("Und zwar gibt's weniger zu Essen!");
						System.out.println("Anzahl der Änderungen: " + c.getRemovedSize());
					}
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
					ReadOnlyListWrapper<Meal> meals = mampfData.getMeals(gridDay);
					mealTable.setItems(meals);
					mealColumn.setCellValueFactory(cellData -> cellData.getValue().recipeNameProperty());
					mealColumn.setText(DateUtil.format(gridDay));
					mealTable.getColumns().add(mealColumn);
					gridPane.add(mealTable, col, row, 1, 1);
					mealTable.setPrefWidth(140);
					mealTable.setPrefHeight(160);
					mealTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
					ContextMenu cm = new ContextMenu();
					MenuItem mi = new MenuItem("Druck mi!");
					mi.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							mampfData.addMealAction();
						}
					});
					cm.getItems().add(mi);
					mealTable.setContextMenu(cm);
					mealColumn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent> () {
						@Override
						public void handle(MouseEvent event) {
				            if (event.getButton() == MouseButton.SECONDARY) {
				            	cm.show(mealTable, event.getScreenX(), event.getScreenY());
				            }
						}
					});
				}
			}
		}
	}
}
