package com.fapse.mampf.view;

import java.time.LocalDate;
import java.util.stream.Stream;
import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.util.DateUtil;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MampfController {
	private MampfData mampfData = MampfData.getMampfData();
	private ReadOnlyListWrapper<Meal> readMeals = mampfData.getMealListWrapper();
	@FXML
	private GridPane gridPane = new GridPane();

	@FXML
	private ScrollPane scrollPane = new ScrollPane();
	
	public MampfController() {
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
					VBox vBox = new VBox();
					vBox.setPadding(new Insets(5,5,5,5));
					LocalDate gridDay = day.withDayOfMonth(dayOfMonthCounter++);
					Stream<Meal> meals = mampfData.getMeals(gridDay);
					if (meals != null) {
						Text dateText = new Text(DateUtil.format(gridDay));
						vBox.getChildren().add(dateText);
						meals.forEach(meal -> vBox.getChildren().add(new Text(meal.getRecipeName())));
					} else {
						vBox.getChildren().add(new Text("No data"));						
					}
					gridPane.add(vBox, col, row, 1, 1);
				}
			}
		}
	}
}
