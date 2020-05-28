package model.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import presenter.PresenterConnection;

public class Client {
	private String hostname;
	private int port;
	private String userName;
	private PresenterConnection presenter;
	private  String password;

	
	public Client(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}
	
	public Client() {
		
	}

	public void execute() {
		ReadThread readThread;
		WriteThread writeThread;

		try {
			
			Socket socket = new Socket(getHostName(), getPort());

			System.out.println("Connected to the chat server");

			readThread = new ReadThread(socket, this);
			writeThread = new WriteThread(socket, this);

			readThread.start();
			writeThread.start();

		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O Error: " + ex.getMessage());
		}

	}

	public PresenterConnection getPresenter() {
		return presenter;
	}


	public String getUserName() {
		return password;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return this.port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getHostName() {
		return this.hostname;
	}
	public void setHostName(String hostname) {
		this.hostname = hostname;
	} 

}