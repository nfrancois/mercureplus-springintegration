package nfrancois.mercureplus.model.gplus;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityObject {

	private ActivityObjectResharers resharers;
	private List<ActivityObjectAttachments> attachments;
	private String originalContent;
	private ActivityObjectPlusoners plusoners;
	private ActivityObjectActor actor;
	private String content;
	private String url;
	private ActivityObjectReplies replies;
	private String id;
	private String objectType;

	public ActivityObjectResharers getResharers() {
		return resharers;
	}

	public void setResharers(ActivityObjectResharers resharers) {
		this.resharers = resharers;
	}

	public List<ActivityObjectAttachments> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<ActivityObjectAttachments> attachments) {
		this.attachments = attachments;
	}

	public String getOriginalContent() {
		return originalContent;
	}

	public void setOriginalContent(String originalContent) {
		this.originalContent = originalContent;
	}

	public ActivityObjectPlusoners getPlusoners() {
		return plusoners;
	}

	public void setPlusoners(ActivityObjectPlusoners plusoners) {
		this.plusoners = plusoners;
	}

	public ActivityObjectActor getActor() {
		return actor;
	}

	public void setActor(ActivityObjectActor actor) {
		this.actor = actor;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ActivityObjectReplies getReplies() {
		return replies;
	}

	public void setReplies(ActivityObjectReplies replies) {
		this.replies = replies;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

}
