package logic;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import interfaces.FileSaver;

public class FolderFileSaver implements FileSaver {

	private String homeDirPath;
	// save directory
	private String targetDir;

	public FolderFileSaver() {
	}

	public FolderFileSaver(String homeDirPath) {
		this.homeDirPath = homeDirPath + "\\";
	}

	/**
	 * Creating save directory
	 */
	public void createDir(String dirName) {

		try {

			File file = new File(homeDirPath + dirName);
			file.mkdir();

		} catch (Exception e) {
			e.printStackTrace();
		}

		targetDir = homeDirPath + dirName + "\\";

	}

	/**
	 * Main method that saves file
	 */
	public void save(File file, String fileName) {

		try {

			FileUtils.copyFile(file, new File(targetDir + fileName));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
