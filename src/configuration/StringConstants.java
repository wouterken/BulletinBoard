package configuration;

/**
 * A list of all user-messages in an easy to manage/change location.
 * @author wouterken
 *
 */
public class StringConstants {

	public static final String LOGIN_REQUIRED_MESSAGE = "You must be logged in to start a topic";
	public static final String TOPIC_DOESNT_EXIST = "Topic '%s' doest not exist";
	public static final String TOPIC_ALREADY_EXISTS =  "Topic '%s' already exists, setting to active.";
	public static final String LOGIN_TO_REMOVE_TOPIC = "You must be logged in to remove a topic";
	public static final String LOGIN_TO_REMOVE_MESSAGE = "You must be logged in to remove a message";
	public static final String SET_TOPIC_TO_REMOVE = "You need to select a topic before you can remove messages";
	public static final String TOPIC_REMOVED = "Topic '%s' has been deleted";
	public static final String TOPIC_CREATED = "Started topic '%s' successfully.";
	public static final String NO_TOPIC_SET = "Please set a valid topic with the '.set' command before\nusing the .read command";
	public static final String MESSAGE_POST_SUCCESS = "\n'Message posted successfully'";
	public static final String MESSAGE_POST_UPDATE = "\n\n UPDATE (%s) User %s just posted '%s' on the topic '%s' ";
	public static final String MESSAGE_REMOVED = "\n'Message has been removed";
	public static final String ACTIVE_TOPIC_SET = "%s\nActive topic has been set to '%s' ";
	public static final String SET_USAGE_INFO = "\n\n --> \"Use .set [topicname] or .set [topicID] to set the active topic\"\n";
	public static final String NOTHING_TO_REMOVE = "No message to remove at that index";
	public static final String CANT_REMOVE_MSG_FROM_OTHER_USERS = "You can't remove messages by another user (Login as the correct user or admin)";

	public static final String USER_ADDED = "User '%s' added successfully.";
	public static final String CANT_DUPLICATE_ADMIN = "Cannot have more than one admin user";
	public static final String ONLY_ADMIN_CAN_CREATE_USERS = "You must be logged in as 'admin' (password is 'admin') to create new users";
	public static final String ADMIN_LOGGED_IN_MESSAGE = "Admin has just logged in.. Everyone on your best behavior!";
	public static final String CREATED_NEW_TOPIC = "%s has just started the new topic: '%s'. Type .set '%s' to join.";
	public static final String NEW_USER_ADDED = "%s has just joined the board.";
	
	public static final String INVALID_CREDENTIALS_MESSAGE = "The given credentials are invalid.";
	public static final String LOGGED_OUT_MESSAGE = "Successfully logged out";
	public static final String LOGGED_IN_MESSAGE = "Successfully logged in as '%s'.\nType (.commands to see a list of available commands)";
	public static final String ALREAD_LOGGED_IN_MESSAGE = "Already logged in as '%s'";
	public static final String NOT_LOGGED_IN_MESSAGE = "Not logged in";
	public static final String LOG_IN_FAILED_MESSAGE = "Log-in failed";
	public static final String LOGGED_OUT_BROADCAST = "User :%s has logged out.";
	public static final String USER_LOGGED_IN_MESSAGE = "User %s has just logged in";
			
	public static final String MESSAGE = "message";
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String TOPIC = "topic";
	public static final String LOGGED_IN_USERS = "\nCurrently Logged in Users: ";
	public static final String ADMIN_USER = "admin";
	public static final String SERVER_LISTENING = "Server is listening on %s";
	public static final String NEW_CLIENT_CONNECTION = "New Client Connection";
	public static final String ONLY_ADMIN_REMOVE_TOPIC = "Only 'admin' can remove topics that have been posted in (try login as admin, password admin)";
	
	public static final String DATA_FILE = "bbs.xml";

}
