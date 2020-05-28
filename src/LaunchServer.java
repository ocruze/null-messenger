import java.util.Scanner;

import model.server.Server;

public class LaunchServer {

	public static void main(String[] args) {
		Scanner saisie = new Scanner(System.in);

		System.out.print("Please type a port number : ");
		int port = Integer.parseInt(saisie.nextLine());

		saisie.close();

		Server server = new Server(port);
		server.execute();
	}
}
