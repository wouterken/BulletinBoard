package actions;
import configuration.StringConstants;
import controllers.*;
import exceptions.IncorrectNumberOfArguments;

/**
 * A logout action logs out a currently logged in user.
 * @author wouterken
 *
 */
public class LogoutAction extends Action {

	public LogoutAction(String... args) throws IncorrectNumberOfArguments {
		super(0, args);
	}
	/**
	 * Attempts to log out user, warns user if they weren't logged in.
	 */
	@Override
	public String perform(){
		String name = ClientState.instance().getName();
		String status = LoginController.logOut();
		if(status.equals(StringConstants.LOGGED_OUT_MESSAGE)){
			this.broadcast = String.format(StringConstants.LOGGED_OUT_BROADCAST, name);
		}
		return status;
	}

}
