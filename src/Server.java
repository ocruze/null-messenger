import model.Server.ChatServer;
import java.util.Scanner;


public class Server {
	public static void main(String[] args) {
		Scanner saisie = new Scanner(System.in);

		System.out.print("Please type a port number : ");
		int port = Integer.parseInt(saisie.nextLine());

		saisie.close();

		ChatServer server = new ChatServer(port);
		server.execute();
	}
}
