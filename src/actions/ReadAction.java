package actions;
import model.BulletinBoardIO;
import controllers.ClientState;
import exceptions.IncorrectNumberOfArguments;

/**
 * A read action returns a complete list of all posts in a topic.
 * @author wouterken
 *
 */
public class ReadAction extends Action {

	public ReadAction(String... args) throws IncorrectNumberOfArguments {
		super(0, args);
	}

	/**
	 * returns a complete list of all posts in a topic.
	 */
	@Override
	public String perform(){
		return BulletinBoardIO.messagesAsString(ClientState.instance()
				.getTopicID(), true);
	}

}
