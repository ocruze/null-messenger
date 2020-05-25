import model.Client.ChatClient;
import java.util.Scanner;

public class LaunchClient {

	public static void main(String[] args) {
		// if (args.length < 2) return;

		System.out.print("Please type a hostname or an IPv4 address : ");
		Scanner sc = new Scanner(System.in);

		String hostname = sc.nextLine();

		System.out.print("Please type a port number : ");
		int port = Integer.parseInt(sc.nextLine());


		// String hostname = "127.0.0.1";// args[0];
		// int port = 8989;// Integer.parseInt(args[1]);

		ChatClient client = new ChatClient(hostname, port);
		client.execute();
	}

}
