package exceptions;

/**
 * Exception for handling improperly formatted or non-existant commands.
 * @author wouterken
 *
 */
public class CommandNonExistant extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String command;

	public CommandNonExistant(String command){
		this.command = command;
	}
	
	public String getMessage(){
		return String.format("The command '%s' is invalid. Type '.commands' for a list of available commands", command);
	}
}
