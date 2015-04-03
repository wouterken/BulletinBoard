package controllers;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import actions.*;
import exceptions.*;

/**
 * A generator for actions depending on the input command.
 * @author wouterken
 *
 */
public class Actionfactory {

	static final Class<?>[] args = {String[].class};
	
	/**
	 * A static map, that maps commands, to the appropriate actions.
	 */
	static Map<String, Class<?>> actions = Collections.unmodifiableMap(new HashMap<String, Class<?>>() {
		private static final long serialVersionUID = 1L;
	{
        put(".login", LoginAction.class);
        put(".logout", LogoutAction.class);
        put(".post", PostAction.class);
        put(".read", ReadAction.class);
        put(".create", CreateAction.class);
        put(".set", SetAction.class);
        put(".topics", TopicsAction.class);
        put(".delete-topic", DeleteTopicAction.class);
        put(".delete-message", DeleteMessageAction.class);
        put(".commands", CommandsAction.class);
        put(".adduser", AddUserAction.class);
        put(".help", CommandsAction.class);
        put(".users", ListUsersAction.class);
    }});

	/**
	 * Creates a new action of the appropriate type, or throws an error.
	 * Then attempts to use the rest of the request to pass
	 * the correct number of variable arguments to the constructor of our mapped action.
	 * @param request
	 * @return
	 * @throws IncorrectNumberOfArguments
	 * @throws CommandNonExistant
	 * @throws Exception
	 */
	public static Action createAction(String request) throws IncorrectNumberOfArguments, CommandNonExistant, Exception {
		
		String[] tokens = request.split(" ");
		
		Class<?> actionClass = (tokens.length != 0)?actions.get(tokens[0]):null;
		
		if(actionClass == null)
			throw new CommandNonExistant(tokens[0]);
		
		else
			return (Action) actionClass.getConstructor(args).newInstance(new Object[]{Arrays.copyOfRange(tokens, 1, tokens.length)});
		
	}

}
