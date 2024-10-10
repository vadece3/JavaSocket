package com.vice.websocket;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;


public class Client extends Application {
    public static void main(String[] args) {
      launch(args); 
    }

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Instant Messaging");
		final TextField textfield = new TextField();
		Button sendButton = new Button("Send");
		HBox hBox = new HBox();
		hBox.getChildren().addAll(textfield, sendButton);
		TextArea textarea = new TextArea();
		textarea.setFocusTraversable(false);
		textarea.setEditable(false);
		VBox vBox = new VBox();
		vBox.getChildren().addAll(textarea, hBox);
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(vBox);
		stage.setScene(new Scene(stackPane, 250, 200));
		final ChatroomClientEndpoint chatroomClientEndpoint = new ChatroomClientEndpoint(textarea);
		sendButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					chatroomClientEndpoint.sendMessage(textfield.getText());
					textfield.clear();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		stage.show();
	}
}
