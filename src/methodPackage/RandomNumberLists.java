
package methodPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * RandomNumberLists
 *
 * This class creates various lists of Integers
 * Those lists can be used to test things
 * without having to write code to generate
 * the lists
 *
 * IMPORTANT: Every method is static on purpose
 * to make it executable in a main class
 *
 * @author alphaClass
 *
 */
public class RandomNumberLists {
	/**
	 * Generate a random Number from 0-99
	 * @return int the random integer
	 */
	public static int randomNumber() {
		return (int)(Math.random() * 100);
	}
	
	/**
	 * Generates a random Number in the given range start and end
	 * Start and end number are included as well
	 * @param start - Integer start number
	 * @param end _ Integer end number
	 * @return int - the random integer in the specific range
	 */
	public static int randomNumber(int start, int end) {
		return (int)(Math.random() * (end-start+1) + start);
	}
	
	/**
	 * Generate an ArrayList with the given size as Integer
	 * @param size - sets the size of the ArrayList. Size is limited to 100 
	 * @return List<Integer> list of Integers (by given size)
	 */
	public static List<Integer> generateArrayList(int size) {
		//limits the size to 100
		int setSize = (size > 100) ? 100 : size;
		List<Integer> returnList = new ArrayList<>(setSize);
		
		//fill the ArrayList with numbers
		for(int i = 0; i < setSize; i++) {
			returnList.add(randomNumber());
		}
		
		return returnList;
	}
	
	/**
	 * Generate an ArrayList with the default size 10
	 * @return List<Integer> list of 10 Integers
	 */
	public static List<Integer> generateArrayList() {
		List<Integer> returnList = new ArrayList<>();
		
		//fill the ArrayList with numbers
		for(int i = 0; i < 10; i++) {
			returnList.add(randomNumber());
		}
		
		return returnList;
	}
	
	/**
	 * Generate a simple array with the given size as Integer
	 * 
	 * @param size - sets the size of the array. Size is limited to 100 
	 * @return int[] simple array with Integers (by given size)
	 */
	public static int[] generateArray(int size) {
		//limits the size to 100
		int setSize = (size > 100) ? 100 : size;
		int[] returnList = new int[setSize];
		
		//fill array with numbers
		for(int i = 0; i < setSize; i++) {
			returnList[i] = randomNumber();
		}
		
		return returnList;
	}
	
	public static int[] generateArray() {
		int[] returnList = new int[10];
		
		//fill the array with numbers
		for(int i = 0; i < 10; i++) {
			returnList[i] = randomNumber();
		}

		return returnList;
	}
	
	/*
	Test main method
	uncomment if needed

	public static void main(String[] args) {
		for(int i = 0; i < 10000; i++) {
			int randomNumber = randomNumber(1, 25);
			
			
			if(randomNumber < 3) {
				System.out.println(randomNumber);
			} 
		}
	} */

}
