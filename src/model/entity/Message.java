package model.entity;

public class Message {
	private User sender;
	private String message;
	private String date;

	public Message(String message, User sender, String date) {
		this.message = message;
		this.sender = sender;
		this.date = date;
	}

	public User getSender() {
		return sender;
	}

	public String getMessage() {
		return message;
	}

	public String getDate() {
		return date;
	}

}
