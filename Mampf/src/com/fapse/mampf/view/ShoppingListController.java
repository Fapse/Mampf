package com.fapse.mampf.view;

import java.util.List;

import com.fapse.mampf.model.Condiment;
import com.fapse.mampf.model.MampfData;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	TextFlow listArea = new TextFlow();
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
	public void setShoppingList(List list) {
		this.shoppingList = shoppingList;
	}
}
