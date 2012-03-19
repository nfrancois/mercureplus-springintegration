package nfrancois.mercureplus.component;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import nfrancois.mercureplus.component.model.CoreHelper;
import nfrancois.mercureplus.model.googl.GooGlObject;
import nfrancois.mercureplus.model.gplus.Activity;
import nfrancois.mercureplus.model.mercureplus.Message;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class ActivityToMessageComponentTest {
	
	ActivityToMessageComponent component = new ActivityToMessageComponent();
	@Mock
	RestTemplate restTemplate;
	
	@Before
	public void setUp() {
		component.setRestTemplate(restTemplate);
	}
	
	@SuppressWarnings("unchecked")
	private void mockGoogGl(String url, String shortUrl){
		ResponseEntity<GooGlObject> gooGlEntityResponse = mock(ResponseEntity.class);
		GooGlObject gooGlResponse = mock(GooGlObject.class);
		when(gooGlEntityResponse.getBody()).thenReturn(gooGlResponse);
		when(gooGlResponse.getShortUrl()).thenReturn(shortUrl);
		when(restTemplate.postForEntity(Matchers.argThat(CoreMatchers.equalTo(ActivityToMessageComponent.GOO_GL_URL_REQUEST)), //
											    Matchers.argThat(CoreMatchers.equalTo(new GooGlObject(url))), //
											    Matchers.argThat(CoreMatchers.equalTo(GooGlObject.class)))).thenReturn(gooGlEntityResponse);		
	}	

	/**
	 * Message tout simple et sans url.
	 */
	@Test
	public void simple_message() throws Exception{
		// Given
		Activity activity = CoreHelper.activityPost("Un petit message court", "http://uneurlpluslongue");
		
		mockGoogGl("http://uneurlpluslongue", "http://goo.gl/test");

		// When
		Message message = component.transform(activity);
		
		// Then
		assertThat(message).isNotNull();
		assertThat(message.getContent()).isEqualTo("Un petit message court");
		assertThat(message.getShortUrl()).isEqualTo("http://goo.gl/test");
		assertThat(message.isHasRichContent()).isFalse();
	}
	
	/**
	 * Message tout simple avec url.
	 */	
	@Test
	public void simple_message_with_url() throws Exception{
		// Given
		Activity activity = CoreHelper.activityPost("Un petit message court http://superblog.com", "http://uneurlpluslongue");

		mockGoogGl("http://uneurlpluslongue", "http://goo.gl/test");
		mockGoogGl("http://superblog.com", "http://goo.gl/test2");
		
		// When
		Message message = component.transform(activity);
		
		// Then
		assertThat(message).isNotNull();
		assertThat(message.getContent()).isEqualTo("Un petit message court http://goo.gl/test2");
		assertThat(message.getShortUrl()).isEqualTo("http://goo.gl/test");
		assertThat(message.isHasRichContent()).isFalse();
	}
	
	/**
	 * Quand je poste une photo avec un commentaire, le message doit contenir le texte et un lien vers le post.
	 */
	@Test
	public void simple_message_with_photo() throws Exception{
		// Given
		Activity activity = CoreHelper.activityPostWithAttachement("Un petit message court", "http://uneurlpluslongue", "photo", "", "http://lienverslaphoto");
		
		mockGoogGl("http://uneurlpluslongue", "http://goo.gl/test");
		mockGoogGl("http://lienverslaphoto", "http://goo.gl/test2");
		
		// When
		Message message = component.transform(activity);
		
		// Then
		assertThat(message).isNotNull();
		assertThat(message.getContent()).isEqualTo("Un petit message court");
		assertThat(message.getShortUrl()).isEqualTo("http://goo.gl/test");
		assertThat(message.isHasRichContent()).isTrue();
		
	}
	
	/**
	 * Message avec seulement une url, on donne le début de l'article avec un lien vers lui.
	 */
	@Test
	public void simple_message_with_just_uri() throws Exception{
		// Given
		Activity activity = CoreHelper.activityPostWithAttachement("<a href=\"http://superarticle\" >http://superarticle</a>", "http://uneurlpluslongue", "article", "Dans cet article nous allons parler de ...", "http://superarticle");

		mockGoogGl("http://uneurlpluslongue", "http://goo.gl/test");
		mockGoogGl("http://superarticle", "http://goo.gl/test2");
		
		// When
		Message message = component.transform(activity);
		
		// Then
		assertThat(message.getContent()).isEqualTo("Dans cet article nous allons parler de ...");
		assertThat(message.getShortUrl()).isEqualTo("http://goo.gl/test2");
		assertThat(message.isHasRichContent()).isTrue();		
		
	}
	
	/**
	 * Message qui donne un lien vers un article sans avoir mit de commentaire, on donne le début de l'article avec un lien vers lui.
	 */	
	@Test
	public void post_with_reader_without_comment() throws Exception{
		// Given
		Activity activity = CoreHelper.activityPostWithAttachement("", "http://uneurlpluslongue", "article", "Dans cet article nous allons parler de ...", "http://superarticle");

		mockGoogGl("http://uneurlpluslongue", "http://goo.gl/test");
		mockGoogGl("http://superarticle", "http://goo.gl/test2");
		
		// When
		Message message = component.transform(activity);
		
		// Then
		assertThat(message).isNotNull();
		assertThat(message.getContent()).isEqualTo("Dans cet article nous allons parler de ...");
		assertThat(message.getShortUrl()).isEqualTo("http://goo.gl/test2");
		assertThat(message.isHasRichContent()).isTrue();		
		
	}		
	
	/**
	 * Message qui donne un lien vers un article sans avec un commentaire, on donne le début de l'article avec un lien vers lui.
	 */	
	@Test
	public void post_with_reader_with_comment() throws Exception{
		// Given
		Activity activity = CoreHelper.activityPostWithAttachement("Intéressant", "http://uneurlpluslongue", "article", "Dans cet article nous allons parler de ...", "http://superarticle");

		mockGoogGl("http://uneurlpluslongue", "http://goo.gl/test");
		
		// When
		Message message = component.transform(activity);
		
		// Then
		assertThat(message).isNotNull();
		assertThat(message.getContent()).isEqualTo("Intéressant");
		assertThat(message.getShortUrl()).isEqualTo("http://goo.gl/test");
		assertThat(message.isHasRichContent()).isTrue();		
		
	}	
	
	
	/**
	 * Quand je poste une photo une vidéo, le message doit contenir le texte et un lien vers le post.
	 */	
	@Test
	public void simple_message_with_video() throws Exception{
		// Given
		Activity activity = CoreHelper.activityPostWithAttachement("Un petit message court", "http://uneurlpluslongue", "video", "", "http://lienverslavideo");
		
		mockGoogGl("http://uneurlpluslongue", "http://goo.gl/test");
		mockGoogGl("http://lienverslavideo", "http://goo.gl/test2");
		
		// When
		Message message = component.transform(activity);
		
		// Then
		assertThat(message).isNotNull();
		assertThat(message.getContent()).isEqualTo("Un petit message court");
		assertThat(message.getShortUrl()).isEqualTo("http://goo.gl/test");
		assertThat(message.isHasRichContent()).isTrue();		
		
	}	
	
	/**
	 * Quand je poste une album photo, le message doit contenir le texte et un lien vers le post.
	 */		
	@Test
	public void simple_message_with_album() throws Exception{
		// Given
		Activity activity = CoreHelper.activityPostWithAttachement("Un petit message court", "http://uneurlpluslongue", "photo-album", "", "http://lienversphotoalbum");
		
		mockGoogGl("http://uneurlpluslongue", "http://goo.gl/test");
		mockGoogGl("http://lienverslaphotoalbum", "http://goo.gl/test2");
		
		// When
		Message message = component.transform(activity);
		
		// Then
		assertThat(message).isNotNull();
		assertThat(message.getContent()).isEqualTo("Un petit message court");
		assertThat(message.getShortUrl()).isEqualTo("http://goo.gl/test");
		assertThat(message.isHasRichContent()).isTrue();		
		
	}	

	/**
	 * Quand je partage un post qui partage d'article sans que personne n'est ajouté de commentaire, alors début article + le vers article
	 */
	@Test
	public void reshare_a_share_without_comment() throws Exception{
		// Given
		Activity activity = CoreHelper.activityShare("", "http://uneurlpluslongue", "Dans cet article nous allons parler de ...", "http://superarticle");

		mockGoogGl("http://uneurlpluslongue", "http://goo.gl/test");
		mockGoogGl("http://superarticle", "http://goo.gl/test2");		

		// When
		Message message = component.transform(activity);
		
		// Then
		assertThat(message).isNotNull();
		assertThat(message.getContent()).isEqualTo("Dans cet article nous allons parler de ...");
		assertThat(message.getShortUrl()).isEqualTo("http://goo.gl/test2");
		assertThat(message.isHasRichContent()).isTrue();
		
	}
	
	/**
	 * Quand je partage un post qui partage un article en y ajoutant un commentaire, je met mon texte plus un lien vers le post
	 */
	@Test
	public void reshare_a_share_with_comment() throws Exception{
		// Given
		Activity activity = CoreHelper.activityShare("Intéressant !", "http://uneurlpluslongue", "Dans cet article nous allons parler de ...", "http://superarticle");
		activity.setAnnotation("C'est vraiment très intéressant");
		
		mockGoogGl("http://uneurlpluslongue", "http://goo.gl/test");
		
		// When
		Message message = component.transform(activity);
		
		// Then
		assertThat(message).isNotNull();
		assertThat(message.getContent()).isEqualTo("C'est vraiment très intéressant");
		assertThat(message.getShortUrl()).isEqualTo("http://goo.gl/test");
		assertThat(message.isHasRichContent()).isTrue();		
		
	}

	/**
	 * Quand je partage un post qui partage un article avec un commenentaire, sans que je l'ai commenté, je met le texte de la personnne et  lien vers le post
	 */	
	@Test
	public void reshare_a_commented_share() throws Exception{
		// Given
		Activity activity = CoreHelper.activityShare("Intéressant !", "http://uneurlpluslongue", "Dans cet article nous allons parler de ...", "http://superarticle");

		mockGoogGl("http://uneurlpluslongue", "http://goo.gl/test");
		
		// When
		Message message = component.transform(activity);
		
		// Then
		assertThat(message).isNotNull();
		assertThat(message.getContent()).isEqualTo("Intéressant !");
		assertThat(message.getShortUrl()).isEqualTo("http://goo.gl/test");
		assertThat(message.isHasRichContent()).isTrue();		
		
	}	

}
