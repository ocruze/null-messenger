package model.entity;
import java.util.Calendar;
import java.util.Date;

public class Message {
	private User sender;
	private String message;
	private Date date;
	
	public Message(String message, User sender) {
		this.message = message;
		this.sender = sender;
		date = Calendar.getInstance().getTime();
	}
	public Message(String message, User sender, Date date) {
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

	public Date getDate() {
		return date;
	}
	
	
}
