package exceptions;

/**
 * Exception for handling commands that are not passed the correct type of arguments.
 * @author wouterken
 *
 */
public class IncorrectArgumentType extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String wasType;
	private String shouldBe;

	public IncorrectArgumentType(String wasType, String shouldBe){
		this.wasType = wasType;
		this.shouldBe = shouldBe;
	}
	public String getMessage(){
		return String.format("Argument is formatted incorrectly, was type: %s, should be: %s", wasType, shouldBe);
	}
}
