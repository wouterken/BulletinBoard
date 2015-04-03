package actions;
import model.BulletinBoardIO;
import configuration.StringConstants;
import controllers.ClientState;
import exceptions.IncorrectNumberOfArguments;

/**
 * An AddUserAction adds a new set of user credentials to the bulletin board.
 * This user can then log-in, post, and create topics.
 */
public class AddUserAction extends Action {

	private String username;
	private String password;

	/**
	 * Takes a username and a password as an argument.
	 * @param args
	 * @throws IncorrectNumberOfArguments
	 */
	public AddUserAction(String... args) throws IncorrectNumberOfArguments {
		super(2, args);
		this.username = args[0];
		this.password = args[1];
	}

	/**
	 * Attempts to create a new user.
	 * Fails if user tries to modify admin account.
	 * Duplicate usernames are allowed. (Assumes different passwords).
	 */
	@Override
	public String perform() {
		if(ClientState.instance().getName().equals(StringConstants.ADMIN_USER)){
			if(this.username.equals(StringConstants.ADMIN_USER)){
				return StringConstants.CANT_DUPLICATE_ADMIN;
			}
			try {
				String newUser =  BulletinBoardIO.addUser(username, password);
				this.broadcast = String.format(StringConstants.NEW_USER_ADDED, username);
				return newUser;
			} catch (Exception e) {
				return e.getMessage();
			}
		}else{
			return StringConstants.ONLY_ADMIN_CAN_CREATE_USERS;
		}
	}

}
