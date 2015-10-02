package com.fapse.mampf;

import java.time.LocalDate;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;
import javafx.application.Application;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MampfApp extends Application {

	private Stage stage;
	private BorderPane rootLayout;
	private LocalDate[] days = {LocalDate.of(1977, 5, 29), LocalDate.of(1977, 5, 30), LocalDate.of(1977, 5, 31)};
	private Recipe spatzen = new Recipe("Käsespatzen", "Rühren, hobeln, kochen");
	private Recipe pommes = new Recipe("Pommes", "Schnippeln, fritieren");
	private Meal mittagessen = new Meal(spatzen, days);
	
	public MampfApp() {
		System.out.println("Die Mahlzeit ist: " + mittagessen.getRecipeName());
		spatzen.setName("Kässpätzle");
		System.out.println("Die Mahlzeit ist: " + mittagessen.getRecipeName());
		mittagessen.getDateSetWrapper().addListener(new SetChangeListener<LocalDate>() {
			@Override
			public void onChanged(SetChangeListener.Change<? extends LocalDate> c) {
				System.out.println("Neu dabei: " + c.getElementAdded().toString());
			}
		});
		mittagessen.addDate(LocalDate.of(1977, 6, 1));
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