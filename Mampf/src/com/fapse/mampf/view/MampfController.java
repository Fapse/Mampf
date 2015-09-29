package com.fapse.mampf.view;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MampfController {
	private GridPane dayGrid = new GridPane();
	
	@FXML
	private ScrollPane scrollPane = new ScrollPane();
	
	@FXML
	private void initialize() {
		
		//ArrayList<Label> labelList = new ArrayList<Label>();
		
		for(int row = 0; row < 7; row++) {
			dayGrid.getColumnConstraints().add(new ColumnConstraints(120));
			dayGrid.getRowConstraints().add(new RowConstraints(120));
			for(int col = 0; col < 7; col++) {
				Label label = new Label("Test_" + row + col);
				dayGrid.add(label, col, row);
				label.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						System.out.println(event.getSource());
						System.out.println(event.getPickResult());
					}
				});
			}
		}
		dayGrid.setGridLinesVisible(true);
		scrollPane.setContent(dayGrid);
	}
}
