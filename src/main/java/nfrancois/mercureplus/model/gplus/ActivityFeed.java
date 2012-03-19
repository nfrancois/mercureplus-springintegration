package nfrancois.mercureplus.model.gplus;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityFeed {

	private String kind;
	private String title;
	private String nextPageToken;
	private String selfLink;
	private String nextLink;
	private String id;
	private DateTime updated;
	private List<Activity> items;
	

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNextPageToken() {
		return nextPageToken;
	}

	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}

	public String getSelfLink() {
		return selfLink;
	}

	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}

	public String getNextLink() {
		return nextLink;
	}

	public void setNextLink(String nextLink) {
		this.nextLink = nextLink;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DateTime getUpdated() {
		return updated;
	}

	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}

	public List<Activity> getItems() {
		return items;
	}

	public void setItems(List<Activity> items) {
		this.items = items;
	}


}
