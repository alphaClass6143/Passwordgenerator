package userData;

import passwordLogicCore.SequenceClassTypes;

import java.util.EnumMap;
import java.util.Map;

/**
 * <h1>UserDataSet</h1>
 *
 * Data Exchange Object to exchange the user data easier
 * This object has no other use than storing user information
 *
 * @author alphaClass
 *
 */
public class UserDataSet {
	private final Language language;
	private final String lengthOfPw;
	private final String userTheme;
	private final boolean lowerCaseSetting;
	private final boolean upperCaseSetting;
	private final boolean numberSetting;
	private final boolean specialCharSetting;
	private final boolean excludeConfusingCharSetting;


	/**
	 * Init a Data Exchange Object with the given parameters
	 * @param language - language object
	 * @param lengthOfPw - length of the password
	 * @param userTheme - used userTheme
	 * @param lowerCaseSetting - boolean
	 * @param upperCaseSetting - boolean
	 * @param numberSetting - boolean
	 * @param specialCharSetting - boolean
	 * @param excludeConfusingCharSetting - boolean
	 */
	public UserDataSet(Language language, String lengthOfPw, String userTheme, boolean lowerCaseSetting, boolean upperCaseSetting, boolean numberSetting, boolean specialCharSetting, boolean excludeConfusingCharSetting) {
		this.language = language;
		this.lengthOfPw = lengthOfPw;
		this.userTheme = userTheme;
		this.lowerCaseSetting = lowerCaseSetting;
		this.upperCaseSetting = upperCaseSetting;
		this.numberSetting = numberSetting;
		this.specialCharSetting = specialCharSetting;
		this.excludeConfusingCharSetting = excludeConfusingCharSetting;
	}

	/**
	 * Read all string settings in the UserDataSet
	 * @param type - UserDataTypes type of user data specified by enum
	 * @return - String of the setting
	 */
	public String read(UserDataTypes type) {
		switch(type)  {
			case languageName:
				return language.getLanguageName();
			case languageCountryCode:
				return language.getLanguageCountryCode();
			case languageLangCode:
				return language.getLanguageLangCode();
			case lengthOfPw:
				return lengthOfPw;
			case userTheme:
				return userTheme;
		}
		return "" + type;
	}

	/**
	 * Returns a boolean array with the CheckBox settings
	 * @return boolean[] - Content order: upperCaseSetting - lowerCaseSetting - numberSetting - specialCharSetting - excludeConfusingCharSetting
	 */
	public Map<SequenceClassTypes, Boolean> CheckBoxSettings() {


		Map<SequenceClassTypes, Boolean> output = new EnumMap<>(SequenceClassTypes.class);

		output.put(SequenceClassTypes.upperCaseLetters, upperCaseSetting);
		output.put(SequenceClassTypes.lowerCaseLetters, lowerCaseSetting);
		output.put(SequenceClassTypes.numbers, numberSetting);
		output.put(SequenceClassTypes.specialChars, specialCharSetting);
		output.put(SequenceClassTypes.excludeConfusingChars, excludeConfusingCharSetting);
		return output;
	}

}
