package passwordLogicCore;


/**
 * <h1>InvalidSequenceException</h1>
 *
 * This Exception is thrown when the custom
 * sequence has characters in it that are not
 * allowed
 */
public class InvalidSequenceException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private final int invalidNum;
	
	InvalidSequenceException(int invalidNum) {
		this.invalidNum = invalidNum;
	}

	/**
	 * What could this method possibly do?
	 * Maybe it generates a String with the error description?
	 * I don't know and we will never find it out
	 * @return - A mysterious String
	 */
	public String toString() {
		return "Invalid number in Sequence: " + invalidNum + " - Only numbers between 33 and 126 (included) are allowed";
	}

}
