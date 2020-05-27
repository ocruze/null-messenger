package window;

import window.model.ModelConnection;
import window.presenter.PresenterConnection;
import window.view.ViewConnection;

public class LaunchClient {

	public static void main(String[] args) {
		ViewConnection view = new ViewConnection();
		view.setPresenter(new PresenterConnection(view, new ModelConnection()));
	}

}
