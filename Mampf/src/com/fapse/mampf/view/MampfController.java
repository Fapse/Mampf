package com.fapse.mampf.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class MampfController {
	@FXML
	private GridPane dayGrid;
	
	@FXML
	private void initialize() {
		Label date1 = new Label("Mon, 12/12/2015");
		dayGrid.add(date1, 0, 0);
		dayGrid.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println(event.getSource());
				System.out.println(event.getPickResult());
			}
		});
	}
}
