package nfrancois.mercureplus.model.mercureplus;

public class Message {
	
	private String content;
	private String shortUrl;
	private boolean hasRichContent;
	
	public Message(String content, String shortUrl, boolean hasRichContent) {
		this.content = content;
		this.shortUrl = shortUrl;
		this.hasRichContent = hasRichContent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public boolean isHasRichContent() {
		return hasRichContent;
	}
	public void setHasRichContent(boolean hasRichContent) {
		this.hasRichContent = hasRichContent;
	}
	

}
