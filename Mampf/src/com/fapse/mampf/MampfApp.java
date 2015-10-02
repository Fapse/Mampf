package com.fapse.mampf;

import java.time.LocalDate;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MampfApp extends Application {

	private Stage stage;
	private BorderPane rootLayout;
	//private ObservableMap<LocalDate, Recipe> mealAgenda = FXCollections.checkedObservableMap(null, null, null);
	private LocalDate day1 = LocalDate.of(1977, 5, 31);
	private Recipe spatzen = new Recipe("Käsespatzen", "Rühren, hobeln, kochen");
	private Recipe pommes = new Recipe("Pommes", "Schnippeln, fritieren");
	private Meal mittagessen = new Meal(spatzen, day1);
	
	public MampfApp() {
		System.out.println("Die Mahlzeit ist: " + mittagessen.getRecipeName());
		
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