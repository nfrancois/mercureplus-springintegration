package nfrancois.mercureplus.model.gplus;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityObjectPlusoners {

	private Long totalItems;
	private String selfLink;

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public String getSelfLink() {
		return selfLink;
	}

	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}

}
