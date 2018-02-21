package userData;

import java.util.Map;
import java.util.prefs.Preferences;
import passwordLogicCore.SequenceClassTypes;

/**
 * <h1>UserDataHandler</h1>
 *
 * This class handles everything that has to do with user preferences 
 * saving to and reading from the preferences via Preferences API
 * Note: This class is a Singleton object gets returned by getInstance()
 * 
 * @author alphaClass
 *
 */



public class UserDataHandler {
	private Preferences prefs;
	private static final UserDataHandler handler = new UserDataHandler();
	private UserDataSet currentSettings;
	private Language currentLanguage;
	private static Language languages[];
	
	/**
	 * creates the preferences default if no settings do exist
	 */
	private UserDataHandler() {
		try {
			prefs = Preferences.userNodeForPackage(UserDataHandler.class);
			
			//checks for all preferences set and sets them for the first time run
			for (UserDataTypes type : UserDataTypes.values()) {
				if(prefs.get("" + type, null) == null) {
					prefs.put("" + UserDataTypes.languageName, "English");
					prefs.put("" + UserDataTypes.languageLangCode, "en");
					prefs.put("" + UserDataTypes.languageCountryCode, "GB");
					prefs.put("" + UserDataTypes.lengthOfPw, "12");
					prefs.put("" + UserDataTypes.userTheme, "Metro");
					prefs.putBoolean("" + UserDataTypes.lowerCaseSetting, true);
					prefs.putBoolean("" + UserDataTypes.upperCaseSetting, true);
					prefs.putBoolean("" + UserDataTypes.numberSetting, true);
					prefs.putBoolean("" + UserDataTypes.specialCharSetting, false);
					prefs.putBoolean("" + UserDataTypes.excludeConfusingCharSetting, true);
					break;
				}
			}
			
			languages = new Language[2];
			languages[0] = new Language("Deutsch", "GER", "de");
			languages[1] = new Language("English", "GB", "en");
			
			for(Language language : languages) {
				if(language.getLanguageName().equals(prefs.get("" + UserDataTypes.languageName, ""))) {
					currentLanguage = language;
				}
			}
			
			currentSettings = new UserDataSet(	currentLanguage,
												prefs.get("" + UserDataTypes.lengthOfPw, ""),
												prefs.get("" + UserDataTypes.userTheme, ""),
                                                prefs.getBoolean("" + UserDataTypes.lowerCaseSetting, true),
                                                prefs.getBoolean("" + UserDataTypes.upperCaseSetting, true),
                                                prefs.getBoolean("" + UserDataTypes.numberSetting, true),
                                                prefs.getBoolean("" + UserDataTypes.specialCharSetting, false),
                                                prefs.getBoolean("" + UserDataTypes.excludeConfusingCharSetting, true));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	
	/**
	 * saves all settings in one go
	 * usually triggered by saving the settings
	 */
	public boolean saveAll(UserDataSet newSettings) {
	    Map<SequenceClassTypes, Boolean> map = newSettings.CheckBoxSettings();
        boolean saveSuccess = false;


		for(SequenceClassTypes type : SequenceClassTypes.values()) {
			if(type != SequenceClassTypes.excludeConfusingChars && map.get(type)) {
				saveSuccess = true;
				break;
			}
		}

        if(saveSuccess) {
            for(UserDataTypes type : UserDataTypes.values()) {
                if(type != UserDataTypes.upperCaseSetting) {
                    prefs.put("" + type, newSettings.read(type));
                }
                else {
                    break;
                }
            }
            prefs.putBoolean("" + UserDataTypes.upperCaseSetting, map.get(SequenceClassTypes.upperCaseLetters));
            prefs.putBoolean("" + UserDataTypes.lowerCaseSetting, map.get(SequenceClassTypes.lowerCaseLetters));
            prefs.putBoolean("" + UserDataTypes.numberSetting, map.get(SequenceClassTypes.numbers));
            prefs.putBoolean("" + UserDataTypes.specialCharSetting, map.get(SequenceClassTypes.specialChars));
            prefs.putBoolean("" + UserDataTypes.excludeConfusingCharSetting, map.get(SequenceClassTypes.excludeConfusingChars));

            currentSettings = newSettings;

            //returns the successful save
            return true;
        }
        else {
        	//save was not successful
            return false;
        }

	}
	
	/**
	 * checks if settings have been saved and if
	 * not returns false
	 * 
	 * @param newSettings to check against
	 * @return save type
	 */
	public boolean saveClose(UserDataSet newSettings) {
		return currentSettings.equals(newSettings);
	}
	
	/**
	 * Returns the instance
	 * 
	 * @return UserDataHandler
	 */
	public static UserDataHandler getInstance() {
		return handler;
	}
	
	/**
	 * Returns the user data set object
	 * 
	 * @return UserDataSet
	 */
	public UserDataSet getSettings() {
		return currentSettings;
	}


    /**
     * Returns language object from given language name
     *
     * @param languageName - String
     * @return - Language object
     */
	public Language getLanguage(String languageName) {
        for(Language elem : languages) {
            if(elem.getLanguageName().equals(languageName)) {
                return elem;
            }
        }
        return currentLanguage;
    }
}
