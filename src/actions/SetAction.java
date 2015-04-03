package actions;
import model.BulletinBoardIO;
import org.w3c.dom.Element;
import configuration.StringConstants;
import controllers.ClientState;
import exceptions.IncorrectNumberOfArguments;

/**
 * A set action sets the currently selected topic.
 * Topic names can be strings of any length
 * @author wouterken
 *
 */
public class SetAction extends Action {

	private String idOrName;

	public SetAction(String... args) throws IncorrectNumberOfArguments {
		super(-1, args);
		idOrName = stringFromArray(args).toLowerCase();
	}

	@Override
	/**
	 * sets the currently selected topic.
	 * Topic names can be strings of any length.
	 * If the topic already exists it returns it.
	 * Prints a sub-list of messages in this topic.
	 */
	public String perform(){
		Element topic = null;
		topic = BulletinBoardIO.topicById(idOrName);
		if (topic == null) {
			topic = BulletinBoardIO.topicByName(idOrName);
		}
		if (topic == null) {
			return String.format(StringConstants.TOPIC_DOESNT_EXIST, this.idOrName);
		}
		ClientState.instance().setTopic(topic.getAttribute("id"));
		return String.format(StringConstants.ACTIVE_TOPIC_SET,
				BulletinBoardIO.messagesAsString(ClientState.instance().getTopicID(), false), topic.getAttribute("name"));
	}

}
