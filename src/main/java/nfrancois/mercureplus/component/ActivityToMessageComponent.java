package nfrancois.mercureplus.component;

import java.util.Map;
import java.util.Set;

import nfrancois.mercureplus.model.googl.GooGlObject;
import nfrancois.mercureplus.model.gplus.Activity;
import nfrancois.mercureplus.model.gplus.ActivityObject;
import nfrancois.mercureplus.model.mercureplus.Message;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Component("activityToMessageComponent")
public class ActivityToMessageComponent {
	
//	private static final Log LOG = LogFactory.getLog(ActivityToMessageComponent.class);

	public static final String GOO_GL_URL_REQUEST = "https://www.googleapis.com/urlshortener/v1/url";
	
	private static final String SHARE_VERB = "share";
	private static final String POST_VERB = "post";
	
	// TODO utiliser un enum
	private static final String ARTICLE_ATTACHEMENT_TYPE = "article";
	private static final String PHOTO_ATTACHEMENT_TYPE = "photo";
	private static final String ALBUM_ATTACHEMENT_TYPE = "photo-album";
	private static final String VIDEO_ATTACHEMENT_TYPE = "video";
	private static final Set<String > RICH_CONTENT = Sets.newHashSet(ALBUM_ATTACHEMENT_TYPE, PHOTO_ATTACHEMENT_TYPE, VIDEO_ATTACHEMENT_TYPE);
	
	private RestTemplate restTemplate;
	
	public Message transform(Activity activity) throws Exception {
		String longUrl = activity.getUrl();
		ActivityObject activityObject = activity.getObject();
		String content = searchAndShortenizeUrl(cleanHtmlSymbols(getOriginContent(activity)));
		String shortUrl = null;
		boolean hasUrlInArticleAttachment = hasUrlInArticleAttachment(activityObject);
		if((POST_VERB.equals(activity.getVerb()) &&  hasUrlInArticleAttachment && (Strings.isNullOrEmpty(activityObject.getContent())) || isUrlContent(activityObject)) || //
				(SHARE_VERB.equals(activity.getVerb()) && Strings.isNullOrEmpty(activity.getAnnotation()) && hasUrlInArticleAttachment && //
						Strings.isNullOrEmpty(activityObject.getContent()) && Strings.isNullOrEmpty(activity.getAnnotation()) && Strings.isNullOrEmpty(activityObject.getContent())) 
				){
			shortUrl = shortenizeUrl(activityObject.getAttachments().get(0).getUrl());
		} else {
			shortUrl = shortenizeUrl(longUrl);
		}
		return new Message(content,shortUrl, hasUrlInArticleAttachment || hasRichContent(activity));
	}

	private String shortenizeUrl(String longUrl){
		ResponseEntity<GooGlObject> googlResponse = restTemplate.postForEntity(GOO_GL_URL_REQUEST, new GooGlObject(longUrl), GooGlObject.class);
		return googlResponse.getBody().getShortUrl();		
	}
	
	private boolean hasUrlInArticleAttachment(ActivityObject activityObject){
		return activityObject.getAttachments()!= null && !activityObject.getAttachments().isEmpty() && //
				ARTICLE_ATTACHEMENT_TYPE.equals(activityObject.getAttachments().get(0).getObjectType()) //
				&& !Strings.isNullOrEmpty(activityObject.getAttachments().get(0).getUrl());
	}
	
//	private boolean hasNoContentInArticleAttachment(ActivityObject activityObject){
//		return activityObject.getAttachments()!= null && !activityObject.getAttachments().isEmpty() && //
//				ARTICLE_ATTACHEMENT_TYPE.equals(activityObject.getAttachments().get(0).getObjectType()) //
//				&& Strings.isNullOrEmpty(activityObject.getAttachments().get(0).getContent());
//	}
	
	private String getOriginContent(Activity activity) {
		// Dans un partage s'il existe une annation, c'est ça le texte
		if(SHARE_VERB.equals(activity.getVerb())){
			if(!Strings.isNullOrEmpty(activity.getAnnotation())){
				return activity.getAnnotation();
			}
		}
		ActivityObject activityObject = activity.getObject();
		String originContent = activityObject.getContent();
		if((Strings.isNullOrEmpty(originContent) && !CollectionUtils.isEmpty(activityObject.getAttachments()) && ARTICLE_ATTACHEMENT_TYPE.equals(activityObject.getAttachments().get(0).getObjectType())) || //
				isUrlContent(activityObject) //
				){
			// Cas de reshare d'un lien sans que personne n'est mit de commentaire
			originContent = activityObject.getAttachments().get(0).getContent();
		} 
		return originContent;
	}
	
	private boolean isUrlContent(ActivityObject activityObject){
		String content = activityObject.getContent();
		return content.startsWith("<a") && content.endsWith("</a>");
	}
	
	private String cleanHtmlSymbols(String content){
		return Jsoup.parse(content).text();
	}
	
	private boolean hasRichContent(Activity activity){
		if(SHARE_VERB.equals(activity.getVerb())){
			return true;
		}
		ActivityObject activityObject = activity.getObject();
		if(!CollectionUtils.isEmpty(activityObject.getAttachments())){
			return RICH_CONTENT.contains(activityObject.getAttachments().get(0).getObjectType()); 
		}
		return false;
	}
	
	private String searchAndShortenizeUrl(String content){
		Iterable<String> words = Splitter.on(' ').split(content);
		Iterable<String> urls = Iterables.filter(words, urlPredicate);
		Map<String, String> urlsMap = Maps.uniqueIndex(urls, shortUrlFunction);
		for(Map.Entry<String, String> entry : urlsMap.entrySet()){
			content = content.replaceAll(entry.getValue(), entry.getKey());
		}
		return content;
	}
	
	private final Predicate<String> urlPredicate = new Predicate<String>(){

		@Override
		public boolean apply(String word) {
			return word.startsWith("http://") || word.startsWith("https://");
		}
		
	};
	
	private final Function<String, String> shortUrlFunction = new Function<String, String>() {

		@Override
		public String apply(String longUrl) {
			return shortenizeUrl(longUrl);
		}
	};	
	
	
	@Required
	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
