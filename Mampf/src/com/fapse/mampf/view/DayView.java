package com.fapse.mampf.view;

import java.io.IOException;
import java.time.LocalDate;

import com.fapse.mampf.Mampf;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.util.DateUtil;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DayView {
	private Mampf mampf;
	private BorderPane bp;
	@FXML
	private VBox vb = new VBox();
	@FXML
	private HBox hb = new HBox();
	private final LocalDate date;
	
	public DayView (LocalDate date, ReadOnlyListWrapper<Meal> meals, Mampf mampf) {
		this.mampf = mampf;
		this.date = date;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Mampf.class.getResource("view/DayView.fxml"));
		try {
			bp = (BorderPane) loader.load();
		} catch (IOException e) {
			System.out.println("Couldn't load DayView borderPane");
			System.exit(1);
		}
		Label dateText = new Label(DateUtil.format(date));
		ContextMenu cm = NewMealContextMenu.getMealContextMenu(date, mampf);
		hb.getChildren().add(dateText);
		bp.setTop(hb);
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
		dateText.getStyleClass().add("dayviewdatelabel");
		hb.getStyleClass().add("dayviewhbox");
	}
	public void updateMeals(ReadOnlyListWrapper<Meal> meals) {
		vb.getChildren().clear();
		for (Meal meal : meals) {
			ContextMenu cm = MealContextMenu.getMealContextMenu(date, meal);
			Label text;
			if (meal.isCookDay(date)) {
				text = new Label(meal.getCookDayString());				
				text.getStyleClass().add("dayviewcooklabel");
			} else {
				text = new Label(meal.toString());
			}
			text.setOnMouseClicked(new EventHandler<MouseEvent> () {
						@Override
						public void handle(MouseEvent event) {
				            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
				            	mampf.showRecipeBrowser(meal.getRecipe());
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
	public LocalDate getDate() {
		return date;
	}
}