package exceptions;
import configuration.StringConstants;

public class InvalidUserException extends Exception {
	
	private static final long serialVersionUID = 1L;
	@Override
	public String getMessage() {
		return StringConstants.INVALID_CREDENTIALS_MESSAGE;
	}
}
