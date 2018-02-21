package passwordLogicCore;


import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import methodPackage.RandomNumberLists;


/**
 * <h1>PWGen</h1>
 *
 * This class generates the password object with a given sequence.
 * There are currently two ways to set up a PWGen object
 * 1. List input of all characters
 * 2. Input via character groups defined in the SequenceClassTypes enum
 *
 */

public class PWGen {
	//set up the sequence
	private final List<Integer> sequenceList = new ArrayList<>();

    /**
     * Initiates the PWGen object with a list of allowed character groups
     * @param types - HashSet of all allowed characters groups
     */
    public PWGen(HashSet<SequenceClassTypes> types) {
        //init available character groups
        final Integer[] lowercaseLetters = {97,98,99,100,101,102,103,104,105,106,107,108,108,110,111,112,113,114,115,116,117,118,119,120,121,122};
        final Integer[] uppercaseLetters = {65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,81,82,83,84,85,86,87,88,89,90};
        final Integer[] numbers = {48,49,50,51,52,53,54,55,56,57};
        //list of chars in pairs starting at index 0
		//character list in order separated by commas
		//-,_,I,i,l,0,O,o
        final Integer[] excludeConfusingChars = {45,95,73,105,108,48,79,111};
        final Integer[] specialChars = {33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,58,59,60,61,62,63,64,91,92,93,94,95,96,123,124,125,126};


        boolean removeConfusingChars = false;

        /*
        Adds all the character groups to sequence list for the password generator
        NOTE: excludeConfusingChars has special treatment because of the opposite operation (removing instead of adding)
         */
        for(SequenceClassTypes elem : types) {
            switch(elem) {
                case lowerCaseLetters:
                    Collections.addAll(sequenceList, lowercaseLetters);
                    break;
                case upperCaseLetters:
                    Collections.addAll(sequenceList, uppercaseLetters);
                    break;
                case numbers:
                    Collections.addAll(sequenceList, numbers);
                    break;
                case specialChars:
                    Collections.addAll(sequenceList, specialChars);
                    break;
                case excludeConfusingChars:
                    //exclude chars
                    removeConfusingChars = true;
                    break;
            }
        }

        //removes all confusing chars if needed
        if(removeConfusingChars) {
            sequenceList.removeAll(Arrays.asList(excludeConfusingChars));
        }
    }

	/**
	 * 
	 * generates the password with the given length
	 *
	 * 
	 * @param length - length of password
	 * @return String - password
	 */
	public String generatePassword(int length) {
		//initiate string builder for the password 
		StringBuilder generatedPW = new StringBuilder();

		//set up taboo list
		ArrayBlockingQueue<Integer> tabooList = new ArrayBlockingQueue<>(this.sequenceList.size()/2);

		//copies original sequenceList into a local sequenceList for modification
		List<Integer> sequenceList = new ArrayList<>(this.sequenceList);

		//store the index of the charNum
		int charIndex;

		//generate password
		while(generatedPW.length() < length) {
			charIndex = RandomNumberLists.randomNumber(0, sequenceList.size()-1);

			//append to the password
			generatedPW.append((char)(int)sequenceList.get(charIndex));

			//check if taboo list is full
			if(tabooList.remainingCapacity() == 0) {
				sequenceList.add(tabooList.poll());
			}

			//append to taboo list and removing from the local sequence list
			tabooList.offer(sequenceList.remove(charIndex));
		}

		return generatedPW.toString();
	}
}
