import model.Client.ChatClient;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		// if (args.length < 2) return;

		System.out.print("Please type a hostname or an IPv4 address : ");
		Scanner sc = new Scanner(System.in);

		String hostname = sc.nextLine();

		System.out.print("Please type a port number : ");
		int port = Integer.parseInt(sc.nextLine());

		ChatClient client = new ChatClient(hostname, port);
		client.execute();
	}

}
