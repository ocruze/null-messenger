package gui.presenter;

import java.util.List;

import gui.model.ModelConversation;
import gui.view.ViewConversation;
import model.client.Client;
import model.entity.Conversation;
import model.entity.User;

public class PresenterConversation {
	private ViewConversation view;
	private ModelConversation model;
	private Client client;

	public PresenterConversation(ViewConversation view, ModelConversation model, Client client) {
		this.view = view;
		this.model = model;
		this.client = client;
	}

	public void CreateConversation() {

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

}
