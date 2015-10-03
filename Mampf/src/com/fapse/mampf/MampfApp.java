package com.fapse.mampf;

import java.time.LocalDate;
import com.fapse.mampf.model.DaySchedule;
import com.fapse.mampf.model.Meal;
import com.fapse.mampf.model.Recipe;
import javafx.application.Application;
import javafx.beans.property.ReadOnlySetWrapper;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MampfApp extends Application {

	private Stage stage;
	private BorderPane rootLayout;
	private LocalDate day = LocalDate.of(1977, 6, 1);
	private Recipe spatzen = new Recipe("Käsespatzen", "Rühren, hobeln, kochen");
	private Recipe pommes = new Recipe("Pommes", "Schnippeln, fritieren");
	private Recipe kuchen = new Recipe("Kuchen", "Rühren, backen");
	private Meal meal1 = new Meal(spatzen);
	private Meal meal2 = new Meal(pommes);
	private Meal meal3 = new Meal(kuchen);
	private Meal[] meals = {meal1, meal2};
	private DaySchedule daySched = new DaySchedule(day, meals);
	private ReadOnlySetWrapper<Meal> readMeals = new ReadOnlySetWrapper<>();
	
	public MampfApp() {
			daySched.getMealsSetWrapper().addListener(new SetChangeListener<Meal>() {
			@Override
			public void onChanged(SetChangeListener.Change<? extends Meal> c) {
				System.out.println("Neu dabei: " + c.getElementAdded().getRecipeName());
			}
		});
		daySched.addMeal(meal3);
		readMeals = daySched.getMealsSetWrapper();
		for (Meal meal : readMeals) {
			System.out.println("Es gibt " + meal.getRecipeName());
		}
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