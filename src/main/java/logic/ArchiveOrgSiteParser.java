package logic;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ArchiveOrgSiteParser implements SiteParser, SiteParserExtend {

	private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

	private String lastYearPrefix = "https://web.archive.org/web/*/";
	private String targetYearPrefix = "https://web.archive.org/web/";
	private String targetYearPostfix = "0101000000*/";

	private String regex = "/web/([0-9]{1,}[/]{1,1})";// regular expression
	private String targetSite;

	private Map<String, String> map = new HashMap<>();

	/**
	 * parse target site, to get Map of unique urls.
	 */
	public void parse() {

		if (targetSite == null) {
			return;
		}

		System.out.println("Start parsing " + targetSite + "... [Option = All YEARS{2006 - " + currentYear + "}]");

		for (int i = 2006; i <= currentYear; i++) {
			parse(i);
		}

	}

	/**
	 * parse target site by year, to get Map of unique urls.
	 */
	public void parse(int year) {

		if (targetSite == null) {
			return;
		}

		System.out.println("Start parsing " + targetSite + "... [Option = " + year + " YEAR]");

		if (year == currentYear) {
			parseLastYear();
			return;
		}

		parse(targetYearPrefix + year + targetYearPostfix + targetSite);
	}

	// parse target site by last year(different page).
	private void parseLastYear() {
		System.out.println("Start parsing " + targetSite + "... [Option = LAST{" + currentYear + "} YEAR]");

		parse(lastYearPrefix + targetSite);
	}

	// main parsing method
	private void parse(String site) {

		try {
			// get site context
			Document document = Jsoup.connect(site).get();

			// getting from site body list of elements with attribute "href"
			// checks for compliance using a regular expression
			Element myBody = document.body();
			Elements links = myBody.getElementsByAttributeValueMatching("href", Pattern.compile(regex));

			for (Element link : links) {

				map.put(link.attr("href"), targetSite);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Clear map of urls
	 */
	public void clear() {
		map = new HashMap<>();
	}

	/**
	 * Convert Map to List. completing URL
	 */
	public List<String> getTargetSiteList() {

		List<String> targetSiteList = new ArrayList<>();

		if (map == null) {
			return targetSiteList;
		}

		for (Map.Entry<String, String> entry : map.entrySet()) {

			targetSiteList.add("https://web.archive.org" + entry.getKey());

		}

		return targetSiteList;
	}

	/**
	 * Set site you want to parse
	 */
	public void setTargetSite(String targetSite) {
		this.targetSite = targetSite;
	}

	/**
	 * Set regular expression, for target urls
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}

}
