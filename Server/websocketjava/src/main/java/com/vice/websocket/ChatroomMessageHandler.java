package com.vice.websocket;

import java.io.StringReader;

import javax.json.Json;

import jakarta.websocket.MessageHandler;

import javafx.scene.control.TextArea;

public class ChatroomMessageHandler implements MessageHandler.Whole<String> {

	TextArea textArea = null;
	public ChatroomMessageHandler(TextArea textArea) {
		this.textArea = textArea;
	}
	public void onMessage(String message) {
		textArea.appendText(Json.createReader(new StringReader(message)).readObject().getString("message")+"\n");
	}

}
