package presenter;

import model.client.Client;
import view.ViewConnection;

public class PresenterConnection {

	public ViewConnection view;
	public Client client;

	public PresenterConnection(ViewConnection view, Client client) {
		this.view = view;
		this.client = client;
	}

	public void login() {
		this.client.execute();
	}

	public void setPassword(String password) {
		client.setPassword(password);
	}

	public void setUsername(String username) {
		client.setUserName(username);
	}

	public void setPort(int port) {
		client.setPort(port);
	}

	public void setIp(String hostname) {
		client.setHostName(hostname);
	}
	
}
