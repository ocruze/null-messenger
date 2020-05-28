package presenter;

import model.client.Client;
import model.gui.ModelConnection;
import view.ViewConnection;

public class PresenterConnection {

	public ViewConnection view;
	public ModelConnection model;
	public Client client;

	public PresenterConnection(ViewConnection view, ModelConnection model) {
		this.view = view;
		this.model = model;
		this.client = null;
	}

	public void login() {

		this.client = new Client(model.getIp(), model.getPort());
		this.client.execute();

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
