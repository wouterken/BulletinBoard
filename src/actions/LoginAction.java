package actions;
import java.security.NoSuchAlgorithmException;
import configuration.StringConstants;
import controllers.LoginController;
import exceptions.*;

/**
 * A LoginAction attempts to login with a username, password combo
 * @author wouterken
 *
 */
public class LoginAction extends Action {

	private String name;
	private String password;

	/**
	 * Takes params [name, password]
	 * @param args
	 * @throws IncorrectNumberOfArguments
	 */
	public LoginAction(String... args) throws IncorrectNumberOfArguments {
		super(2, args);
		this.name = args[0];
		this.password = args[1];
	}
	/**
	 * Attempt login
	 * @throws Exception
	 */
	@Override
	public String perform() throws Exception{
		try {
			if(name.equals(StringConstants.ADMIN_USER)){
				this.broadcast = StringConstants.ADMIN_LOGGED_IN_MESSAGE;
				return LoginController.attemptAdminLogin(password);
			}
			this.broadcast = String.format(StringConstants.USER_LOGGED_IN_MESSAGE, name);
			return LoginController.attemptLogin(name, password);
		} catch (Exception e) {
			this.broadcast = "";
			if(e instanceof NoSuchAlgorithmException || e instanceof AlreadyLoggedInException)
				return e.getMessage();
			throw e;
		}
	}

}
