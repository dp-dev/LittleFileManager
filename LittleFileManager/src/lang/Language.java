package lang;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

public class Language {
	
	static final String[] SPRACHEN = { "English", "Deutsch" };

	public ResourceBundle waehleSprache() {
		String country;
		String language = (String) JOptionPane.showInputDialog(null, "Select your language:", "Language Selection", JOptionPane.INFORMATION_MESSAGE, null, SPRACHEN, SPRACHEN[0]);
		if (language != null) {
			switch (language) {
				case "Deutsch":
					language = "de";
					country = "DE";
					break;
				default:
					language = "en";
					country = "US";
					break;
			}
			Locale langu = new Locale(language, country);
			ResourceBundle rBundle = ResourceBundle.getBundle("lang/language", langu);
			return rBundle;
		} else {
			System.exit(0);
		}
		return null;
	}

}
