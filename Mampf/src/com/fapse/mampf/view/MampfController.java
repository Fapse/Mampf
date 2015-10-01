package com.fapse.mampf.view;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MampfController {

	@FXML
	private TableView tableView = new TableView();
	
	@FXML
	private ScrollPane scrollPane = new ScrollPane();
	
	@FXML
	private void initialize() {

	}
}
