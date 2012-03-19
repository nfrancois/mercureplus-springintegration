package nfrancois.mercureplus.model.gplus;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityActorImage {

	private String url;

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
