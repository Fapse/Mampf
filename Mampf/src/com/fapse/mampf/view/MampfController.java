package com.fapse.mampf.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class MampfController {
	@FXML
	private GridPane dayGrid;
	
	@FXML
	private void initialize() {
		System.out.println("test");
		dayGrid.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println(event.getSource());
			}
		});
	}
}
