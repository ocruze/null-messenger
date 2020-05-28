public class LaunchClientGUI {
	public static void main(String[] args) {
		ViewConnection view = new ViewConnection();
		view.setPresenter(new PresenterConnection(view, new ModelConnection()));
	}
}
