package nfrancois.mercureplus.model.gplus;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityObjectActor {

	private String url;
	private ActivityObjectActorImage image;
	private String displayName;
	private String id;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ActivityObjectActorImage getImage() {
		return image;
	}

	public void setImage(ActivityObjectActorImage image) {
		this.image = image;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
