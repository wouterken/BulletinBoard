package exceptions;
import configuration.StringConstants;

/**
 * Exception to be thrown when logged in user attempts to log in again.
 */
public class AlreadyLoggedInException extends Exception {
	private static final long serialVersionUID = 1L;
	private String name = "";

	public AlreadyLoggedInException(String name){
		this.name = name;
	}

	@Override
	public String getMessage() {
		return String.format(StringConstants.ALREAD_LOGGED_IN_MESSAGE, name);
	}
}
