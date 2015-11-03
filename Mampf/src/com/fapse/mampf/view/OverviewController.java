package com.fapse.mampf.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fapse.mampf.Mampf;
import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.util.DateUtil;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class OverviewController {
	private Mampf mampf;
	private MampfData mampfData = MampfData.getMampfData();
	private ReadOnlyListWrapper<MealAction> readMeals = mampfData.getMeals();
	private List<DayView> dayViews = new ArrayList<>();
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
							System.out.println(mealAction.meal.getRecipeName() + " am " + DateUtil.format(mealAction.date));
							ReadOnlyListWrapper<Meal> meals = mampfData.getMeals(mealAction.date);
							for (DayView dayView : dayViews) {
								if (dayView.getDate().equals(mealAction.date)) {
									dayView.updateMeals(meals);
									System.out.println("dayView update!");
								}
							}
						}
					} else if (c.wasRemoved()) {
						System.out.println("Und zwar gibt's weniger zu Essen!");
						System.out.println("Anzahl der Änderungen: " + c.getRemovedSize());
						for (MealAction mealAction : c.getRemoved()) {
							System.out.println(mealAction.meal.getRecipeName() + " am " + DateUtil.format(mealAction.date));
							ReadOnlyListWrapper<Meal> meals = mampfData.getMeals(mealAction.date);
							for (DayView dayView : dayViews) {
								if (dayView.getDate().equals(mealAction.date)) {
									dayView.updateMeals(meals);
									System.out.println("dayView update!");
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
		LocalDate day = LocalDate.now();
		LocalDate gridDay = day.minusDays(day.getDayOfWeek().getValue() +6);
		int rowCount = 5, colCount = 7;
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
					ReadOnlyListWrapper<Meal> meals = mampfData.getMeals(gridDay);
					DayView dayView = new DayView(gridDay, meals);
					gridPane.add(dayView.getDayView(), col, row, 1, 1);
					dayViews.add(dayView);
					gridDay = gridDay.plusDays(1);
			}
		}
	}
	
	public void deleteMeal(Meal meal) {
		mampfData.deleteMeal(meal);
	}
	public void setMampf(Mampf mampf) {
		this.mampf = mampf;
	}
}