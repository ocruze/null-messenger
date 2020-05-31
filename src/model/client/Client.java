package model.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

import org.json.JSONObject;

import gui.presenter.IPresenter.Window;
import gui.presenter.PresenterConnection;

public class Client {

	private String hostname;
	private int port;
	private PresenterConnection presenter;
	private int idUser;
	private ReadThread readThread;
	private RequestInvoker requestInvoker;

	private JSONObject json;
	private boolean wantRegister;
	private Window currentPresenter;

	private Consumer<JSONObject> onRequestSuccess;
	private Consumer<JSONObject> onRequestFailed;

	private Consumer<JSONObject> onRequestSuccessConversation;
	private Consumer<JSONObject> onRequestFailedConversation;

	/**
	 * public Client(String hostname, int port) { this.hostname = hostname;
	 * this.port = port; }
	 **/

	public Client() {

	}

	public void execute() {
		readThread = null;

		try {
			Socket socket = new Socket(getHostName(), getPort());

			System.out.println("Connected to the chat server");

			this.readThread = new ReadThread(socket, this);
			this.requestInvoker = new RequestInvoker(socket);

			readThread.start();
		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O Error: " + ex.getMessage());
		}

	}

	public RequestInvoker getInvoker() {
		return this.requestInvoker;
	}

	public Window getCurrentPresenter() {
		return currentPresenter;
	}

	public void setCurrentPresenter(Window currentPresenter) {
		this.currentPresenter = currentPresenter;
	}

	public boolean isWantRegister() {
		return wantRegister;
	}

	public void setWantRegister(boolean wantRegister) {
		this.wantRegister = wantRegister;
	}

	public void setOnRequestFailedConversation(Consumer<JSONObject> onRequestFailed) {
		this.onRequestFailedConversation = onRequestFailed;
	}

	public void setOnRequestSuccessConversation(Consumer<JSONObject> onRequestSuccess) {
		this.onRequestSuccessConversation = onRequestSuccess;
	}

	public void onRequestFailedConversation(JSONObject json) {
		this.onRequestFailedConversation.accept(json);
	}

	public void onRequestSuccessConversation(JSONObject json) {
		this.onRequestSuccessConversation.accept(json);
	}

	public void setOnRequestFailed(Consumer<JSONObject> onRequestFailed) {
		this.onRequestFailed = onRequestFailed;
	}

	public void setOnRequestSuccess(Consumer<JSONObject> onRequestSuccess) {
		this.onRequestSuccess = onRequestSuccess;
	}

	public void onRequestFailed(JSONObject json) {
		this.onRequestFailed.accept(json);
	}

	public void onRequestSuccess(JSONObject json) {
		this.onRequestSuccess.accept(json);
	}

	public PresenterConnection getPresenter() {
		return presenter;
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

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

}