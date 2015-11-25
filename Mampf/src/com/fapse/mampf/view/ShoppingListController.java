package com.fapse.mampf.view;

import java.util.List;
import java.util.stream.Collectors;

import com.fapse.mampf.model.Condiment;
import com.fapse.mampf.model.CondimentCategory;
import com.fapse.mampf.model.MampfData;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ShoppingListController {
	private Stage dialogStage;
	private List<Condiment> shoppingList;
	private StringBuilder shoppingListText = new StringBuilder();
	
	@FXML
	Label listText = new Label();
	
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
				shoppingListText.append(cat.getCategoryName() + System.lineSeparator());
			}
			for (Condiment tmpCond : tmpConds) {
				shoppingListText.append(tmpCond.toString() + System.lineSeparator());
			}			
		}
		listText.setText(shoppingListText.toString());
	}
}