package com.fapse.mampf;

import com.fapse.mampf.model.MampfData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MampfApp extends Application {

	private Stage stage;
	private BorderPane rootLayout;
	private MampfData mampfData = MampfData.getMampfData();
	
	public MampfApp() {
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		this.stage.setTitle("Mampf");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MampfApp.class.getResource("view/MampfOverview.fxml"));
		rootLayout = (BorderPane) loader.load();
		Scene scene = new Scene(rootLayout);
		this.stage.setScene(scene);
		this.stage.show();		
	}
}