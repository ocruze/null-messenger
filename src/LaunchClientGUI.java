import javax.swing.SwingUtilities;

import gui.model.ModelConnection;
import gui.presenter.PresenterConnection;
import gui.view.ViewConnection;
import model.client.Client;

public class LaunchClientGUI {
	public static void main(String[] args) {
		
			ViewConnection view = new ViewConnection();
			view.setPresenter(new PresenterConnection(view, new ModelConnection(), new Client()));	

	}
}
