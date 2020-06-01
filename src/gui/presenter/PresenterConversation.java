package gui.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.model.ModelConversation;
import gui.view.ViewConversation;
import model.client.Client;
import model.client.UserSession;
import model.entity.Conversation;
import model.entity.Message;
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
		LoadConversations();
	}

	public void init() {
		client.setOnRequestFailedConversation((json) -> onRequestFailureConversation(json));
		client.setOnRequestSuccessConversation((json) -> onRequestSuccessConversation(json));
	}

	public void LoadUsers() {
		client.getInvoker().loadUsers();
	}

	public void LoadConversations() {
		client.getInvoker().loadConversations();
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
			JSONArray jsonArray = json.getJSONArray(Constants.KEY_USERS);
			List<User> listUsers = new ArrayList<User>();
			jsonArray.forEach(item -> {
				JSONObject obj = (JSONObject) item;
				listUsers.add(new User(Integer.valueOf(obj.get(Constants.KEY_ID_USER).toString()),
						obj.get(Constants.KEY_USERNAME).toString()));
			});

			listUsers.remove(new User(UserSession.getConnectedUserId(), UserSession.getConnectedUsername()));
			this.model.setListParticipantsNewConv(listUsers);
			this.view.loadUsers(this.model.getListParticipantsNewConv());
			break;
		case Constants.VALUE_ACTION_GET_CONVERSATIONS:
			if (!json.getString(Constants.KEY_INFO).equals(Constants.VALUE_NONE_MESSAGE)) {

				JSONArray jsonArrayConversation = json.getJSONArray(Constants.KEY_CONVERSATIONS);
				List<Conversation> listConversation = new ArrayList<Conversation>();
				List<Message> listMessage = new ArrayList<Message>();
				List<User> listUser = new ArrayList<User>();

				Conversation conversation = new Conversation();
				jsonArrayConversation.forEach(item -> {
					JSONArray array = (JSONArray) item;
					array.forEach(message -> {
						JSONObject obj = (JSONObject) message;
						int id = Integer.valueOf(obj.get(Constants.KEY_ID_SENDER).toString());
						int idConversation = Integer.valueOf(obj.get(Constants.KEY_ID_CONVERSATION).toString());
						conversation.setIdConversation(idConversation);
						User user = model.getUser(id);
						if (user == null && UserSession.getConnectedUserId() == id) {
							user = new User(UserSession.getConnectedUserId(), UserSession.getConnectedUsername());
						}
						Message newMessage = new Message(obj.get(Constants.KEY_MESSAGE_CONTENT).toString(), user,
								obj.get(Constants.KEY_MESSANGE_DATE).toString());
						listMessage.add(newMessage);
						listUser.add(user);
					});
					conversation.setMessages(listMessage);
					conversation.setParticipants(listUser);
					listConversation.add(conversation);
				});

				this.model.setListConversations(listConversation);
				this.view.loadConversations(this.model.getListConversations());

			}
			// System.out.println(this.model.getListConversations());
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
