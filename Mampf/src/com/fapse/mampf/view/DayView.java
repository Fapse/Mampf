package com.fapse.mampf.view;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;

import com.fapse.mampf.Mampf;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.util.DateUtil;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DayView implements Comparator<DayView> {
	private Mampf mampf;
	private OverviewController overview;
	private BorderPane bp;
	@FXML
	private VBox vb = new VBox();
	@FXML
	private HBox hb = new HBox();
	private final LocalDate date;
	
	public DayView (LocalDate date, ReadOnlyListWrapper<Meal> meals, Mampf mampf, OverviewController overview) {
		this.mampf = mampf;
		this.overview = overview;
		this.date = date;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Mampf.class.getResource("view/DayView.fxml"));
		try {
			bp = (BorderPane) loader.load();
		} catch (IOException e) {
			System.out.println("Couldn't load DayView borderPane");
			System.exit(1);
		}
		resetStyle();
		Label dateText;
		if (date.getDayOfMonth() == 1) {
			dateText = new Label(DateUtil.format_day_month(date));			
		} else {
			dateText = new Label(DateUtil.format_day(date));						
		}
		ContextMenu cm = NewMealContextMenu.getMealContextMenu(date, mampf);
		hb.getChildren().add(dateText);
		bp.setTop(hb);
		bp.setOnMouseClicked(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent event) {
	            if (event.getButton() == MouseButton.SECONDARY) {
	            	cm.show(vb, event.getScreenX(), event.getScreenY());				            	
	            }
	            requestDayViewFocus();
	            bp.requestFocus();
	            event.consume();
			}
		});
		
		
		bp.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent> () {
			@Override
			public void handle(final KeyEvent keyEvent) {
				System.out.println("DayView (" + date + ") : Taste gedrückt");
			}
		});

		
		
		updateMeals(meals);
		bp.setCenter(vb);
		if (LocalDate.now().equals(date)) {
			dateText.setId("dayviewdatelabeltoday");
			bp.requestFocus();
		} else {
			dateText.getStyleClass().add("dayviewdatelabel");
		}
		hb.getStyleClass().add("dayviewhbox");
	}
	public void requestDayViewFocus() {
		overview.requestDayViewFocus(this);
	}
	public void setFocus() {
		bp.getStyleClass().clear();
		bp.getStyleClass().add("dayviewpane");
		bp.getStyleClass().add("dayviewfocus");		
	}
	public void resetStyle() {
		if (date.getMonthValue() % 2 != 0) {
			if ((date.getDayOfWeek() == DayOfWeek.SATURDAY) ||
					(date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				bp.getStyleClass().clear();
				bp.getStyleClass().add("dayviewpane");
				bp.getStyleClass().add("dayviewunevenmonthweekend");
			} else {
				bp.getStyleClass().clear();
				bp.getStyleClass().add("dayviewpane");
				bp.getStyleClass().add("dayviewunevenmonth");				
			}
		} else {
			if ((date.getDayOfWeek() == DayOfWeek.SATURDAY) ||
					(date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				bp.getStyleClass().clear();
				bp.getStyleClass().add("dayviewpane");
				bp.getStyleClass().add("dayviewevenmonthweekend");
			} else {
				bp.getStyleClass().clear();
				bp.getStyleClass().add("dayviewpane");
				bp.getStyleClass().add("dayviewevenmonth");
			}
		}		
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
				            requestDayViewFocus();
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
	@Override
	public boolean equals(Object o) {
		if ((o instanceof DayView) && 
				(((DayView) o).getDate()).equals(date)) {
			return true;
		} else {
			return false;
		}
		
	}
	@Override
	public int compare(DayView o1, DayView o2) {
		return Integer.valueOf(o1.getDate().compareTo(o2.getDate()));
	}
}