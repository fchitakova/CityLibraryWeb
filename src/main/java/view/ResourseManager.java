package main.java.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.PropertyResourceBundle;

import main.java.Constants;

public class ResourseManager {

	public static final String RESOURCE_BUNDLE_BASE_PATH = "C:\\Users\\i356406\\EEeclipse-workspace\\CityLibraryWeb\\WebContent\\WEB-INF\\lib\\resourses\\languageResources_";
	public static final String BG_LANGUAGE_INITIALS = "bg_BG";
	public static final String EN_LANGUAGE_INITIALS = "en";

	protected PropertyResourceBundle languageResources;
	private String currentLanguage;

	public ResourseManager() throws IOException {
		this(Constants.EN_LANGUAGE);

	}

	public ResourseManager(String language) throws IOException {
		currentLanguage = language;
		initResourceBundle();
	}

	private void initResourceBundle() throws IOException {

		StringBuilder filePath = new StringBuilder(RESOURCE_BUNDLE_BASE_PATH);
		if (currentLanguage.equals(Constants.BG_LANGUAGE)) {
			filePath.append(BG_LANGUAGE_INITIALS);
		} else {
			filePath.append(EN_LANGUAGE_INITIALS);
		}
		filePath.append(Constants.PROPERTIES_FILE_EXTENSION);

		InputStream fileInput = new FileInputStream(new File(filePath.toString()));

		languageResources = new PropertyResourceBundle(
				new InputStreamReader(fileInput, Charset.forName(Constants.UTF_8_ENCODING)));
	}
	
	
	public String getResource(String key) {
		return languageResources.getString(key);
	}

	public void changeLanguage() throws IOException {
		currentLanguage = (currentLanguage.equals(Constants.BG_LANGUAGE)) ? Constants.EN_LANGUAGE
				: Constants.BG_LANGUAGE;
		initResourceBundle();
	}

	public String printMessageResourse(String propertyName) {
		if (!propertyName.equals(Constants.EMPTY_MESSAGE)) {
			return languageResources.getString(propertyName);
		}
		return propertyName;
	}

}
