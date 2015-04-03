package actions;
import java.util.ArrayList;
import model.*;
import configuration.StringConstants;
import exceptions.IncorrectNumberOfArguments;

/**
 * A TopicsAction returns a list of all topics on the Bulletin board.
 * @author wouterken
 *
 */
public class TopicsAction extends Action {

	public TopicsAction(String... args) throws IncorrectNumberOfArguments {
		super(0, args);
	}

	/**
	 * requests a list of all topics on the Bulletin board.
	 * and returns them in a formatted string.
	 */
	@Override
	public String perform(){
		String list = "";
		try {
			ArrayList<Topic> topicList = BulletinBoardIO.getTopicList();
			for (Topic t : topicList) {
				list += "\n" + t;
			}
		} catch (Exception e) {
		}
		list += StringConstants.SET_USAGE_INFO;
		return list;
	}

}
