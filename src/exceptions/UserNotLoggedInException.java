package exceptions;

/**
 * Exception for handling attempts at operations that require authentication, where
 * the user is not authenticated.
 * @author wouterken
 *
 */
public class UserNotLoggedInException extends Exception {

	private static final long serialVersionUID = 1L;

	public String getMessage(){
		return String.format("You need to log-in before you can post");
	}
}
