import gui.model.ModelConnection;
import gui.presenter.PresenterConnection;
import gui.view.ViewConnection;
import model.client.Client;

public class LaunchClientGUI {
	public static void main(String[] args) {

		ViewConnection view = new ViewConnection();
		PresenterConnection presenterConnection = new PresenterConnection(view, new ModelConnection(), new Client());
		view.setPresenter(presenterConnection);
		/**
		 * if(presenterConnection.isLogIn()) { ViewConversation viewConversation = new
		 * ViewConversation(); PresenterConversation presenterConversation = new
		 * PresenterConversation(viewConversation, new
		 * ModelConversation(presenterConnection.getClient().getUsername(),
		 * presenterConnection.getClient().getIdUser()),presenterConnection.getClient());
		 * viewConversation.setPresenter(presenterConversation); }
		 **/

	}
}
