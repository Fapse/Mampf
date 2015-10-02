package com.fapse.mampf.view;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MampfController {
	@FXML
	private TableView tableView = new TableView();
	@FXML
	private TableColumn col1 = new TableColumn();
	@FXML
	private TableColumn col2 = new TableColumn();
	@FXML
	private TableColumn col3 = new TableColumn();
	@FXML
	private TableColumn col4 = new TableColumn();
	@FXML
	private TableColumn col5 = new TableColumn();
	@FXML
	private TableColumn col6 = new TableColumn();
	@FXML
	private TableColumn col7 = new TableColumn();
	@FXML
	private TableColumn col8 = new TableColumn();
	@FXML
	private TableColumn col9 = new TableColumn();
	private List<TableColumn> colList = new ArrayList<>();

	@FXML
	private ScrollPane scrollPane = new ScrollPane();
	
	public MampfController() {
		colList.add(col1);
		colList.add(col2);
		colList.add(col3);
		colList.add(col4);
		colList.add(col5);
		colList.add(col6);
		colList.add(col7);
		colList.add(col8);
		colList.add(col9);
	}
	
	@FXML
	private void initialize() {

	}
}
