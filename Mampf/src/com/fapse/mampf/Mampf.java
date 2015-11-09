package com.fapse.mampf;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fapse.mampf.model.Recipe;
import com.fapse.mampf.model.Condiment;
import com.fapse.mampf.view.OverviewController;
import com.fapse.mampf.view.RecipeBrowserController;
import com.fapse.mampf.view.ShoppingListController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Mampf extends Application {

	private Stage stage;
	private AnchorPane rootLayoutDayOverview;

	public Mampf() {
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		this.stage.setTitle("Mampf");
		setupDayOverview();
		Scene scene = new Scene(rootLayoutDayOverview);
		this.stage.setScene(scene);
		this.stage.show();	
	}
	
	private void setupDayOverview() throws Exception {
		FXMLLoader loaderDayOverview = new FXMLLoader();
		loaderDayOverview.setLocation(Mampf.class.getResource("view/MampfOverview.fxml"));
		rootLayoutDayOverview = (AnchorPane) loaderDayOverview.load();
		OverviewController controller = loaderDayOverview.getController();
		controller.setMampf(this);		
	}
	
	public void showRecipeBrowser(Recipe recipe) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Mampf.class.getResource("view/RecipeBrowser.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
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
			List<Condiment> shoppingList = new ArrayList<>();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Mampf.class.getResource("view/ShoppingList.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			ShoppingListController controller = loader.getController();
			controller.setDialogStage(dialogStage);
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