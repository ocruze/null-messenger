package model.Entity;
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
