package model.client;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class UserSession {
	private static AtomicReference<String> username;
	private static AtomicInteger userId;
	private static AtomicInteger conversationId;
	
	
	static {
		username = new AtomicReference<String>(null);
		userId = new AtomicInteger(-1);
		conversationId = new AtomicInteger(-1);
	}
	
	private static void setUsername(String username) {
		UserSession.username.set(username);
	}
	
	private static void setUserId(Integer userId) {
		UserSession.userId.set(userId);
	}
	
	public static void connect(String username, int userId) {
		setUsername(username);
		setUserId(userId);
	}
	
	public static String getConnectedUsername () {
		if(username.get() != null) return username.get();
		else throw new IllegalStateException("UserSession not started");
	}
	
	public static int getConnectedUserId () {
		if(userId.get() != -1) return userId.get();
		else throw new IllegalStateException("UserSession not started");
	}
	
	public static boolean isConnected () {
		return username.get() != null;
	}
	
	public static int getConversationId() {
		return conversationId.get();
	}
	
	public static void setConversationId(int convId) {
		conversationId.set(convId);
	}
	
	public static void unsetConversationId() {
		conversationId.set(-1);
	}
	
	public static void disconnect() {
		setUsername(null);
		setUserId(-1);
	}
	
	

	
	
	
	
}
