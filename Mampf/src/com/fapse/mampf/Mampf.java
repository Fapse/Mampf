package com.fapse.mampf;

import com.fapse.mampf.view.OverviewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Mampf extends Application {

	private Stage stage;
	private AnchorPane rootLayout;
	
	public Mampf() {
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		this.stage.setTitle("Mampf");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Mampf.class.getResource("view/MampfOverview.fxml"));
		rootLayout = (AnchorPane) loader.load();
		OverviewController controller = loader.getController();
		controller.setMampf(this);
		Scene scene = new Scene(rootLayout);
		this.stage.setScene(scene);
		this.stage.show();		
	}
	
	public Stage getPrimaryStage() {
		return stage;
	}
}