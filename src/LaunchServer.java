import model.Server.ChatServer;

public class LaunchServer {
	public static void main(String[] args) {
		/*
		 * if (args.length < 1) {
		 * System.out.println("Syntax: java ChatServer <port-number>"); System.exit(0);
		 * }
		 */

		int port = 8989;// Integer.parseInt(args[0]);

		ChatServer server = new ChatServer(port);
		server.execute();
	}
}
