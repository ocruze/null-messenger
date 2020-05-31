package exceptions;

public class UserAlreadyRegisteredException extends Exception {

	private static final long serialVersionUID = 7239349709823160282L;

	public UserAlreadyRegisteredException() {
		super("Utilisateur déjà inscrit");
	}
}
