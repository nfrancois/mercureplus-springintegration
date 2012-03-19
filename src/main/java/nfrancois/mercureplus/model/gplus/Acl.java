package nfrancois.mercureplus.model.gplus;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Acl {

	private List<PlusAclentryResource> items;
	private String kind;
	private String description;

	public List<PlusAclentryResource> getItems() {
		return items;
	}

	public void setItems(List<PlusAclentryResource> items) {
		this.items = items;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
