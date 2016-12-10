package interfaces;
import java.io.File;

public interface FileSaver {

	public void createDir(String dirName);

	public void save(File file, String fileName);

}
