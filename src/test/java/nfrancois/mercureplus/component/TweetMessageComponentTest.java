package nfrancois.mercureplus.component;

import static org.fest.assertions.Assertions.assertThat;
import nfrancois.mercureplus.model.mercureplus.Message;

import org.junit.Test;

public class TweetMessageComponentTest {
	
	
	public TweetMessageComponent component = new TweetMessageComponent();
	
	@Test
	public void shouldnt_be_change() throws Exception{
		// Given
		String content = "message court de moins de 140 caractères";
		Message message = new Message(content, "http://goo.gl/test", false);
		
		// When
		String twitMessage = component.twitMessage(message);
		
		// Then
		assertThat(twitMessage).isNotNull();
		assertThat(twitMessage.length()).isLessThan(140);
		assertThat(twitMessage).isEqualTo(content);
	}
	
	@Test
	public void should_just_add_a_short_link_when_rich_content() throws Exception{
		// Given
		String content = "message court de moins de 140 caractères";
		Message message = new Message(content, "http://goo.gl/test", true);
		
		// When
		String twitMessage = component.twitMessage(message);		
		
		// Then
		assertThat(twitMessage).isNotNull();
		assertThat(twitMessage.length()).isLessThan(140);
		assertThat(twitMessage).isEqualTo("message court de moins de 140 caractères http://goo.gl/test");
	}
	
	@Test
	public void should_trunk_content() throws Exception{
		// Given
		String content = "message de test qui contient suffisament de caractères pour le tronquer avec quelques points de suspension, par contre, il n'y a pas de contenu riche";
		Message message = new Message(content, "http://goo.gl/test", false);
		
		// When
		String twitMessage = component.twitMessage(message);		
		
		// Then
		assertThat(twitMessage).isNotNull();
		System.out.println(twitMessage.length());
		assertThat(twitMessage.length()).isEqualTo(139);
		assertThat(twitMessage).isEqualTo("message de test qui contient suffisament de caractères pour le tronquer avec quelques points de suspension, par contr... http://goo.gl/test");
	}	
	
	@Test
	public void should_trunk_content_with_rich_content() throws Exception{
		// Given
		String content = "message de test qui contient suffisament de caractères pour le tronquer avec quelques points de suspension, par contre, mais avec du contenu riche";
		Message message = new Message(content, "http://goo.gl/test", true);
		
		// When
		String twitMessage = component.twitMessage(message);		
		
		// Then
		assertThat(twitMessage).isNotNull();
		assertThat(twitMessage.length()).isEqualTo(139);
		assertThat(twitMessage).isEqualTo("message de test qui contient suffisament de caractères pour le tronquer avec quelques points de suspension, par contr... http://goo.gl/test");
	}
	

}
