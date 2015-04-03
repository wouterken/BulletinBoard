package model;

import org.w3c.dom.Element;

/**
 * Represents a message on a topic in the Bulletin Board.
 * @author wouterken
 *
 */
public class Message {

	private String username;
	private String userid;
	private String text;

	/**
	 * Create a message with a user, and some text. (its ID is contained within its parent document).
	 * @param message
	 */
	public Message(Element message) {
		this.userid = message.getAttribute("user");
		this.username = BulletinBoardIO.getUserName(this.userid);
		this.text = message.getTextContent();
	}
	
	/**
	 * Formatted representation of the topic.
	 */
	public String toString(){
		return String.format("%s:%s", this.username, this.text);
	}
}
