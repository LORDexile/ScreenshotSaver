package interfaces;
import java.io.IOException;
import java.util.List;

public interface FileParser {

	public List<String> readFile(String filePath) throws IOException;

}
