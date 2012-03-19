package nfrancois.mercureplus.component.model;

import nfrancois.mercureplus.model.gplus.Activity;
import nfrancois.mercureplus.model.gplus.ActivityObject;
import nfrancois.mercureplus.model.gplus.ActivityObjectAttachments;

import com.google.common.collect.Lists;

public class CoreHelper {

	public static Activity activityPost(String content, String url) {
		Activity activity = new Activity();
		activity.setVerb("post");
		ActivityObject activityObject = new ActivityObject();
		activityObject.setContent(content);
		activity.setObject(activityObject);
		activity.setUrl(url);
		return activity;
	}

	public static Activity activityPostWithAttachement(String content, String url, String attachementType, String shareContent, String attachementUrl) {
		Activity activity = activityPost(content, url);
		ActivityObjectAttachments activityObjectAttachments = new ActivityObjectAttachments();
		activityObjectAttachments.setObjectType(attachementType);
		activityObjectAttachments.setUrl(attachementUrl);
		activityObjectAttachments.setContent(shareContent);
		activity.getObject().setAttachments(Lists.newArrayList(activityObjectAttachments));
		return activity;
	}

	public static Activity activityShare(String content, String url, String shareContent, String sharedUrl) {
		Activity activity = activityPostWithAttachement(content, url, "article", shareContent, sharedUrl);
		activity.setVerb("share");
		return activity;
	}

}
