package com.fapse.mampf.view;

import java.util.List;

import com.fapse.mampf.model.Condiment;
import com.fapse.mampf.model.MampfData;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ShoppingListController {
	private Stage dialogStage;
	private MampfData mampfData;
	private List<Condiment> shoppingList;
	
	@FXML
	Button send = new Button();
	@FXML
	Button cancel = new Button();
	@FXML
	VBox listArea = new VBox();
	public ShoppingListController() {
		
	}
	public void initialize() {
		
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		mampfData = MampfData.getMampfData();
	}
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	public void setShoppingList(List<Condiment> list) {
		this.shoppingList = list;
		for (Condiment cond : shoppingList) {
			Label text = new Label(cond.toString());
			listArea.getChildren().add(text);
		}
	}
}
