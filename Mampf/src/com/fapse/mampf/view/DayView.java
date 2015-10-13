package com.fapse.mampf.view;

import java.time.LocalDate;

import com.fapse.mampf.model.Meal;
import com.fapse.mampf.util.DateUtil;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DayView {
	private BorderPane bp = new BorderPane();
	private VBox vb = new VBox();
	private final LocalDate date;
	
	public DayView (LocalDate date, ReadOnlyListWrapper<Meal> meals) {
		this.date = date;
		Text dateText = new Text(DateUtil.format(date));
		ContextMenu cm = NewMealContextMenu.getDayViewContextMenu(date);
		dateText.setFill(Color.TOMATO);
		bp.setTop(dateText);
		vb.setOnMouseClicked(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent event) {
	            if (event.getButton() == MouseButton.SECONDARY) {
	            	cm.show(vb, event.getScreenX(), event.getScreenY());				            	
	            }
	            event.consume();
			}
		});
		updateMeals(meals);
		bp.setCenter(vb);
		BorderPane.setAlignment(dateText, Pos.TOP_RIGHT);
		BorderPane.setMargin(dateText, new Insets(2,4,2,2));
		BorderPane.setMargin(vb, new Insets(2,2,2,4));
		bp.setMinHeight(70);
		bp.setMinWidth(100);
	}
	public void updateMeals(ReadOnlyListWrapper<Meal> meals) {
		vb.getChildren().clear();
		for (Meal meal : meals) {
			ContextMenu cm = MealContextMenu.getMealContextMenu(date, meal);
			Text text = new Text(meal.getRecipeName());
			text.setOnMouseClicked(new EventHandler<MouseEvent> () {
						@Override
						public void handle(MouseEvent event) {
				            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
				            	text.setFill(Color.ORANGERED);
				            	System.out.println("Hallo");
				            	showMealInfo(meal);
				            } else if (event.getButton() == MouseButton.SECONDARY) {
				            	cm.show(text, event.getScreenX(), event.getScreenY());				            	
				            }
				            event.consume();
						}
					});
			vb.getChildren().add(text);
		}
	}
	public BorderPane getDayView() {
		return bp;
	}
	private void showMealInfo(Meal meal) {
		Alert alert = new Alert(AlertType.INFORMATION);
        //alert.initOwner(mampf.getPrimaryStage());
        alert.setTitle("Mahlzeitinformation");
        alert.setHeaderText(meal.getRecipeName());
        alert.setContentText(meal.getRecipe().getRecipe());
        alert.showAndWait();		
	}
	public LocalDate getDate() {
		return date;
	}
}
