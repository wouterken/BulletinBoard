package actions;
import controllers.ClientState;
import exceptions.IncorrectNumberOfArguments;

/**
 * A ListUsersAction returns a list of all currently logged in users.
 * @author wouterken
 *
 */
public class ListUsersAction extends Action {

	public ListUsersAction(String... args) throws IncorrectNumberOfArguments {
		super(0, args);
	}
	
	@Override
	public String perform() {
		return ClientState.instance().getOtherUsers();
	}

}
