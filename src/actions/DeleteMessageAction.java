package actions;
import model.BulletinBoardIO;
import controllers.ClientState;
import exceptions.*;

/**
 * A DeleteMessage action deletes a message by index..
 * @author wouterken
 *
 */
public class DeleteMessageAction extends Action {

	private int messageIdx;

	/**
	 * The constructor ensures the index parameter
	 * is a valid integer.
	 * @param args
	 * @throws IncorrectNumberOfArguments
	 * @throws IncorrectArgumentType
	 */
	public DeleteMessageAction(String... args) throws IncorrectNumberOfArguments, IncorrectArgumentType {
		super(1, args);
		try{
			this.messageIdx = Integer.parseInt(args[0]) - 1;
		}catch (Exception e) {
			throw new IncorrectArgumentType(String.class.getSimpleName(), Integer.class.getSimpleName());
		}
	}

	/**
	 * Attempts to delete the message at idx from the current topic.
	 */
	@Override
	public String perform(){
		return BulletinBoardIO.deleteMessage(messageIdx, ClientState.instance().getTopicID());
	}

}
