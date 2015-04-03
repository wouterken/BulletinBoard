package actions;
import java.util.Calendar;
import model.BulletinBoardIO;
import configuration.StringConstants;
import controllers.ClientState;
import exceptions.*;

/**
 * A Post action attempts to create a post from the current user
 * to the currently selected message board.
 * @author wouterken
 *
 */
public class PostAction extends Action {

	private String message = "";

	public PostAction(String... args) throws IncorrectNumberOfArguments {
		super(MANY, args);
		this.message = stringFromArray(args);
	}

	/**
	 * Attempts to create a post from the current user
	 * to the currently selected message board.
	 * Checks that the user is logged in, and has a topic set.
	 * If the post is successful, it broadcasts the change in state
	 * to all logged-in users.
	 */
	@Override
	public String perform(){
		try {
			BulletinBoardIO.postMessage(ClientState.instance().getUserID(),
					ClientState.instance().getTopicID(), message);
		} catch (UserNotLoggedInException e) {
			return e.getMessage();
		} catch (CommandNonExistant e) {
			return e.getMessage();
		} catch (Exception e) {
			return e.getMessage();
		}
		/**
		 * Broadcast the update in state to all logged-in users.
		 */
		this.broadcast = String.format(StringConstants.MESSAGE_POST_UPDATE,
				Calendar.getInstance().getTime().toString(), ClientState
						.instance().getName(), message, ClientState.instance()
						.topicName());
		return BulletinBoardIO.messagesAsString(ClientState.instance()
				.getTopicID(), false)
				+ StringConstants.MESSAGE_POST_SUCCESS;
	}

}
