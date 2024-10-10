package com.vice.websocket.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.scene.layout.VBox;

public class Client {
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	

	public Client(Socket socket) {
		try {
			this.socket = socket;
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		} catch (Exception e) {
			System.out.println("Error creating client.");
			e.printStackTrace();
			
		}
		
	}
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter ) {
		try {
			if (bufferedReader != null) {
				 bufferedReader.close();
			}
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
			if (socket != null) {
				socket.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void receiveMessageFromServer(VBox vBox) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (socket.isConnected()) {
					String messageFromServer;
					try {
						messageFromServer = bufferedReader.readLine();
						Controller.addLabel(messageFromServer, vBox);
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("Error received to the client");
						closeEverything(socket, bufferedReader, bufferedWriter);
						break;
					}
					
				}
				
			}
		}).start();
		
	}

	public void sendMessageToServer(String messageToSend) {
		
		try {
			bufferedWriter.write(messageToSend);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error sending to the client");
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
	}
	
}
