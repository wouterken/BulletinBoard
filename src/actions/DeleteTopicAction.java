package actions;
import model.BulletinBoardIO;
import org.w3c.dom.Element;
import configuration.StringConstants;
import controllers.ClientState;
import exceptions.*;


/**
 * A Delete Topic action removes a topic from the bulletin board.
 * @author wouterken
 *
 */
public class DeleteTopicAction extends Action {

	private String idOrName;

	/**
	 * The constructor stores a single argument as a possible ID or name
	 * @param args
	 * @throws IncorrectNumberOfArguments
	 */
	public DeleteTopicAction(String... args) throws IncorrectNumberOfArguments {
		super(-1, args);
		idOrName = stringFromArray(args);
	}

	@Override
	/**
	 * Attempts to delete a topic by ID,
	 * if it can't be found attempts to delete it by name,
	 * otherwise we conclude it doesn't exist.
	 * If the user isn't logged in the operation fails.
	 */
	public String perform(){
		Element topic = null;
		topic = BulletinBoardIO.topicById(idOrName);
		if(topic == null){
			topic = BulletinBoardIO.topicByName(idOrName);
		}if(topic == null){
			return String.format(StringConstants.TOPIC_DOESNT_EXIST, this.idOrName);
		}
		try {
			BulletinBoardIO.deleteTopic(topic.getAttribute("id"), ClientState.instance().getUserID());
		} catch (Exception e) {
			return StringConstants.ONLY_ADMIN_REMOVE_TOPIC;
		}
		if(ClientState.instance().getTopicID().equals(topic.getAttribute("id"))){
			ClientState.instance().setTopic("");
		}
		return String.format(StringConstants.TOPIC_REMOVED, topic.getAttribute("name"));
	}
}
