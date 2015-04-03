package actions;
import model.BulletinBoardIO;
import configuration.StringConstants;
import controllers.ClientState;
import exceptions.*;

/**
 * A Create action creates a new topic.
 * @author wouterken
 *
 */
public class CreateAction extends Action {

	private String newTopicName;

	/**
	 * Takes a single argument, the name of the new topic
	 * @param args
	 * @throws IncorrectNumberOfArguments
	 */
	public CreateAction(String... args) throws IncorrectNumberOfArguments {
		super(MANY, args);
		newTopicName = stringFromArray(args);
	}

	/**
	 * Attempts to create the topic, fails if the user is not logged in
	 * or the topic exists.
	 * The currently active topic is set to the new topic
	 */
	@Override
	public String perform(){
		try {
			String topicCreated = BulletinBoardIO.startTopic(newTopicName.toLowerCase());
			this.broadcast = String.format(StringConstants.CREATED_NEW_TOPIC, ClientState.instance().getName(), newTopicName, newTopicName);
			return topicCreated;
		} catch (UserNotLoggedInException e) {
			return StringConstants.LOGIN_REQUIRED_MESSAGE;
		}catch (Exception e) {
			return e.getMessage();
		}
	}
}
