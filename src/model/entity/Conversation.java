package model.entity;
import java.util.ArrayList;
import java.util.List;

public class Conversation {
	private List<Message> messages;
	private List<User> participants;

	public Conversation() {
		messages = new ArrayList<>();
		participants = new ArrayList<>();
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
