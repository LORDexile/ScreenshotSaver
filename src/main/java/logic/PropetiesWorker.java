package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import resources.Constants;

public class PropetiesWorker {

	public PropetiesWorker() {
		if (!isPropertiesExist()) {
			createPropertiesFile();
		} else {
			System.out.println("OK");
		}

	}

	public void changePropeties(Map<String, String> propertiesMap) {
		Properties properties = readProperties(Constants.CONFIG_PATH);

		for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {

			properties.setProperty(entry.getKey(), entry.getValue());

		}

		writeProperties(Constants.CONFIG_PATH, properties);

	}

	public Properties readProperties(String filePath) {

		Properties properties = new Properties();
		FileInputStream input = null;

		try {

			input = new FileInputStream(filePath);
			properties.load(input);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return properties;

	}

	private void writeProperties(String propetrysPath, Properties properties) {

		FileOutputStream output = null;

		try {

			output = new FileOutputStream(propetrysPath);

			// save properties
			properties.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	private void writeNewPropertiesFile(LinkedHashMap<String, String> popertiesLinkedMap) {

		Properties properties = new Properties();

		// set the properties value
		for (Map.Entry<String, String> entry : popertiesLinkedMap.entrySet()) {

			properties.setProperty(entry.getKey(), entry.getValue());

		}
		writeProperties(Constants.CONFIG_PATH, properties);

	}

	private void writeTemplateProperties() {
		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();

		linkedHashMap.put("driver.path", "");
		linkedHashMap.put("exportFolder.path", "");

		writeNewPropertiesFile(linkedHashMap);

	}

	private boolean isPropertiesExist() {

		File file = new File(Constants.PROGRAM_SOURCE_FOLDER);

		if (file.exists()) {

			file = new File(Constants.CONFIG_PATH);

			if (file.exists()) {

				return true;

			}
		}

		return false;
	}

	private void createPropertiesFile() {

		try {

			File file = new File(Constants.PROGRAM_SOURCE_FOLDER);
			if (file.mkdirs()) {
				System.out.println("dir was created!");
			} else {
				System.out.println("dir exist!");
			}
			file = new File(Constants.CONFIG_PATH);
			if (file.createNewFile()) {
				System.out.println("file was created!");
			} else {
				System.out.println("file exist!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		writeTemplateProperties();

	}
}
