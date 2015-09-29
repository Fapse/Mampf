package com.fapse.mampf.view;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MampfController {
	private GridPane dayGrid = new GridPane();
	
	@FXML
	private ScrollPane scrollPane = new ScrollPane();
	
	@FXML
	private void initialize() {
		
		//ArrayList<Label> labelList = new ArrayList<Label>();
		int days = 0;
		for(int row = 0; row < 7; row++) {
			dayGrid.getColumnConstraints().add(new ColumnConstraints(120));
			dayGrid.getRowConstraints().add(new RowConstraints(120));
			for(int col = 0; col < 7; col++) {
				LocalDate date = LocalDate.now();
				date = date.plusDays(days++);
				Text text = new Text(date.toString());
				TextFlow textFlow = new TextFlow(text);
				dayGrid.add(textFlow, col, row);
				textFlow.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if (event.getSource() instanceof TextFlow) {
							TextFlow tf = (TextFlow) event.getSource();
							System.out.println(tf.getChildren().get(0));
						}
											}
				});
				
				text.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if (event.getSource() instanceof Text) {
							Text text = (Text) event.getSource();
							System.out.println(text.toString());
						}
					}
				});
			}
		}
		dayGrid.setGridLinesVisible(true);
		scrollPane.setContent(dayGrid);
	}
}
