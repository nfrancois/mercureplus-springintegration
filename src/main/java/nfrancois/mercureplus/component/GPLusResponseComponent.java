package nfrancois.mercureplus.component;

import java.util.List;

import nfrancois.mercureplus.model.gplus.Activity;
import nfrancois.mercureplus.model.gplus.ActivityFeed;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Component("gPLusResponseComponent")
public class GPLusResponseComponent  {
	
	private static final Log LOG = LogFactory.getLog(GPLusResponseComponent.class);
	
	private MongoDBService mongoDBService;
	
	public List<Activity> getActivities(ActivityFeed activityFeed) {
		final DateTime lastSyncho = mongoDBService.getLastSyncho();
		LOG.info("Last synchro="+lastSyncho);
		List<Activity> newActivities = Lists.newArrayList(Iterables.filter(activityFeed.getItems(), new LastActivitiesFilter(lastSyncho)));
		LOG.info(newActivities.size()+ " a twitter");
		if(!newActivities.isEmpty()){ // Mise a jour seulement si besoin
			boolean updateSyncho = mongoDBService.updateSyncho(activityFeed.getUpdated(), newActivities.size());
			if(!updateSyncho){ // Si fail l'aventure s'arrete
				newActivities.clear();
			}
		}
		return Lists.reverse(newActivities);
	}

	@Required
	@Autowired
	public void setMongoDBService(MongoDBService mongoDBService) {
		this.mongoDBService = mongoDBService;
	}
	
	
	private static class LastActivitiesFilter implements Predicate<Activity>  {
		
		public DateTime lastSynchronisation;

		public LastActivitiesFilter(DateTime lastSynchronisation) {
			this.lastSynchronisation = lastSynchronisation;
		}

		@Override
		public boolean apply(Activity activity) {
			return lastSynchronisation.isBefore(activity.getUpdated());
		}
		
	}

	
}
