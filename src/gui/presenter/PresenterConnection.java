package gui.presenter;

import org.json.JSONObject;

import gui.model.ModelConnection;
import gui.view.ViewConnection;
import model.client.Client;
public class PresenterConnection {

	private ViewConnection view;
	private Client client;
	private ModelConnection model;

	

	public PresenterConnection(ViewConnection view, ModelConnection model, Client client) {
		this.view = view;
		this.model = model;
		this.client = client;
		init();
	}

	public void init() {
		client.setOnRequestFailed((json) -> onRequestFailure(json));
	}
	
	public void onRequestFailure(JSONObject json){
		System.out.println("ONREQUESTFAILURE");
		this.view.needRegister();
	}

	public void onRequestSuccess(JSONObject json){
		System.out.println("ONREQUEST_SUCCESS");
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
	
}
