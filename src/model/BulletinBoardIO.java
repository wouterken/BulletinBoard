package model;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import configuration.StringConstants;
import controllers.ClientState;
import exceptions.InvalidTopicException;
import exceptions.UserNotLoggedInException;

public class BulletinBoardIO extends XMLIO {

	/**
	 * Constants for the admin user to ensure it is never accidentally deleted from the source XML.
	 */
	private static String ADMIN_ID = "0";
	private static String ADMIN_HASH = "44047210810420107506624974438055026627";
	
	/**
	 * Attempts to add a new user to the model.
	 * @param name
	 * @param password
	 * @return
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
    public static synchronized String addUser(String name, String password)
            throws TransformerFactoryConfigurationError, TransformerException,
            IOException, NoSuchAlgorithmException {

        Element newUser = boards.createElement(StringConstants.USER);
        
        setKeyValues(newUser, new String[] { StringConstants.NAME, StringConstants.PASSWORD, StringConstants.ID },new String[] { name, MD5(password), uniqueID() });
        
        boards.getElementsByTagName("users").item(0).appendChild(newUser);
        
        saveBoards();
        
        return String.format(StringConstants.USER_ADDED, name);
    }
    
    /**
     * Attempts to verify login credentials, returns ID of user if successfull, or an
     * empty string upon failure.
     * @param name
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static synchronized String attemptLogin(String name, String password) throws NoSuchAlgorithmException {
    	password = MD5(password);
    	
        NodeList users = boards.getElementsByTagName(StringConstants.USER);
        
        for (int idx = 0; idx < users.getLength(); idx++) {
        	
            Element user = (Element) users.item(idx);
            
            if (user.getAttribute(StringConstants.NAME).equals(name) && user.getAttribute(StringConstants.PASSWORD).equals(password)) 
                return user.getAttribute(StringConstants.ID);
            
        }
        return "";
    }
    
    /**
     * Attempts to login the admin user. Validates against constant username and password (both 'admin');
     * @param password
     * @return
     */
	public static String attemptAdminLogin(String password) {
		try {
			password = MD5(password);
			
			if(password.equals(ADMIN_HASH))
				return ADMIN_ID;
			
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
		return "";
	}
	
	/**
	 * Returns a username for a given user id.
	 * @param id
	 * @return
	 */
    public static String getUserName(String id) {
    	
    	if(id.equals(ADMIN_ID))
    		return StringConstants.ADMIN_USER;
    	
        NodeList users = boards.getElementsByTagName(StringConstants.USER);
        
        for (int idx = 0; idx < users.getLength(); idx++) {
        	
            Element user = (Element) users.item(idx);
            
            if (user.getAttribute(StringConstants.ID).equals(id)) 
                return user.getAttribute(StringConstants.NAME).toString();
            
        }
        return "";
    }
    
    /**
     * Adds a message to the model,
     * accepts the id of the user making the post,
     * the topic to post on
     * and the message contents.
     * Validates the topic is valid, and the user is logged in first.
     * @param userid
     * @param topicId
     * @param messageText
     * @throws Exception
     */
    public static synchronized void postMessage(String userid, String topicId, String messageText) throws Exception {

        Element topic = topicById(topicId);
        
        if (topic == null) 
            throw new InvalidTopicException();
        
        if (userid.equals("-1")) 
            throw new UserNotLoggedInException();
        
        Element message = boards.createElement(StringConstants.MESSAGE);
        
        setKeyValues(message, new String[] { StringConstants.USER, StringConstants.ID }, new String[] {userid, uniqueID() });
        
        message.setTextContent(messageText);
        
        topic.appendChild(message);
        
        saveBoards();

    }
    
    /**
     * Retrieves a topic by  name.
     * @param name
     * @return
     */
    public static Element topicByName(String name) {

        NodeList topics = boards.getElementsByTagName(StringConstants.TOPIC);
        
        for (int idx = 0; idx < topics.getLength(); idx++) {
            
        	Element topic = (Element) topics.item(idx);
            
            if (topic.getAttribute(StringConstants.NAME).equals(name)) 
                return topic;
            
        }
        return null;
    }

    /**
     * Retrieves a topic by ID.
     * @param id
     * @return
     */
    public static Element topicById(String id) {

        NodeList topics = boards.getElementsByTagName(StringConstants.TOPIC);
        
        for (int idx = 0; idx < topics.getLength(); idx++) {
        	
            Element topic = (Element) topics.item(idx);
            
            if (topic.getAttribute(StringConstants.ID).equals(id)) 
                return topic;
            
        }
        return null;
    }

    /**
     * Adds a new topic to the model if it doesn't already exist,
     * returns error messages if user isn't logged in, or if topic already
     * exists.
     * @param newTopic
     * @return
     * @throws Exception
     * @throws UserNotLoggedInException
     */
    public static synchronized String startTopic(String newTopic) throws Exception, UserNotLoggedInException {
    	
        String topicID = uniqueID();
        
        try {
        	Element activeTopic = topicByName(newTopic);
        	
        	/** Shouldn't have to explicitly throw this exception but not checking for null ourselves causes a segfault on the uni ecs systems **/
        	if(activeTopic == null) throw new NullPointerException();
        	
        	topicID = activeTopic.getAttribute(StringConstants.ID);
            ClientState.instance().setTopic(topicID);
            
            return String.format( StringConstants.TOPIC_ALREADY_EXISTS, newTopic);
        }
        catch (Exception e) {
        	
            if (ClientState.instance().getUserID().equals("-1"))
                throw new UserNotLoggedInException();

            Element topic = boards.createElement(StringConstants.TOPIC);
            
            setKeyValues(topic, new String[] { StringConstants.NAME, StringConstants.ID }, new String[] {newTopic, topicID });
            
            boards.getElementsByTagName("topics").item(0).appendChild(topic);
            
            saveBoards();
        }
        ClientState.instance().setTopic(topicID);
        
        return String.format(StringConstants.TOPIC_CREATED, newTopic);
    }
    
    /**
     * Returns an array list of all messages from any given topic ID.
     * @param topicId
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static ArrayList<Message> getMessageList(String topicId)
            throws ParserConfigurationException, SAXException, IOException {

        Element topic = topicById(topicId);
        
        if (topic != null) {
            ArrayList<Message> messages = new ArrayList<Message>();
            
            NodeList messagesList = topic.getElementsByTagName(StringConstants.MESSAGE);
            
            for (int idx = 0; idx < messagesList.getLength(); idx++) {
                Element message = (Element) messagesList.item(idx);
                messages.add(new Message(message));
            }
            
            return messages;
        }
        return null;
    }

    /**
     * Returns a list of messages for a given topic as formatted text.
     * if overrideSizeLimit is true, it shows all messages in the topic,
     * otherwise it limits the output to the latest 5 messages. 
     * @param topicID
     * @param overrideSizeLimit
     * @return
     */
    public static String messagesAsString(String topicID,
            boolean overrideSizeLimit) {
        ArrayList<Message> messages = null;
        
        try {
            messages = getMessageList(topicID);
            if (messages == null) throw new Exception();
        } catch (Exception e) {
            return StringConstants.NO_TOPIC_SET;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n================\nTOPIC: '");
        sb.append(topicById(topicID).getAttribute(StringConstants.NAME));
        sb.append("'\n================\n");
       
        int idx = (messages.size() > 5 && !overrideSizeLimit) ? messages.size() - 5 : 1;
        
        for (Message s : messages.subList(idx - 1, messages.size())) {
            sb.append(idx++);
            sb.append(") ");
            sb.append(s.toString());
            sb.append("\n");
        }
        
        return sb.toString();
    }

    /**
     * Returns a list of all topics on the Bulletin Board.
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static ArrayList<Topic> getTopicList()
            throws ParserConfigurationException, SAXException, IOException {

        ArrayList<Topic> topics = new ArrayList<Topic>();
        
        NodeList topicList = boards.getElementsByTagName(StringConstants.TOPIC);
        
        for (int idx = 0; idx < topicList.getLength(); idx++) {
        	
            Element topic = (Element) topicList.item(idx);
            topics.add(new Topic(topic));
            
        }
        return topics;

    }
    
    /**
     * Removes a topic from the bulletin board. Ensures user is logged in.
     * Topic can only be deleted if user is admin, or topic has no messages.
     * @param topicID
     * @param userID
     * @throws UserNotLoggedInException
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     */
    public static synchronized void deleteTopic(String topicID, String userID)
            throws UserNotLoggedInException, ParserConfigurationException, SAXException, IOException {
    	
        if (!ClientState.instance().getUserID().equals(ADMIN_ID) && getMessageList(topicID).size() > 0) 
            throw new UserNotLoggedInException();
        
        Element topic = topicById(topicID);
        
        topic.getParentNode().removeChild(topic);
        
        saveBoards();
    }
    
    /**
     * Removes a message from a topic.
     * Ensures client is logged in, topic is valid, and
     * the owner of the message is the same as the user removing it.
     * (Admin user can remove all messages).
     * @param messageIdx
     * @param topicID
     * @return
     */
    public static synchronized String deleteMessage(int messageIdx, String topicID) {
    	
        if (!ClientState.instance().isLoggedIn()) 
            return StringConstants.LOGIN_TO_REMOVE_MESSAGE;
        
        Element topic = topicById(topicID);
        String userID = ClientState.instance().getUserID();
        
        if (topic == null) 
            return StringConstants.SET_TOPIC_TO_REMOVE;
        
        else {
            Element messageToRemove = (Element) topic.getElementsByTagName(StringConstants.MESSAGE).item(messageIdx);
            
            if (messageToRemove == null) 
                return StringConstants.NOTHING_TO_REMOVE;
            
            else if (userID.equals(messageToRemove.getAttribute(StringConstants.USER))|| userID.equals(ADMIN_ID)){
                		
            			messageToRemove.getParentNode().removeChild(messageToRemove);
                		
                		saveBoards();
                		
                		return BulletinBoardIO.messagesAsString(ClientState.instance().getTopicID(), false)
                				+ StringConstants.MESSAGE_REMOVED;
            	}
            else 
                return StringConstants.CANT_REMOVE_MSG_FROM_OTHER_USERS;
        }
    }

}
