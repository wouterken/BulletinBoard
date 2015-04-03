package model;
import org.w3c.dom.Element;

/**
 *  Represents a topic on the bulletin board
 *
 */
public class Topic {

	private String name;
	private String id;

	/**
	 * create a named topic
	 * @param topic
	 */
	public Topic(Element topic) {
		name = topic.getAttribute("name");
		id = topic.getAttribute("id");
	}

	/**
	 *  get the name of the topic
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Formatted representation of the topic.
	 */
	public String toString(){
		return String.format("%s: (%s)", this.name, this.id);
	}
}