package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import models.Activity;
import models.Fixtures;
import models.Location;
import models.User;

public class ActivityTest {

	// PacemakerAPI pacemaker = new
	// PacemakerAPI("https://morning-dusk-13146.herokuapp.com/");
	PacemakerAPI pacemaker = new PacemakerAPI("http://localhost:7000");
	User homer = new User("homer", "simpson", "homer@simpson.com", "secret");

	@Before
	public void setup() {
		pacemaker.deleteUsers();
		homer = pacemaker.createUser(homer.firstname, homer.lastname, homer.email, homer.password);
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testCreateActivity() {
		Activity activity = new Activity("walk", "shop", 2.5);

		Activity returnedActivity = pacemaker.createActivity(homer.id, activity.type, activity.location,
				activity.distance);
		assertEquals(activity.type, returnedActivity.type);
		assertEquals(activity.location, returnedActivity.location);
		assertEquals(activity.distance, returnedActivity.distance, 0.001);
		assertNotNull(returnedActivity.id);
	}

	@Test
	public void testGetActivity() {
		Activity activity = new Activity("run", "fridge", 0.5);
		Activity returnedActivity1 = pacemaker.createActivity(homer.id, activity.type, activity.location,
				activity.distance);
		assertNotNull(returnedActivity1);

		Activity returnedActivity2 = pacemaker.getActivity(homer.id, returnedActivity1.id);
		assertNotEquals(returnedActivity1, returnedActivity2);
	}
	
	@Test
	public void testGetActivities() {
		Activity activity = new Activity("run", "fridge", 0.5);
		Activity returnedActivity1 = pacemaker.createActivity(homer.id, activity.type, activity.location,
				activity.distance);
		assertNotNull(returnedActivity1);

		Collection<Activity> returnedActivity2 = pacemaker.getActivities(homer.id);
		assertNotNull(returnedActivity2);
	}
	
	@Test
	public void testListActivities() {
		Activity activity = new Activity("run", "fridge", 0.5);
		Activity returnedActivity1 = pacemaker.createActivity(homer.id, activity.type, activity.location,
				activity.distance);
		assertNotNull(returnedActivity1);

		Collection<Activity> returnedActivity2 = pacemaker.listActivities(homer.id, "run");
		assertNotNull(returnedActivity2);
	}
	
	@Test
	public void testListActivitiesFail() {
		Activity activity = new Activity("run", "fridge", 0.5);
		Activity returnedActivity1 = pacemaker.createActivity(homer.id, activity.type, activity.location,
				activity.distance);
		assertNotNull(returnedActivity1);

		Collection<Activity> returnedActivity2 = pacemaker.listActivities(null, "run");
		assertNull(returnedActivity2);
	}
	
	@Test
	public void testGetActivitiesFail() {
		Activity activity = new Activity("run", "fridge", 0.5);
		Activity returnedActivity1 = pacemaker.createActivity(homer.id, activity.type, activity.location,
				activity.distance);
		assertNotNull(returnedActivity1);

		Collection<Activity> returnedActivity2 = pacemaker.getActivities(null);
		assertNull(returnedActivity2);
	}

	@Test
	public void testGetActivityFail() {
		Activity activity = new Activity("run", "fridge", 0.5);
		Activity returnedActivity1 = pacemaker.createActivity(null, activity.type, activity.location,
				activity.distance);
		assertNull(returnedActivity1);

	}

	@Test
	public void testGetActivityReportByType() {
		Activity activity = new Activity("run", "fridge", 0.5);
		Activity returnedActivity1 = pacemaker.createActivity(homer.id, activity.type, activity.location,
				activity.distance);
		assertNotNull(returnedActivity1);
		List<Activity> returnedActivityList = (List<Activity>) pacemaker.listActivities(homer.id, "type");
		assertNotNull(returnedActivityList);
	}

	@Test
	public void testDeleteActivity() {
		Activity activity = new Activity("sprint", "pub", 4.5);
		Activity returnedActivity = pacemaker.createActivity(homer.id, activity.type, activity.location,
				activity.distance);
		assertNotNull(returnedActivity);
		pacemaker.deleteActivities(homer.id);
		returnedActivity = pacemaker.getActivity(homer.id, returnedActivity.id);
		assertNull(returnedActivity);
	}
	
	@Test
	public void testDeleteActivityFail() {
		Activity activity = new Activity("sprint", "pub", 4.5);
		Activity returnedActivity = pacemaker.createActivity(homer.id, activity.type, activity.location,
				activity.distance);
		assertNotNull(returnedActivity);
		pacemaker.deleteActivities(null);
		returnedActivity = pacemaker.getActivity(homer.id, returnedActivity.id);
		assertNull(returnedActivity);
	}

	@Test
	public void testCreateActivityWithSingleLocation() {
		pacemaker.deleteActivities(homer.id);
		Activity activity = new Activity("walk", "shop", 2.5);
		Location location = new Location(12.0, 33.0);

		Activity returnedActivity = pacemaker.createActivity(homer.id, activity.type, activity.location,
				activity.distance);
		pacemaker.addLocation(homer.id, returnedActivity.id, location.latitude, location.longitude);

		List<Location> locations = pacemaker.getLocations(homer.id, returnedActivity.id);
		assertEquals(locations.size(), 1);
		assertEquals(locations.get(0), location);
	}

	@Test
	public void testCreateActivityWithMultipleLocation() {
		pacemaker.deleteActivities(homer.id);
		Activity activity = new Activity("walk", "shop", 2.5);
		Activity returnedActivity = pacemaker.createActivity(homer.id, activity.type, activity.location,
				activity.distance);

		Fixtures.locations.forEach(location -> pacemaker.addLocation(homer.id, returnedActivity.id, location.latitude,
				location.longitude));
		List<Location> returnedLocations = pacemaker.getLocations(homer.id, returnedActivity.id);
		assertEquals(Fixtures.locations.size(), returnedLocations.size());
		assertEquals(Fixtures.locations, returnedLocations);
	}
	
	@Test
	public void getLocationFail() {
		pacemaker.deleteActivities(homer.id);
		Activity activity = new Activity("walk", "shop", 2.5);
		Location location = new Location(12.0, 33.0);

		Activity returnedActivity = pacemaker.createActivity(homer.id, activity.type, activity.location,
				activity.distance);
		pacemaker.addLocation(homer.id, returnedActivity.id, location.latitude, location.longitude);

		List<Location> locations = pacemaker.getLocations(null, returnedActivity.id);
		assertNull(locations);
	}
}