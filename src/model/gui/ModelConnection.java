package model.gui;
public class ModelConnection {

	private static String username;
	private static String password;
	private static int port;
	private static String ip;
	
	public ModelConnection() {}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public static String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	} 
	
}
