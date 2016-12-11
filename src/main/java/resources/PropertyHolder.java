package resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import logic.PropetiesWorker;

public class PropertyHolder {
	// singltone
	private static PropertyHolder instance = new PropertyHolder();

	private static String pathDriver;
	private static String pathExportFolder;
	private static String parseMaxLinkPerYear;
	private static String parseStarYear;
	private static String parseMinTextLenghts;
	private static String parseUniqueValueWaiting;

	public PropertyHolder() {
		updateData();
	}

	private static void updateData() {
		System.out.println("+");
		PropetiesWorker worker = new PropetiesWorker();
		Properties properties = worker.readProperties(Constants.CONFIG_PATH);

		pathDriver = properties.getProperty(PropertyConstants.PATH_DRIVER);
		pathExportFolder = properties.getProperty(PropertyConstants.PATH_EXPORT_FOLDER);
		parseMaxLinkPerYear = properties.getProperty(PropertyConstants.PARSE_MAX_LINK_PER_YEAR);
		parseStarYear = properties.getProperty(PropertyConstants.PARSE_START_YEAR);
		parseMinTextLenghts = properties.getProperty(PropertyConstants.PARSE_MIN_TEXT_LENGHTS);
		parseUniqueValueWaiting = properties.getProperty(PropertyConstants.PARSE_UNIQUE_VALUE_WAITING);

		// write property to System
		System.setProperty("webdriver.gecko.driver", pathDriver);

	}

	private static void setProperty(String name, String value) {

		PropetiesWorker propetiesWorker = new PropetiesWorker();

		Map<String, String> propertiesMap = new HashMap<>();
		propertiesMap.put(name, value);

		propetiesWorker.changePropeties(propertiesMap);

	}

	public static void setPathDriver(String pathDriver) {
		PropertyHolder.setProperty(PropertyConstants.PATH_DRIVER, pathDriver);

		updateData();
	}

	public static void setPathExportFolder(String pathExportFolder) {
		PropertyHolder.setProperty(PropertyConstants.PATH_EXPORT_FOLDER, pathExportFolder);

		updateData();
	}

	public static void setParseMaxLinkPerYear(String parseMaxLinkPerYear) {
		PropertyHolder.setProperty(PropertyConstants.PARSE_MAX_LINK_PER_YEAR, parseMaxLinkPerYear);

		updateData();
	}

	public static void setParseStarYear(String parseStarYear) {
		PropertyHolder.setProperty(PropertyConstants.PARSE_START_YEAR, parseStarYear);

		updateData();
	}

	public static void setParseMinTextLenghts(String parseMinTextLenghts) {
		PropertyHolder.setProperty(PropertyConstants.PARSE_MIN_TEXT_LENGHTS, parseMinTextLenghts);

		updateData();
	}

	public static void setParseUniqueValueWaiting(String parseUniqueValueWaiting) {
		PropertyHolder.setProperty(PropertyConstants.PARSE_UNIQUE_VALUE_WAITING, parseUniqueValueWaiting);

		updateData();
	}

	public static String getPathDriver() {
		return pathDriver;
	}

	public static String getPathExportFolder() {
		return pathExportFolder;
	}

	public static int getParseMaxLinkPerYear() {
		return Integer.parseInt(parseMaxLinkPerYear);
	}

	public static int getParseStarYear() {
		return Integer.parseInt(parseStarYear);
	}

	public static int getParseMinTextLenghts() {
		return Integer.parseInt(parseMinTextLenghts);
	}

	public static int getParseUniqueValueWaiting() {
		return Integer.parseInt(parseUniqueValueWaiting);
	}

	public static PropertyHolder getInstance() {
		return instance;
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("______________________");
		System.out.println("object terminated");
		new PropertyHolder();
		System.out.println("______________________");
	}

}
