package logic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import resources.PropertyHolder;

public class TextSiteParser {

	private Map<String, Map<String, String>> parsedTextMap = null;

	private Map<String, ArrayList<String>> siteMap = null;
	private Map<String, String> siteTextList = new LinkedHashMap<>();
	private List<String> siteList = null;

	public void setTargetSiteMap(Map<String, ArrayList<String>> siteMap) {
		this.siteMap = siteMap;
	}

	public void setTargetSiteList(List<String> siteList) {
		this.siteList = siteList;
	}

	public void parseTextMap() {
		parsedTextMap = new LinkedHashMap<>();

		for (Map.Entry<String, ArrayList<String>> item : siteMap.entrySet()) {

			for (String site : item.getValue()) {

				Map<String, String> tmpList = parseText(site);

				if (tmpList != null) {
					siteTextList.putAll(tmpList);
				}

			}
			// put only if text found
			if (siteTextList.size() != 0) {

				parsedTextMap.put(item.getKey(), siteTextList);
				siteTextList = new LinkedHashMap<>();
			}

		}
	}

	public void parseTextList() {

		parsedTextMap = new LinkedHashMap<>();

		for (String site : siteList) {

			if (!site.substring(0, 4).equals("http")) {
				if (site.substring(0, 3).equals("www")) {
					site = "https://" + site;
				} else {
					site = "https://www." + site;
				}
			}

			Map<String, String> tmpList = parseText(site);

			if (tmpList != null) {
				siteTextList.putAll(tmpList);
			}

			// put only if text found
			if (siteTextList.size() != 0) {

				parsedTextMap.put(site, siteTextList);
				siteTextList = new LinkedHashMap<>();
			}
		}

	}

	private Map<String, String> parseText(String site) {

		Document document;

		Map<String, String> textList = new LinkedHashMap<>();

		try {

			System.out.println("[getin Text from] " + site);

			document = Jsoup.connect(site).get();

			// searching elements <p></p>
			for (Element element : document.select("p")) {

				if (element.text().length() >= PropertyHolder.getParseMinTextLength()) {

					System.out.println("text.Length.ok");

					// put text and % of uniqueness
					if (!textList.containsKey(element.text())) {

						System.out.println("text.uniq.ok");
						System.out.println(element.text());
						textList.put(element.text(), uniqueTest(element.text()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return textList;

	}

	private String uniqueTest(String text) {
		TextParserSelenium parser = new TextParserSelenium();

		return parser.start(text);
	}

	public Map<String, Map<String, String>> getParsedTextMap() {

		return parsedTextMap;
	}

}
