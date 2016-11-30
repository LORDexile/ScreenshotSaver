package logic;

import java.util.ArrayList;
import java.util.Map;

public interface SiteParser {

	public void setTargetSite(String targetSite);

	public void parse();

	public void clear();

	public Map<String, ArrayList<String>> getTargetSiteMap();

	public void setRegex(String regex);
}
