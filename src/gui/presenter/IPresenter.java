package gui.presenter;

import java.util.function.Consumer;

import org.json.JSONObject;

public interface IPresenter {
	public enum Window {
		Connection,
		Conversation
	}
	void changeWindow(Window window);
	void setChangeWindow(Consumer<Window> window);

}
