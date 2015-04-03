package actions;
import exceptions.IncorrectNumberOfArguments;


/**
 * A Bullet in board action.
 * @author wouterken
 *
 */
public abstract class Action {
	
	protected static int MANY = -1;
	protected String broadcast = "";
	
	/**
	 * The constructor ensures the number of arguments to the action are correct. 
	 * @param argsRequired
	 * @param arguments
	 * @throws IncorrectNumberOfArguments
	 */
	public Action(int argsRequired, String... arguments) throws IncorrectNumberOfArguments{
		if(arguments.length != argsRequired && argsRequired != MANY){
			throw new IncorrectNumberOfArguments(this.getClass().getName(), arguments.length, argsRequired);
		}
	}
	
	/**
	 * An action can broadcast a message, to all clients.
	 * @return
	 */
	public String broadcast(){
		return this.broadcast;
	}
	
	/**
	 * When an action is performed it modifies current state
	 * and returns a status string.
	 * @return
	 */
	public abstract String perform() throws Exception;
	
	/**
	 * Turns an array of strings into a single string.
	 * Useful to turn a sequence of strings into a single argument.
	 * @param args
	 * @return
	 */
	protected String stringFromArray(String[] args) {
		StringBuilder sb = new StringBuilder();
		for (String s : args)
		{
		    sb.append(s);
		    sb.append(" ");
		}
		return sb.toString().trim();
	}
}
