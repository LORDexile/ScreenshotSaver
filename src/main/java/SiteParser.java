import java.util.List;

public interface SiteParser {

	public void setTargetSite(String targetSite);

	public void parse();

	public void clear();

	public List<String> getTargetSiteList();

	public void setRegex(String regex);
}
