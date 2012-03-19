package nfrancois.mercureplus.model.googl;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.google.common.base.Objects;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class GooGlObject {

	private String kind;
	@JsonProperty("id")
	private String shortUrl;
	private String longUrl;

	public GooGlObject() {
	};
	
	public GooGlObject(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getLongUrl() {
		return longUrl;
	}
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(longUrl);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null){
			return false;
		}
		if(o instanceof GooGlObject){
			GooGlObject other = (GooGlObject) o;
			return Objects.equal(other.longUrl, longUrl);
		}
		return false;
	}
	
	
}
