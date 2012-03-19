package nfrancois.mercureplus.component;

import nfrancois.mercureplus.model.mercureplus.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component("tweetMessageComponent")
public class TweetMessageComponent {
	
	private static final Log LOG = LogFactory.getLog(ActivityToMessageComponent.class);
	
	private static final String TWEET_CONTENT_SUSPENS = "... ";
	private static final String WHITESPACE = " ";
	private static final int TWEET_CONTENT_SIZE = 139;

	public String twitMessage(Message message) throws Exception {
		String twitterContent = twitterContent(message);
		LOG.info("TwitterMessage = "+twitterContent );
		return twitterContent;
	}
	
	private String twitterContent(Message message){
		String content = message.getContent();
		if(content.length() <= TWEET_CONTENT_SIZE && !message.isHasRichContent()){
			return content;
		} else {
			int remainingSize = TWEET_CONTENT_SIZE-message.getShortUrl().length()-WHITESPACE.length();
			if(content.length() <= remainingSize){
				return content.concat(WHITESPACE).concat(message.getShortUrl());
			} else {
				return message.getContent().substring(0, remainingSize-TWEET_CONTENT_SUSPENS.length()+1).concat(TWEET_CONTENT_SUSPENS).concat(message.getShortUrl());
			}
		}
		
	}

}
