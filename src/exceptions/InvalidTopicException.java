package exceptions;

/**
 * Exception for handling operations performed on null or invalid topics.
 * @author wouterken
 *
 */
public class InvalidTopicException extends Exception {

	private static final long serialVersionUID = 1L;

	public String getMessage(){
		return String.format("Please select a valid topic first\nUse the '.set' command to set a topic\nuse the '.topics' command to see all available topics");
	}
}
