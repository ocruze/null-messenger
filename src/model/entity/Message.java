package model.entity;

public class Message {
	private User sender;
	private String content;
	private String date;

	public Message(String content, User sender, String date) {
		this.content = content;
		this.sender = sender;
		this.date = date;
	}

	public User getSender() {
		return sender;
	}

	public String getContent() {
		return content;
	}

	public String getDate() {
		return date;
	}

}
