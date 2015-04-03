package controllers;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import model.BulletinBoardIO;
import configuration.StringConstants;

/**
 * This class maintains the state of the current user
 * @author wouterken
 *
 */
public class ClientState {

	/**
	 * Maps clients to threads so we can see everyone who is connected,
	 * and also know which client state is ours.
	 */
	public static Map<Thread, ClientState> activeClients = new HashMap<Thread, ClientState>();

	public static final String ANONYMOUS = "Anonymous";
	public static final String NO_TOPIC = "None";
	public static final String NO_ID = "-1";

	private boolean loggedIn = false;
	private String name = ANONYMOUS;
	private String activeTopicID = "";
	private String userID = NO_ID;
	private String topicName = NO_TOPIC;

	/**
	 * Returns our client state object.
	 * @return
	 */
	public static ClientState instance(){
		if(!activeClients.containsKey(Thread.currentThread())){
			activeClients.put(Thread.currentThread(), new ClientState());
		}
		return activeClients.get(Thread.currentThread());
	}

	/**
	 * Returns login status.
	 * @return
	 */
	public boolean isLoggedIn(){
		return loggedIn;
	}

	/**
	 * Sets login details for the current state.
	 * @param name
	 * @param userID
	 */
	public void logIn(String name, String userID) {
		this.name = name;
		this.userID = userID;
		loggedIn = true;
	}

	/**
	 * Logs out user.
	 */
	public void logOut() {
		this.name = ANONYMOUS;
		this.topicName = NO_TOPIC;
		this.activeTopicID = "";
		this.userID = NO_ID;
		loggedIn = false;
	}

	/**
	 * Removes this state from collection of active clients.
	 */
	public static void removeSelf() {
		activeClients.remove(Thread.currentThread());
	}

	/**
	 * GETTERS AND SETTERS
	 *
	 */

	public void setTopic(String topicId){
		this.activeTopicID = topicId;
		try{
			Element activeTopic = BulletinBoardIO.topicById(topicId);
			
			/** Shouldn't have to explicitly throw this exception but not checking for null ourselves causes a segfault on the uni ecs systems **/
			if(activeTopic == null) throw new NullPointerException();
			
			this.topicName = BulletinBoardIO.topicById(topicId).getAttribute("name");
		}catch (Exception e) {
			this.topicName = NO_TOPIC;
			this.activeTopicID = "";
		}
	}
	
	public void checkTopicExists(){
		Element activeTopic = BulletinBoardIO.topicByName(topicName);
		if(activeTopic == null)
			setTopic("");
	}
	
	public Object topicName() {
		checkTopicExists();
		return topicName;
	}
	public String getUserID(){
		return userID;
	}

	public String getTopicID(){
		return activeTopicID;
	}

	public String getName() {
		return name;
	}

	public String getOtherUsers() {
		String userList = StringConstants.LOGGED_IN_USERS;
		for(ClientState user: activeClients.values()){
				userList += "\n-"+user.name;
		}
		return userList;
	}



}
