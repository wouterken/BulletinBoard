package actions;
import exceptions.IncorrectNumberOfArguments;

/**
 * A Commands action simply provides all available commands
 * to the user.
 * @author wouterken
 *
 */
public class CommandsAction extends Action {

	public CommandsAction(String... args) throws IncorrectNumberOfArguments {
		super(0, args);
	}

	@Override
	/**
	 * Lists all possible commands.
	 */
	public String perform() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n===================\n");
		sb.append("COMMANDS\n");
		sb.append("===================\n");
		sb.append("\n.login [username] [password]");
		sb.append("\n.logout ");
		sb.append("\n.post [message to post]");
		sb.append("\n.read");
		sb.append("\n.create [topic name (allows whitespace)]");
		sb.append("\n.set [topic name | topic ID]");
		sb.append("\n.topics");
		sb.append("\n.delete-topic [topic name | topic ID]");
		sb.append("\n.delete-message [message ID]");
		sb.append("\n.commands");
		sb.append("\n.users");
		sb.append("\n.adduser [username] [password] (admin only)");

		return sb.toString();

	}

}
