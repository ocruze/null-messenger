package util;

public class Constants {

	/**
	 * Clés
	 */

	// Spécificités sur le message
//	public final static String KEY_CODE = "code"; // 0 = 
	public final static String KEY_MESSAGE = "message";
	public final static String KEY_CLIENT_ACTION = "clientAction";
	public final static String KEY_SERVER_RESPONSE = "serverResponse";

	// Autres infos
	public final static String KEY_ID_USER = "idUser";
	public final static String KEY_USERNAME = "username";
	public final static String KEY_PASSWORD = "password";

	public final static String KEY_ID_CONVERSATION = "idConversation";
	public final static String KEY_USERNAME_SENDER = "usernameSender";
	public final static String KEY_USERNAME_RECIPIENT = "usernameRecipient";
	public final static String KEY_MESSAGE_CONTENT = "messageContent";

	/**
	 * Valeurs
	 */

	// Statuts ou type d'erreur
	public final static String VALUE_MESSAGE_OK = "ok";
	public final static String VALUE_ERROR_USER_NOT_FOUND = "userNotFound";
	public final static String VALUE_ERROR_USER_ALREADY_EXIST = "userAlreadyExist";
	public final static String VALUE_ERROR_PASSWORD_INCORRECT = "passwordIncorrect";
	public final static String VALUE_ERROR_INTERNAL = "internalServerError";

	public final static String VALUE_DELETED_MESSAGE = "Message supprimé";

	// Actions
	public final static String VALUE_ACTION_LOGIN = "login";
	public final static String VALUE_ACTION_REGISTER = "register";
	public final static String VALUE_ACTION_DISCONNECT = "disconnect";
	public final static String VALUE_ACTION_SEND_MESSAGE = "sendMessage";
	public final static String VALUE_ACTION_RECEIVE_MESSAGE = "receiveMessage";
	public final static String VALUE_ACTION_GET_LIST_CONVERSATION = "getListConversation";
	public final static String VALUE_ACTION_GET_CONVERSATION_MESSAGES = "getConversationMessages";

}