package nfrancois.mercureplus.component;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import nfrancois.mercureplus.model.gplus.Activity;
import nfrancois.mercureplus.model.gplus.ActivityFeed;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class GPLusResponseComponentTest {
	
	private final GPLusResponseComponent component = new GPLusResponseComponent();
	@Mock
	private MongoDBService mongoDBService;
	
	@Before
	public void setUp() {
		component.setMongoDBService(mongoDBService);
	}
	
	@Test 
	public void should_get_activities_and_update_to_mongo(){
		// Given
		when(mongoDBService.getLastSyncho()).thenReturn(new DateTime(2011, 1, 1, 0, 0, 0, 0));
		
		ActivityFeed activityFeed = new ActivityFeed();
		activityFeed.setUpdated(new DateTime(2011, 2, 2, 0, 0, 0, 0));

		Activity activity1 = new Activity();
		activity1.setId("recent");
		activity1.setUpdated(new DateTime(2011, 2, 1, 0, 0, 0, 0));

		Activity activity2 = new Activity();
		activity2.setId("moins récent");
		activity2.setUpdated(new DateTime(2011, 1, 1, 1, 0, 0, 0));

		Activity activity3 = new Activity();
		activity3.setId("ancien");
		activity3.setUpdated(new DateTime(2010, 12, 25, 0, 0, 0, 0));
		
		activityFeed.setItems(Lists.newArrayList(activity1, activity2, activity3));
		when(mongoDBService.updateSyncho(activityFeed.getUpdated(), 2)).thenReturn(true);
		
		// When
		List<Activity> activities = component.getActivities(activityFeed);
		
		// Then
		verify(mongoDBService).getLastSyncho();
		verify(mongoDBService).updateSyncho(activityFeed.getUpdated(), 2);
		
		assertThat(activities).isNotNull().hasSize(2);
		assertThat(activities).onProperty("id").containsExactly("moins récent", "recent");
	}
	
	@Test 
	public void should_get_activities_but_fail_to_update_last_synchro(){
		// Given
		when(mongoDBService.getLastSyncho()).thenReturn(new DateTime(2011,1,1,0,0,0,0));
		ActivityFeed activityFeed = new ActivityFeed();
		activityFeed.setUpdated(new DateTime(2011, 2, 2, 0, 0, 0, 0));
		Activity activity1 = new Activity();
		activity1.setId("recent");
		activity1.setUpdated(new DateTime(2011,2,1,0,0,0,0));
		Activity activity2 = new Activity();
		activity2.setId("ancien");
		activity2.setUpdated(new DateTime(2010,12,31,0,0,0,0));
		Activity activity3 = new Activity();
		activity3.setId("encore plus ancien");
		activity3.setUpdated(new DateTime(2010,12,25,0,0,0,0));		
		activityFeed.setItems(Lists.newArrayList(activity1, activity2, activity3));
		when(mongoDBService.updateSyncho(activityFeed.getUpdated(), 1)).thenReturn(false);
		
		// When
		List<Activity> activities = component.getActivities(activityFeed);
		
		// Then
		verify(mongoDBService).getLastSyncho();
		verify(mongoDBService).updateSyncho(activityFeed.getUpdated(), 1);
		Assert.assertNotNull(activities);
		Assert.assertTrue(activities.isEmpty());
	}	

	@Test 
	public void should_got_nothing_to_publish(){
		// Given
		when(mongoDBService.getLastSyncho()).thenReturn(new DateTime(2011,5,1,0,0,0,0));
		ActivityFeed activityFeed = new ActivityFeed();
		activityFeed.setUpdated(new DateTime(2011, 2, 2, 0, 0, 0, 0));
		Activity activity1 = new Activity();
		activity1.setId("recent");
		activity1.setUpdated(new DateTime(2011,2,1,0,0,0,0));
		Activity activity2 = new Activity();
		activity2.setId("ancien");
		activity2.setUpdated(new DateTime(2010,12,31,0,0,0,0));
		Activity activity3 = new Activity();
		activity3.setId("encore plus ancien");
		activity3.setUpdated(new DateTime(2010,12,25,0,0,0,0));		
		activityFeed.setItems(Lists.newArrayList(activity1, activity2, activity3));
		
		// When
		List<Activity> activities = component.getActivities(activityFeed);
		
		// Then
		verify(mongoDBService).getLastSyncho();
		verifyNoMoreInteractions(mongoDBService);
		assertThat(activities).isNotNull().isEmpty();
	}
	
}
