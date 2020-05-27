package window.model;

public class ModelConnection {

	private static String username;
	private static String password;
	private int port;
	private String ip;
	
	public ModelConnection() {}
	public static String getUsername() {
		return ModelConnection.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public static String getPassword() {
		return ModelConnection.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	} 
	
}
