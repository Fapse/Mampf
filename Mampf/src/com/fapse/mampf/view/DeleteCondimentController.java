package com.fapse.mampf.view;

import com.fapse.mampf.model.Condiment;

import javafx.beans.property.ReadOnlyListWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class DeleteCondimentController {
	private Stage dialogStage;
	private boolean deleteClicked = false;
	private Condiment tempCondiment = new Condiment();
	@FXML
	private ChoiceBox<Condiment> condiment = new ChoiceBox<>();
	@FXML
	private Button cancel = new Button();
	@FXML
	private Button delete = new Button();
	@FXML
	private void initialize() {
	}
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	@FXML
	private void handleDelete() {
		tempCondiment.setName(condiment.getValue().getName());
		tempCondiment.setCategory(condiment.getValue().getCategory());
		tempCondiment.setQuantityUnit(condiment.getValue().getQuantityUnit());
		deleteClicked = true;
		dialogStage.close();
	}
	public boolean isDeleteClicked() {
		return deleteClicked;
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	public void setCondiment(Condiment condiment, ReadOnlyListWrapper<Condiment> condiments) {
		this.tempCondiment = condiment;
		this.condiment.setItems(condiments);
	}
}
