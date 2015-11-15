package com.fapse.mampf.view;

import java.awt.Font;
import java.util.List;
import java.util.stream.Collectors;

import com.fapse.mampf.model.Condiment;
import com.fapse.mampf.model.CondimentCategory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShoppingListController {
	private Stage dialogStage;
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
	}
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	public void setShoppingList(List<Condiment> list) {
		this.shoppingList = list;
		for (CondimentCategory cat : CondimentCategory.values()) {
			List<Condiment> tmpConds = shoppingList.stream().filter(c -> c.getCategory().equals(cat)).collect(Collectors.toList());
			if (!tmpConds.isEmpty()) {
				Text text = new Text(cat.getCategoryName());
				text.setFill(Color.CORAL);
				listArea.getChildren().add(text);
			}
			for (Condiment tmpCond : tmpConds) {
				Text text = new Text(tmpCond.toString());
				listArea.getChildren().add(text);
			}			
		}
	}
}