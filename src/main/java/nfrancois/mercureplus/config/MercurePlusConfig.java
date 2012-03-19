package nfrancois.mercureplus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
@ImportResource("classpath:mercureplus-integration.xml")
@ComponentScan("nfrancois.mercureplus.component" )
public class MercurePlusConfig {

	@Value("${twitter.consumerKey}")
	private String consumerKey;

	@Value("${twitter.consumerSecret}")
	private String consumerSecret;

	@Value("${twitter.oauthToken}")
	private String accessToken;

	@Value("${twitter.oauthSecret}")
	private String accessTokenSecret;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public TwitterTemplate twitterTemplate() {
		return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}

	@Bean
	public PropertyPlaceholderConfigurer properties() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] {new ClassPathResource(propertiesPath()) };
		ppc.setLocations(resources);
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}

	protected String propertiesPath() {
		return "mercureplus.properties";
	}

}
