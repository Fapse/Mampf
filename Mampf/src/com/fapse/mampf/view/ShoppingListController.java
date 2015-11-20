package com.fapse.mampf.view;

import java.util.List;
import java.util.stream.Collectors;

import com.fapse.mampf.model.Condiment;
import com.fapse.mampf.model.CondimentCategory;
import com.fapse.mampf.model.MampfData;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ShoppingListController {
	private Stage dialogStage;
	private List<Condiment> shoppingList;
	private StringBuilder shoppingListText = new StringBuilder();
	
/*	@FXML
	Button print = new Button();
	@FXML
	Button cancel = new Button();*/
	@FXML
	VBox listArea = new VBox();
	public ShoppingListController() {
		
	}
	public void initialize() {
		
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	@FXML
	private void handlePrint() {
		MampfData.getMampfData().printShoppingList(shoppingListText.toString());
	}
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	public void setShoppingList(List<Condiment> list) {
		this.shoppingList = list;
		shoppingListText.append("Einkaufszettel" + System.lineSeparator());
		for (CondimentCategory cat : CondimentCategory.values()) {
			List<Condiment> tmpConds = shoppingList.stream().filter(c -> c.getCategory().equals(cat)).collect(Collectors.toList());
			if (!tmpConds.isEmpty()) {
				Text text = new Text(cat.getCategoryName());
				text.setFont(Font.font("Arial", FontWeight.BOLD, 16));
				text.setFill(Color.CORAL);
				listArea.getChildren().add(text);
				shoppingListText.append(System.lineSeparator() + cat.getCategoryName() + System.lineSeparator());
			}
			for (Condiment tmpCond : tmpConds) {
				Text text = new Text(tmpCond.toString());
				text.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
				listArea.getChildren().add(text);
				shoppingListText.append(tmpCond.toString() + System.lineSeparator());
			}			
		}
	}
}