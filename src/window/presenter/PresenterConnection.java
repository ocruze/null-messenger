package window.presenter;

import model.Client.ChatClient;
import window.model.ModelConnection;
import window.view.ViewConnection;

public class PresenterConnection {

	public ViewConnection view;
	public ModelConnection model;
	public ChatClient chatclient;


	public PresenterConnection(ViewConnection view, ModelConnection model)	 {
		this.view = view;
		this.model = model;
		this.chatclient = null;
	}
	
	public void login(String hostname, int port) {
		
		this.chatclient = new ChatClient(hostname, port);
		this.chatclient.execute();

		// System.out.println("Login :" +model.getUsername());
		
		
	}
	
	public void setPassword(String password) {
		model.setPassword(password);
	}
	public void setUsername(String username) {
		model.setUsername(username);
	}
	public void setPort(int port) {
		model.setPort(port);
	}
	public void setIp(String ip) {
		model.setIp(ip);
	}
}
