package gui.presenter;

import java.util.List;
import java.util.function.Consumer;

import org.json.JSONObject;

import gui.model.ModelConversation;
import gui.view.ViewConversation;
import model.client.Client;
import model.client.UserSession;
import model.entity.Conversation;
import model.entity.User;
import util.Constants;

public class PresenterConversation implements IPresenter {
	private ViewConversation view;
	private ModelConversation model;
	private Client client;
	public Consumer<Window> changeWindow;

	public PresenterConversation(ViewConversation view, ModelConversation model, Client client) {
		this.view = view;
		this.model = model;
		this.client = client;
		init();
		LoadUsers();
	}

	public void init() {
		client.setOnRequestFailedConversation((json) -> onRequestFailureConversation(json));
		client.setOnRequestSuccessConversation((json) -> onRequestSuccessConversation(json));
	}

	public void LoadUsers() {
		client.getInvoker().loadUsers();
	}

	/**
	 * public List<Conversation> LoadConversations() {
	 * 
	 * }
	 **/
	public List<User> getListParticipantsNewConv() {
		return this.model.getListParticipantsNewConv();
	}

	public void setListParticipantsNewConv(List<User> listParticipantsNewConv) {
		this.model.setListParticipantsNewConv(listParticipantsNewConv);
	}

	public List<Conversation> getListConversations() {
		return this.model.getListConversations();
	}

	public void setListConversations(List<Conversation> listConversations) {
		this.model.setListConversations(listConversations);
	}

	public String getUserName() {
		return this.model.getUserName();
	}

	public void setUserName(String userName) {
		this.model.setUserName(userName);
	}

	public int getIdUser() {
		return this.model.getIdUser();
	}

	public void setIdUser(int idUser) {
		this.model.setIdUser(idUser);
	}

	@Override
	public void changeWindow(Window window) {
		this.changeWindow.accept(window);

	}

	@Override
	public void setChangeWindow(Consumer<Window> window) {
		this.changeWindow = window;

	}

	
	public void onRequestSuccessConversation(JSONObject json) {
		switch (json.getString(Constants.KEY_CLIENT_ACTION)) {
		case Constants.VALUE_ACTION_DISCONNECT:
			UserSession.disconnect();
			break;
		case Constants.VALUE_ACTION_GET_USERS:
			System.out.println("recover all users");
			break;
		
		}

	}

	public void onRequestFailureConversation(JSONObject json) {
		switch (json.getString(Constants.KEY_CLIENT_ACTION)) {
		case Constants.VALUE_ACTION_GET_USERS:
			System.out.println("bad");
			break;

		}
	}

}
