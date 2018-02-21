package userData;

/**
 * <h1>Language</h1>
 *
 * Language object which contains the basic information about the language
 * useful for easier data exchange in the UserDataHandler and easier to
 * use with LanguageBundles
 *
 */
public class Language {

	private final String languageName;
	private final String languageCountryCode;
	private final String languageLangCode;

	/**
	 * Creates a Language object with the given parameters
	 *
	 * @param languageName - the name of the language
	 * @param languageCountryCode - the country code of the language
	 * @param languageLangCode - the language code of the language
	 */
	public Language(String languageName, String languageCountryCode, String languageLangCode) {
		this.languageName = languageName;
		this.languageCountryCode = languageCountryCode;
		this.languageLangCode = languageLangCode;
	}

	/**
	 * Returns the name languageName of the Language object
	 * @return the name of the Language object
	 */
	public String getLanguageName() {
		return languageName;
	}

	/**
	 * Returns the countryCode of the Language object
	 * @return the country code of the Language object
	 */
	String getLanguageCountryCode() {
		return languageCountryCode;
	}


	/**
	 * Returns the langCode of the Language object
	 * @return the language code of the Language object
	 */
	String getLanguageLangCode() {
		return languageLangCode;
	}
}
