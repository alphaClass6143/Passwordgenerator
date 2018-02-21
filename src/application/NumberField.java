package application;

import javafx.scene.control.*;


/**
 * <h1>NumberField</h1>
 *
 * TextField that only takes numbers as input.
 * A NumberField can be limited with a min and max value.
 * Limited NumberFields however can't be changed back to an unlimited NumberField
 * Min value only gets checked if the NumberField looses focus
 * other implementations for text values are not possible
 *
 *
 * @author alphaClass
 * Created by alphaClass on 30.01.2017.
 */

public class NumberField extends TextField {
	//The expression that only checks if the given input is a number
	//The implementation of the NumberField might switch over to a
	//Regex only version without parsing to Integer but at the
	//moment custom regex is not possible in this class
	final String numberRegEx = "\\b\\d+\\b";

	boolean isLimited;
	private int min;
	private int max;
	private String tooltipText;

	/**
	 * Unlimited NumberField with no restrictions except
	 * numbers only inputs
	 * this can be changed later by using the given setters
	 * for the min and max values
	 */
	public NumberField() {
		super();

		//set for no limitation
		isLimited = false;
	}

	/**
	 * Limits number input to the given min and max values
	 * This automatically limits the NumberField to a limited NumberField
	 * and can't be changed back to an unlimited NumberField
	 *
	 * @param min - is the minimum value
	 * @param max - is the maximum value
	 */
	public NumberField(int min, int max) {
		//set min and max values
		this.min = min;
		this.max = max;

		//update
		updateToLimitedNumberField();
	}


	/**
	 * Replaces the text with the given string
	 *
	 * @param start - the starting position of the replaced text
	 * @param end - the ending position of the replaced text
	 * @param text - the text that it gets set to
	 */
	@Override
	public void replaceText(int start, int end, String text) {
		//gets text from NumberField
		//deletes the selected text --> would get replaced anyway so it can be removed from the checked string
		//deleting the selected text is necessary otherwise the checked string could be invalid (it sometimes would be too long)
		//the new text gets appended
		String num = getText().replace(getSelectedText(), "") + text;


		//check if the NumberField gets emptied
		if(num.equals("")) {
			super.replaceText(start, end, text);
		}

		//check if the input is a valid number
		else if((validate(num))) {
			//check if the NumberField has a limitation
			if(isLimited) {
				//convert the text to number
				int newVal = Integer.parseInt(num);

				//check if the text is in the max range
				//min range cannot be checked since there could
				//be more input written after the replacing
				if(newVal <= max) {
					super.replaceText(start, end, text);
				}
			}

			//replaces if the NumberField has no limitation anyway
			else {
				super.replaceText(start, end, text);
			}

		}

	}


	/**
	 * Sets the listener and the boolean value for a limited NumberField
	 * it also updates the tooltip with the new min and max values
	 */
	private void updateToLimitedNumberField() {
		//checks if the limiter is already set
		if(!isLimited) {
			//activate listener
			this.focusedProperty().addListener((obs, oldVal, newVal) -> {
				//check if the focus of the NumberField is lost
				if(!newVal) {
					if(validate(getText())) {
						//get the current NumberField text
						int current = Integer.parseInt(getText());

						//check if current NumberField text is less than the min value
						//the max value needs no check because the replaceText method
						//prevents values that are to high
						if (current < min) {
							//replace text with the minimum value
							this.setText("" + min);
						}
					}
					else {
						this.setText("" + min);
					}
				}

			});

			//set isLimited to true --> has not been set yet
			this.isLimited = true;
		}

		//set the new tooltip --> this also overwrites the old tooltip
		this.setTooltip(new Tooltip("Minimum: " + min + ", Maximum: " + max));
	}

	/**
	 * Checks if the given string is not empty and a number
	 * Prevents the parseInt functions from throwing NumberFormatExceptions
	 *
	 * @param text - the string that needs to be validated
	 * @return - the boolean value if it was valid or not
	 */
	private boolean validate(String text) {
		return (text.matches(numberRegEx));
	}



	/**
	 * Sets the min value for the NumberField
	 * If this value is being set for the first time the NumberField changes to a LimitedNumberField
	 * this can't be changed back to an unlimited NumberField
	 * @param min - The value it should be set to
	 */
	final public void setMin(int min) {
		//set min
		this.min = min;

		//update the tooltip and the limitation
		updateToLimitedNumberField();
	}

	/**
	 * Sets the max value for the NumberField
	 * If this value is being set for the first time the NumberField changes to a LimitedNumberField
	 * this can't be changed back to an unlimited NumberField
	 * @param max - The value it should be set to
	 */
	final public void setMax(int max) {
		//set max
		this.max = max;

		//update the tooltip and the limitation
		updateToLimitedNumberField();
	}

	/**
	 * Returns the current max value for the NumberField
	 * @return The max value of the NumberField
	 */
	final public int getMax() {
		return max;
	}

	/**
	 * Returns the current min value for the NumberField
	 * @return The min value of the NumberField
	 */
	final public int getMin() {
		return min;
	}


	/**
	 * Returns if the NumberField is currently limited or not
	 * @return The min value of the NumberField
	 */
	final public boolean isLimited() {
		return isLimited;
	}

}
