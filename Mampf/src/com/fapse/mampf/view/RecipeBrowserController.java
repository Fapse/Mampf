package com.fapse.mampf.view;

import com.fapse.mampf.model.Condiment;
import com.fapse.mampf.model.Recipe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RecipeBrowserController {
	@FXML
	private Label recipeNameLabel;
	@FXML
	private Label condimentsLabel;
	@FXML
	private Label recipeTextLabel;
	@FXML
	private Button okButton;
	
	private Stage dialogStage;
		
	@FXML
	public void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		recipeTextLabel.setWrapText(true);
	}
	
	public void setRecipe(Recipe recipe) {
		StringBuilder condiments = new StringBuilder();
		recipeNameLabel.setText(recipe.getName());
		for (Condiment condiment : recipe.getCondiments()) {
			condiments.append(condiment.toString() + System.lineSeparator());
		}
		condimentsLabel.setText(condiments.toString());
		recipeTextLabel.setText(recipe.getRecipe());
	}
	
	@FXML
	private void handleOK() {
		dialogStage.close();
	}
}