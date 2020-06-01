package model.entity;
import java.util.ArrayList;
import java.util.List;

public class Conversation {
	private int idConversation;
	private List<Message> messages;
	private List<User> participants;

	public int getIdConversation() {
		return idConversation;
	}

	public void setIdConversation(int idConversation) {
		this.idConversation = idConversation;
	}

	public Conversation() {
		messages = new ArrayList<>();
		participants = new ArrayList<>();
	}
	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	public List<Message> getMessages()
	{
		return this.messages;
	}
	
	public List<User> getParticipants() 
	{
		return this.participants;
	}
	
}
