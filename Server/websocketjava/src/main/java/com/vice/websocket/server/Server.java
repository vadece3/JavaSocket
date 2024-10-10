package com.vice.websocket.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.scene.layout.VBox;

public class Server {
	
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	

	public Server(ServerSocket serverSocket) {
		try {
			this.serverSocket = serverSocket;
			this.socket = serverSocket.accept();
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		} catch (Exception e) {
			System.out.println("Error creating server.");
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

	public void receiveMessageFromClient(VBox vBox) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (socket.isConnected()) {
					String messageFromClient;
					try {
						messageFromClient = bufferedReader.readLine();
						Controller.addLabel(messageFromClient, vBox);
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

	public void sendMessageToClient(String messageToSend) {
		
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
