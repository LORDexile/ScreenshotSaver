package logic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TextSiteParser {

	private Map<String, Map<String, String>> parsedTextMap = new LinkedHashMap<>();
	private int minimumTextSize = 999;
	Map<String, String> siteTextList = null;
	Map<String, ArrayList<String>> hrefMap = null;
	// minimumTextSize =
	// Integer.parseInt(properties.getProperty("parse.minimumTextSize"));

	public void parseText() {

		for (Map.Entry<String, ArrayList<String>> item : hrefMap.entrySet()) {

			for (String site : item.getValue()) {
				siteTextList = null;
				siteTextList = parseText(site);

			}
			parsedTextMap.put(item.getKey(), siteTextList);

		}
	}

	private Map<String, String> parseText(String site) {

		Document document;

		Map<String, String> siteTextList = new LinkedHashMap<>();

		try {
			System.out.println(site);

			document = Jsoup.connect(site).get();

			// searching elements <p></p>
			for (Element element : document.select("p")) {

				if (element.text().length() > minimumTextSize) {

					// put text and % of uniqueness
					siteTextList.put(element.text(), uniqueTest(element.text()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return siteTextList;

	}

	private String uniqueTest(String Text) {
		return "0";
	}

	public Map<String, Map<String, String>> getParsedTextMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
