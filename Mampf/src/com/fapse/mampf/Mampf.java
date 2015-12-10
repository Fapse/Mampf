package com.fapse.mampf;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fapse.mampf.model.Recipe;
import com.fapse.mampf.model.Condiment;
import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.view.OverviewController;
import com.fapse.mampf.view.RecipeBrowserController;
import com.fapse.mampf.view.ShoppingListController;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Mampf extends Application {

	private Stage stage;
	private BorderPane rootLayoutDayOverview;

	public Mampf() {
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		this.stage.setTitle("Mampf");
		FXMLLoader loaderDayOverview = new FXMLLoader();
		loaderDayOverview.setLocation(Mampf.class.getResource("view/MampfOverview.fxml"));
		rootLayoutDayOverview = (BorderPane) loaderDayOverview.load();
		OverviewController controller = loaderDayOverview.getController();
		controller.setMampf(this);		
		Scene scene = new Scene(rootLayoutDayOverview);
		this.stage.setScene(scene);
		this.stage.show();
		scene.addEventHandler(ScrollEvent.SCROLL_FINISHED, new EventHandler<ScrollEvent>() {
			@Override
			public void handle(final ScrollEvent scrollEvent) {
				if (scrollEvent.getTotalDeltaY() > 100) {
					controller.requestDayViewFocus(-7);
				} else if (scrollEvent.getTotalDeltaY() < -100) {
					controller.requestDayViewFocus(7);
				}
			}
		});
		scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent> () {
			@Override
			public void handle(final KeyEvent keyEvent) {
				switch (keyEvent.getCode()) {
				case UP:
					controller.requestDayViewFocus(-7);
					break;
				case DOWN:
					controller.requestDayViewFocus(7);
					break;
				case LEFT:
					controller.requestDayViewFocus(-1);
					break;
				case RIGHT:
					controller.requestDayViewFocus(1);
					break;
				default:
					break;
				}
				
			}
		});
	}
	
	public void showRecipeBrowser(Recipe recipe) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Mampf.class.getResource("view/RecipeBrowser.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			RecipeBrowserController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setRecipe(recipe);
			dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void showShoppingList(LocalDate date, int day) {
		try {
			MampfData mampfData = MampfData.getMampfData();
			List<Condiment> shoppingList = new ArrayList<>();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Mampf.class.getResource("view/ShoppingList.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Einkaufszettel");
			ShoppingListController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			shoppingList = mampfData.getShoppingList(date, day);
			controller.setShoppingList(shoppingList);
			dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public Stage getPrimaryStage() {
		return stage;
	}
}