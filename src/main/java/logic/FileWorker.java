package logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import resources.Constants;
import resources.FileType;

public class FileWorker {

	public boolean writeTextFile(List<String> list) {
		return writeTextFile(Constants.TMP_PATH, "tmp", list);
	}

	private boolean writeTextFile(String path, String name, List<String> list) {

		FileOutputStream fileOutputStream = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileOutputStream = new FileOutputStream(path + File.separator + name + ".txt");
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));

			Iterator<String> iterator = list.iterator();
			while (iterator.hasNext()) {
				bufferedWriter.append(iterator.next());
				if (iterator.hasNext()) {
					bufferedWriter.newLine();
				}
			}

			bufferedWriter.flush();
		} catch (IOException ex) {
			System.out.println("IO exception 1");
			return false;
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
			} catch (IOException ex) {

				System.out.println("breack close opertion");
			}

			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException ex) {
				System.out.println("breack close opertion");
			}
		}

		return true;

	}

	public boolean writeObjectFile(String name, Object object) {
		return writeObjectFile(Constants.TMP_PATH, name, object);
	}

	public boolean writeObjectTmp(FileType type, Object object) {
		String fileName = "tmp";
		switch (type) {
		case TMP:
			break;
		case SITE_LIST:
			fileName = "sitelist";
			break;
		case PARSED_SITE_MAP:
			fileName = "parsed_site_list";
			break;
		default:
			return false;
		}

		return writeObjectFile(Constants.TMP_PATH, fileName, object);
	}

	private boolean writeObjectFile(String path, String name, Object object) {
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;

		try {

			fileOutputStream = new FileOutputStream(path + File.separator + name + ".tmp");
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(object);

			objectOutputStream.flush();

		} catch (IOException ex) {

			System.out.println("IO exception 2");
			return false;
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException ex) {
				System.out.println("breack close opertion");
			}

			try {
				if (objectOutputStream != null) {
					objectOutputStream.close();
				}
			} catch (IOException ex) {
				System.out.println("breack close opertion");
			}
		}
		return true;
	}

	public Object readObjectFile(String name) {
		return readObjectFile(Constants.TMP_PATH, name);
	}

	public Object readObjectTmp(FileType type) {
		String fileName = "tmp";
		switch (type) {
		case TMP:
			break;
		case SITE_LIST:
			fileName = "sitelist";
			break;
		case PARSED_SITE_MAP:
			fileName = "parsed_site_list";
			break;
		default:
			return false;
		}
		return readObjectFile(Constants.TMP_PATH, fileName);
	}

	private Object readObjectFile(String path, String name) {

		Object object = null;

		System.out.println("reading...");

		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;

		try {

			fileInputStream = new FileInputStream(path + File.separator + name + ".tmp");
			objectInputStream = new ObjectInputStream(fileInputStream);

			object = objectInputStream.readObject();

		} catch (IOException ex) {
			System.out.println("IO exception 3");
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("CNF exception 1");
			return null;
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException ex) {
				System.out.println("breack close opertion");
			}

			try {
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			} catch (IOException ex) {
				System.out.println("breack close opertion");
			}
		}
		return object;
	}
}
