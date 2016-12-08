package resources;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

public final class Constants {
	public static final String PRODUCT_VERSION = "0.1.3";
	public static final String PROGRAM_NAME = "ScreenShotSaver";
	public static final String PROGRAM_TITLE = PROGRAM_NAME + " ver: [" + PRODUCT_VERSION + "]";
	public static final String PROGRAM_SOURCE_FOLDER = FileSystemView.getFileSystemView().getDefaultDirectory() + File.separator + PROGRAM_NAME;
	public static final String CONFIG_PATH = PROGRAM_SOURCE_FOLDER + File.separator + "config.properties";

}
