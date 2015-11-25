package com.fapse.mampf.view;

import com.fapse.mampf.model.Condiment;
import com.fapse.mampf.model.MampfData;
import com.fapse.mampf.model.Recipe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RecipeBrowserController {
	@FXML
	private Label recipeNameLabel;
	@FXML
	private Label recipeTextArea;
	
	private Stage dialogStage;
	private MampfData mampfData;
	private Recipe recipe = null;
		
	@FXML
	public void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		mampfData = MampfData.getMampfData();
	}
	
	public void setRecipe(Recipe recipe) {
		StringBuilder recipeText = new StringBuilder();
		recipeNameLabel.setText(recipe.getName());
		for (Condiment condiment : recipe.getCondiments()) {
			recipeText.append(condiment.toString() + System.lineSeparator());
		}
		recipeText.append(System.lineSeparator());
		recipeText.append(recipe.getRecipe());
		recipeTextArea.setText(recipeText.toString());
		this.recipe = recipe;
	}
	
	@FXML
	private void handleOK() {
		dialogStage.close();
	}
	@FXML
	private void handleLastRecipe() {
		this.recipe = mampfData.getNextRecipe(this.recipe, -1);
		if (this.recipe != null) {
			setRecipe(this.recipe);
		}
	}
	@FXML
	private void handleNextRecipe() {
		this.recipe = mampfData.getNextRecipe(this.recipe, +1);		
		if (this.recipe != null) {
			setRecipe(this.recipe);
		}
	}
}