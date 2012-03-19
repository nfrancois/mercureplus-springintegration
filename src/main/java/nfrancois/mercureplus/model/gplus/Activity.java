package nfrancois.mercureplus.model.gplus;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {

	private String placeName;
	private String kind;
	private DateTime updated;
	private ActivityProvider provider;
	private String title;
	private String url;
	private ActivityObject object;
	private String placeId;
	private ActivityActor actor;
	private String id;
	private Acl access;
	private String verb;
	private String geocode;
	private String radius;
	private String address;
	private String crosspostSource;
	private Boolean placeholder;
	private String annotation;
	private DateTime published;

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public DateTime getUpdated() {
		return updated;
	}

	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getGeocode() {
		return geocode;
	}

	public void setGeocode(String geocode) {
		this.geocode = geocode;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCrosspostSource() {
		return crosspostSource;
	}

	public void setCrosspostSource(String crosspostSource) {
		this.crosspostSource = crosspostSource;
	}

	public Boolean getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(Boolean placeholder) {
		this.placeholder = placeholder;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public DateTime getPublished() {
		return published;
	}

	public void setPublished(DateTime published) {
		this.published = published;
	}

	public ActivityActor getActor() {
		return actor;
	}

	public void setActor(ActivityActor actor) {
		this.actor = actor;
	}

	public ActivityObject getObject() {
		return object;
	}

	public void setObject(ActivityObject object) {
		this.object = object;
	}

	public ActivityProvider getProvider() {
		return provider;
	}

	public void setProvider(ActivityProvider provider) {
		this.provider = provider;
	}

	public Acl getAccess() {
		return access;
	}

	public void setAccess(Acl access) {
		this.access = access;
	}

}
