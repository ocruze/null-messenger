package window.presenter;

import window.model.IModel;
import window.model.ModelConnection;
import window.view.ViewConnection;

public class PresenterConnection implements IPresenter {

	public ViewConnection view;
	public ModelConnection model;
	
	public PresenterConnection(ViewConnection view, ModelConnection model)	 {
		this.view = view;
		this.model = model;
	}
	
	public void login() {
		
		System.out.println("Login :" +model.getUsername());
		
	}
	
	public void setPassword(String password) {
		model.setPassword(password);
	}
	public void setUsername(String username) {
		model.setUsername(username);
	}
	public void setPort(int port) {
		model.setPort(port);
	}
	public void setIp(String ip) {
		model.setIp(ip);
	}
}
