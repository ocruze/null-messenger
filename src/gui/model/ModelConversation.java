package gui.model;

import java.util.ArrayList;
import java.util.List;

import model.entity.Conversation;
import model.entity.User;

public class ModelConversation {
	private List<User> listParticipantsNewConv;
	private List<Conversation> listConversations;
	private List<User> listUser;
	private String userName;
	private int idUser;

	public ModelConversation() {
		this.userName = "";
		this.idUser = 0;
		this.listConversations = new ArrayList<Conversation>();
		this.listParticipantsNewConv = new ArrayList<User>();
	}

	public ModelConversation(String userName, int idUser) {
		this.userName = userName;
		this.idUser = idUser;
		this.listConversations = new ArrayList<Conversation>();
		this.listParticipantsNewConv = new ArrayList<User>();
	}

	public ModelConversation(String userName, int idUser, List<Conversation> listConversations) {
		this.userName = userName;
		this.idUser = idUser;
		this.listConversations = new ArrayList<Conversation>();
		this.listConversations.addAll(listConversations);
		this.listParticipantsNewConv = new ArrayList<User>();
	}

	public List<User> getListParticipantsNewConv() {
		return listParticipantsNewConv;
	}

	public void setListParticipantsNewConv(List<User> listParticipantsNewConv) {
		this.listParticipantsNewConv = listParticipantsNewConv;
	}

	public List<Conversation> getListConversations() {
		return listConversations;
	}

	public void setListConversations(List<Conversation> listConversations) {
		this.listConversations = listConversations;
	}
	
	public User getUser(int id) {
		for(User user : this.listUser) {
			if(user.getIdUser() == id) {
				return user;
			}
		}
		return null;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public List<User> getListUser() {
		return listUser;
	}

	public void setListUser(List<User> listUser) {
		this.listUser = listUser;
	}
	

}
