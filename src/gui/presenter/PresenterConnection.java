package gui.presenter;

import java.util.function.Consumer;

import org.json.JSONObject;

import gui.model.ModelConnection;
import gui.view.ViewConnection;
import model.client.Client;
import util.Constants;

public class PresenterConnection implements IPresenter{


	private ViewConnection view;
	private Client client;
	private ModelConnection model;
	//private boolean isLogIn;

	public Consumer<Window> changeWindow;

	public void changeWindow(Window window) {
		this.changeWindow.accept(window);
	}
	
	public void setChangeWindow(Consumer<Window> window) {
		this.changeWindow = window;	
	}

	public PresenterConnection(ViewConnection view, ModelConnection model, Client client) {
		this.view = view;
		this.model = model;
		this.client = client;
		init();
	}

	public void init() {
		client.setOnRequestFailed((json) -> onRequestFailure(json));
		client.setOnRequestSuccess((json) -> onRequestSuccess(json));
	}

	public void onRequestFailure(JSONObject json) {
		System.out.println("ONREQUESTFAILURE");
		this.view.needRegister();
	}

	public void onRequestSuccess(JSONObject json) {
		switch (json.getString(Constants.KEY_CLIENT_ACTION)) {

		case Constants.VALUE_ACTION_LOGIN:
			connectionAccepted();
			break;

		default:
			break;
		}
		// System.out.println("ONREQUEST_SUCCESS");
	}

	public void connectionAccepted() {
		this.view.prepareWindowChanged();
		changeWindow(Window.Conversation);
		//setLogIn(true);
	}

	public void login() {
		this.client.setHostName(model.getHostName());
		this.client.setUserName(model.getUserName());
		this.client.setPort(model.getPort());
		this.client.setPassword(model.getPassword());
		this.client.execute();
	}

	public void register() {
		this.client.setHostName(model.getHostName());
		this.client.setUserName(model.getUserName());
		this.client.setPort(model.getPort());
		this.client.setPassword(model.getPassword());
		this.client.register();
	}

	public void setPassword(String password) {
		model.setPassword(password);
	}

	public void setUsername(String username) {
		model.setUserName(username);
	}

	public void setPort(int port) {
		model.setPort(port);
	}

	public void setHostName(String hostname) {
		model.setHostName(hostname);
	}

	public Client getClient() {
		return this.client;
	}




}
