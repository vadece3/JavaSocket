package com.vice.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;
import javafx.scene.control.TextArea;

public class ChatroomClientEndpoint extends Endpoint {

	Session session = null;
	public ChatroomClientEndpoint(TextArea textArea) throws URISyntaxException, DeploymentException, IOException {
		URI url = new URI("ws://localhost:8080/WebSocketJava/chatroomServerEndpoint");
		ContainerProvider.getWebSocketContainer().connectToServer(this, url);
		session.addMessageHandler(new ChatroomMessageHandler(textArea));
	}
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		
	}
	
	public void sendMessage(String message) throws IOException {
		session.getBasicRemote().sendText(message);
	}

}
