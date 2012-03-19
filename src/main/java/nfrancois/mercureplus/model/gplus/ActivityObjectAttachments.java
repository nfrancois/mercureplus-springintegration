package nfrancois.mercureplus.model.gplus;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityObjectAttachments {

	private String displayName;
	private ActivityObjectAttachmentsFullImage fullImage;
	private String url;
	private ActivityObjectAttachmentsImage image;
	private String content;
	private ActivityObjectAttachmentsEmbed embed;
	private String id;
	private String objectType;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public ActivityObjectAttachmentsFullImage getFullImage() {
		return fullImage;
	}

	public void setFullImage(ActivityObjectAttachmentsFullImage fullImage) {
		this.fullImage = fullImage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ActivityObjectAttachmentsImage getImage() {
		return image;
	}

	public void setImage(ActivityObjectAttachmentsImage image) {
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ActivityObjectAttachmentsEmbed getEmbed() {
		return embed;
	}

	public void setEmbed(ActivityObjectAttachmentsEmbed embed) {
		this.embed = embed;
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
