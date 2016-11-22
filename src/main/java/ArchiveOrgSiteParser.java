import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ArchiveOrgSiteParser implements SiteParser {

	private String targetSite = "https://web.archive.org/web/*/";
	private String regex = "/web/([0-9]{1,}[/]{1,1})";
	private Map<String, String> map = new HashMap<>();

	/**
	 * parse target site, to get Map of unique urls.
	 */
	public void parse() {

		try {
			// get site context
			Document document = Jsoup.connect(targetSite).get();

			// getting from site body list of elements with attribute "href"
			// checks for compliance using a regular expression
			Element myBody = document.body();
			Elements links = myBody.getElementsByAttributeValueMatching("href", Pattern.compile(regex));

			for (Element link : links) {

				map.put(link.attr("href"), "");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Convert Map to List. completing URL
	 */
	public List<String> getTargetSiteList() {

		List<String> targetSiteList = new ArrayList<>();

		int i = 0;
		for (Map.Entry<String, String> entry : map.entrySet()) {

			targetSiteList.add("https://web.archive.org" + entry.getKey());

		}

		return targetSiteList;
	}

	public void setTargetSite(String targetSite) {
		this.targetSite += targetSite;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

}
