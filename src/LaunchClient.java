import model.Client.ChatClient;

public class LaunchClient {

	public static void main(String[] args) {
		// if (args.length < 2) return;

		String hostname = "127.0.0.1";// args[0];
		int port = 8989;// Integer.parseInt(args[1]);

		ChatClient client = new ChatClient(hostname, port);
		client.execute();
	}

}
