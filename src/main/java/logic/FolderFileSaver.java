package logic;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

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

		String dir = nameFilter(dirName);
		try {

			File file = new File(homeDirPath + dir);
			file.mkdir();

		} catch (Exception e) {
			e.printStackTrace();
		}

		targetDir = homeDirPath + dir + "\\";

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

	/**
	 * doing correct name fore folder
	 * 
	 * @param siteName
	 * @return correct NameFolder
	 */
	private String nameFilter(String siteName) {

		char[] characters = siteName.toCharArray();
		String str = "";

		for (int i = characters.length - 2; i > 0; i--) {

			if (characters[i] == '/') {
				return str;
			}
			str = characters[i] + str;
		}

		return siteName;
	}

}
