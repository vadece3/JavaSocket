package com.vice.websocket.server;

import java.net.ServerSocket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Controller extends Application {
	
	private Server server;
	
	public static void main(String[] args) {
	      launch(args); 
	    }

		@Override
		public void start(Stage stage) throws Exception {
			
			server = new Server(new ServerSocket(8086));
			
			
			stage.setTitle("Server: Instant Messaging");
			final TextField textfield = new TextField();
			Button sendButton = new Button("Send");
			HBox hBox = new HBox();
			hBox.getChildren().addAll(textfield, sendButton);
			VBox vBox = new VBox();
			vBox.setPrefWidth(400); // Set preferred width
	        vBox.setPrefHeight(400); // Set preferred height
	        ScrollPane scrollPane = new ScrollPane(vBox);
	        VBox vBoxAll = new VBox();
	        vBoxAll.getChildren().addAll(scrollPane, hBox);
	        AnchorPane anchorPane = new AnchorPane(vBoxAll);
	        
	        // Optional: Set scroll policies
	        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	        
			vBox.heightProperty().addListener(new ChangeListener<Number>() {
			
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
						
						scrollPane.setVvalue((Double) arg2);
					}
			
			});
//			StackPane stackPane = new StackPane();
//			stackPane.getChildren().add(vBox);
			stage.setScene(new Scene(anchorPane, 600, 600));
//			final ChatroomClientEndpoint chatroomClientEndpoint = new ChatroomClientEndpoint(textarea);
			sendButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
				
						String messageToSend = textfield.getText();
						if (!messageToSend.isEmpty()) {
							HBox hBox = new HBox();
							hBox.setAlignment(Pos.CENTER_RIGHT);
							hBox.setPadding(new Insets(5, 5, 5, 10));
							
							Text text = new Text(messageToSend);
							TextFlow textFlow = new TextFlow(text);

							textFlow.setStyle("-fx-color: rgb(239,242,255); "+
							"-fx-background-color: rgb(15,125,242);"+
							" -fx-background-radius: 20px;");
							
							textFlow.setPadding(new Insets(5, 5, 5, 10));
							text.setFill(Color.color(0.934,0.945,0.996));
							
							hBox.getChildren().add(textFlow);
							vBox.getChildren().add(hBox);
							
							server.sendMessageToClient(messageToSend);
						}
						textfield.clear();
					
				}
			});
			server.receiveMessageFromClient(vBox);
			stage.show();
		}
		
		public static void addLabel(String messageFromClient, VBox vbox) {
			HBox hBox = new HBox();
			hBox.setAlignment(Pos.CENTER_LEFT);
			hBox.setPadding(new Insets(5, 5, 5, 10));
			
			Text text = new Text(messageFromClient);
			TextFlow textFlow = new TextFlow(text);
			
			textFlow.setStyle("-fx-background-color: black;"+
					" -fx-background-radius: 20px; -fx-fill: black;");
					
			textFlow.setPadding(new Insets(5, 5, 5, 10));
			text.setFill(Color.color(0.934,0.945,0.996));
			
			hBox.getChildren().add(textFlow);
			
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					vbox.getChildren().add(hBox);	
					
				}
			});
		}
		
		
}
