import model.client.Client;
import presenter.PresenterConnection;
import view.ViewConnection;

public class LaunchClientGUI {
	public static void main(String[] args) {
		ViewConnection view = new ViewConnection();
		PresenterConnection presenter = new PresenterConnection(view, new Client());
		view.setPresenter(presenter);	
	}
}
