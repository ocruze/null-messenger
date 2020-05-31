package gui.presenter;

import java.util.function.Consumer;

import org.json.JSONObject;

import gui.model.ModelConnection;
import gui.view.ViewConnection;
import model.client.Client;
import model.client.UserSession;
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
		switch(json.getString(Constants.KEY_MESSAGE).toString()) {
		case Constants.VALUE_ERROR_USER_NOT_FOUND:
			this.view.needRegister();
			break;
		case Constants.VALUE_ERROR_USER_ALREADY_REGISTERED:
			client.setWantRegister(false);
			this.view.userAlreadyExist();
			break;
		case Constants.VALUE_ERROR_PASSWORD_INCORRECT:
			break;
		}
	}

	public void onRequestSuccess(JSONObject json) {
		switch (json.getString(Constants.KEY_CLIENT_ACTION)) {

		case Constants.VALUE_ACTION_LOGIN:
			connectionAccepted(json);
			break;
		
		case Constants.VALUE_ACTION_REGISTER:
			client.setWantRegister(false);
			registerSuccess();
			break;

		default:
			break;
		}
		// System.out.println("ONREQUEST_SUCCESS");
	}

	public void registerSuccess() {
		this.view.registerSuccess();
	}
	public void connectionAccepted(JSONObject json) {
		String username = json.getString("username");
		int userId = json.getInt("idUser");
		this.view.prepareWindowChanged();
		UserSession.connect(username, userId);
		changeWindow(Window.Conversation);
	}

	public void login() {
		this.client.setHostName(model.getHostName());
		this.client.setPort(model.getPort());
		this.client.execute();
		this.client.getInvoker().login(model.getUserName(), model.getPassword());
	}

	public void register() {
		this.client.setHostName(model.getHostName());
		this.client.setPort(model.getPort());
		this.client.execute();
		this.client.getInvoker().register(model.getUserName(), model.getPassword());
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
