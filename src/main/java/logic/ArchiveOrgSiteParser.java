package logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import interfaces.SiteParser;
import interfaces.SiteParserExtend;
import resources.Constants;

public class ArchiveOrgSiteParser implements SiteParser, SiteParserExtend {

	private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

	private String lastYearPrefix = "https://web.archive.org/web/*/";
	private String targetYearPrefix = "https://web.archive.org/web/";
	private String targetYearPostfix = "0101000000*/";
	private int starYear;

	private int maxLinkPerYear = 12;
	private boolean monthGroup1 = true, monthGroup2 = true, monthGroup3 = true, monthGroup4 = true;

	private String regex = "/web/([0-9]{1,}[/]{1,1})";// regular expression
	private String targetSite;

	private Map<String, ArrayList<String>> hrefMap = new HashMap<>();
	private ArrayList<String> hrefList = new ArrayList<>();

	public ArchiveOrgSiteParser() {
		PropetiesWorker propetiesWorker = new PropetiesWorker();
		Properties properties = propetiesWorker.readProperties(Constants.CONFIG_PATH);

		starYear = Integer.parseInt(properties.getProperty("parse.starYear"));
		maxLinkPerYear = Integer.parseInt(properties.getProperty("parse.maxLinkPerYear"));
	}

	/**
	 * parse target site, to get Map of unique urls.
	 */
	public void parse() {

		if (targetSite == null) {
			return;
		}

		System.out.println("Start parsing " + targetSite + "... [Option = All YEARS{2006 - " + currentYear + "}]");

		for (int i = starYear; i <= currentYear; i++) {
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

		if (year == currentYear) {
			parseLastYear();
			return;
		}

		System.out.println("Start parsing " + targetSite + "... [Option = " + year + " YEAR]");

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

			ArrayList<String> newList = new ArrayList<>();

			for (Element link : links) {
				String uniqLink = "https://web.archive.org" + link.attr("href");

				if (!newList.contains(uniqLink) && !hrefList.contains(uniqLink)) {
					newList.add(uniqLink);
				}

			}

			if (newList.size() > maxLinkPerYear) {

				for (String string : newList) {
					if (monthFilter(string)) {

						hrefList.add(string);
					}
				}
				monthGroup1 = true;
				monthGroup2 = true;
				monthGroup3 = true;
				monthGroup4 = true;

			} else {
				hrefList.addAll(newList);
			}
			// Site name + List of Links
			hrefMap.put(targetSite, hrefList);
			System.out.println(targetSite + " - " + hrefList.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Clear map of urls
	 */
	public void clear() {

		hrefMap = new HashMap<>();
		hrefList = new ArrayList<>();

	}

	private boolean monthFilter(String string) {

		// take month from url
		int beginIndex = 32;// 9
		int endIndex = 34;// 11
		int manthKey = Integer.parseInt(string.substring(beginIndex, endIndex));

		switch (manthKey) {
		case 1:
		case 2:
		case 3:
			if (monthGroup1) {
				monthGroup1 = false;
				return true;
			}
			return false;
		case 4:
		case 5:
		case 6:
			if (monthGroup2) {
				monthGroup2 = false;
				return true;
			}
			return false;
		case 7:
		case 8:
		case 9:
			if (monthGroup3) {
				monthGroup3 = false;
				return true;
			}
			return false;
		case 10:
		case 11:
		case 12:
			if (monthGroup4) {
				monthGroup4 = false;
				return true;
			}
			return false;
		}

		return false;
	}

	/**
	 * Return map URL
	 */
	public Map<String, ArrayList<String>> getTargetSiteMap() {

		return hrefMap;
	}

	/**
	 * Set site you want to parse
	 */
	public void setTargetSite(String targetSite) {
		this.targetSite = targetSite;
		hrefList = new ArrayList<>();
	}

	/**
	 * Set regular expression, for target urls
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}

}
