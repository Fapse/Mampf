package com.fapse.mampf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
	}
	
	public Stage getPrimaryStage() {
		return stage;
	}
}