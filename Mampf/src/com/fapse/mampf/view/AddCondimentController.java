package com.fapse.mampf.view;

import com.fapse.mampf.model.Condiment;
import com.fapse.mampf.model.CondimentCategory;
import com.fapse.mampf.model.QuantityUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCondimentController {
	private Stage dialogStage;
	private boolean okClicked = false;
	private Condiment condiment;
	private CondimentCategory category;
	private QuantityUnit unit;
	private String name;
	@FXML
	private TextField condimentName = new TextField();
	@FXML
	private ChoiceBox<CondimentCategory> condimentCategory = new ChoiceBox<>();
	@FXML
	private ChoiceBox<QuantityUnit> condimentQuantityUnit = new ChoiceBox<>();
	@FXML
	private Button cancel = new Button();
	@FXML
	private Button ok = new Button();
	@FXML
	private void initialize() {
		ObservableList<CondimentCategory> categories = FXCollections.observableArrayList();
		for (CondimentCategory cc : CondimentCategory.values()) {
			categories.add(cc);
		}
		condimentCategory.setItems(categories);
		ObservableList<QuantityUnit> quantities = FXCollections.observableArrayList();
		for (QuantityUnit cc : QuantityUnit.values()) {
			quantities.add(cc);
		}
		condimentQuantityUnit.setItems(quantities);
	}
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	@FXML
	private void handleOk() {
		name = condimentName.getText();
		category = condimentCategory.getValue();
		unit = condimentQuantityUnit.getValue();
		if (!(name == null) && !(category == null)&& !(unit == null)) {
			condiment.name = name;
			condiment.category = category;
			condiment.quantityUnit = unit;
			okClicked = true;
		}
		dialogStage.close();
	}
	public boolean isOkClicked() {
		return okClicked;
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	public void setCondiment(Condiment condiment) {
		this.condiment = condiment;
	}
}
