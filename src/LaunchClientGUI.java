import gui.model.ModelConnection;
import gui.model.ModelConversation;
import gui.presenter.IPresenter;
import gui.presenter.PresenterConnection;
import gui.presenter.PresenterConversation;
import gui.view.ViewConnection;
import gui.view.ViewConversation;
import model.client.Client;

public class LaunchClientGUI {

	public static void main(String[] args) {
		ViewConnection view = new ViewConnection();
		PresenterConnection presenterConnection = new PresenterConnection(view, new ModelConnection(), new Client());
		LaunchClientGUI launch = new LaunchClientGUI(presenterConnection);
		view.setPresenter(presenterConnection);
		launch.launch(presenterConnection);
	}

	private PresenterConnection presenterConnection;
	private PresenterConversation presenterConversation;

	public LaunchClientGUI() {
		this.presenterConnection = null;
		this.presenterConversation = null;
	}

	public LaunchClientGUI(PresenterConnection presenter) {
		this.presenterConnection = presenter;
		this.presenterConversation = null;
	}

	public void launch(IPresenter presenter) {
		presenter.setChangeWindow((window) -> changeWindow(window));
	}

	public void changeWindow(gui.presenter.IPresenter.Window window) {
		switch (window) {
		/**
		 * case Connection: ViewConnection view = new ViewConnection();
		 * presenterConnection = new PresenterConnection(view, new ModelConnection(),
		 * new Client()); view.setPresenter(presenterConnection); break;
		 **/
		case Conversation:
			ViewConversation viewConversation = new ViewConversation();
			PresenterConversation presenterConversation = new PresenterConversation(viewConversation,
					new ModelConversation(presenterConnection.getClient().getUsername(),
							presenterConnection.getClient().getIdUser()),
					presenterConnection.getClient());
			viewConversation.setPresenter(presenterConversation);
			setPresenterConversation(presenterConversation);
			break;
		default:
			break;
		}
	}

	public void setPresenterConversation(PresenterConversation presenter) {
		this.presenterConversation = presenter;
	}

}
