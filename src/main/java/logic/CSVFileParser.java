package logic;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import interfaces.FileParser;

public class CSVFileParser implements FileParser {

	public List<String> readFile(String filePath) throws IOException {

		return readCSVFile(filePath);

	}

	private List<String> readCSVFile(String filePath) throws IOException {

		FileReader reader = new FileReader(filePath);

		CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL);
		List<CSVRecord> CSVList = parser.getRecords();
		parser.close();
		reader.close();

		List<String> itemList = new ArrayList<>();

		if (CSVList != null) {
			for (CSVRecord csvRecord : CSVList) {

				for (int i = 0; i < csvRecord.size(); i++) {
					String item = csvRecord.get(i);
					if (!item.equals("")) {
						itemList.add(item);
					}
				}

			}

		}
		return itemList;
	}

}
