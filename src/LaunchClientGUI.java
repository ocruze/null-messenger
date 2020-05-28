package window;

import window.model.ModelConnection;
import window.presenter.PresenterConnection;
import window.view.ViewConnection;

public class LaunchClientGUI {

	public static void launch() {
		ViewConnection view = new ViewConnection();
		view.setPresenter(new PresenterConnection(view, new ModelConnection()));
	}

}
