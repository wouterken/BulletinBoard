package exceptions;

/**
 * Exception for handling commands that are not passed the correct number of arguments.
 * @author wouterken
 *
 */
public class IncorrectNumberOfArguments extends Exception {

	private static final long serialVersionUID = 1L;
	private int argsGiven;
	private int argsRequired;
	private String command;

	public IncorrectNumberOfArguments(String command, int argsGiven, int argsRequired) {
		this.command = command;
		this.argsGiven = argsGiven;
		this.argsRequired = argsRequired;
	}
	public String getMessage(){
		return String.format("The command '%s' requires '%d' arguments. '%d' were given", this.command, this.argsRequired, this.argsGiven);
	}

}
