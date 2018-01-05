package controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PacemakerConsoleServiceTest {
	
	PacemakerConsoleService service = new PacemakerConsoleService();

	@Before
	public void setup() {
		service.deleteUsers();
	}

	@After
	public void tearDown() {
		service.deleteUsers();
	}

	@Test
	public void testRegister() {
		
		//Register, Login and test first user
		service.register("Test", "Test", "test@test.com", "password");
		service.register("Test2", "Test2", "test2@test.com", "password");	
		service.login("test@test.com", "password");
		service.listUsers();
		service.addActivity("run", "waterford", 10.0);
		service.addActivity("cycle", "waterford", 20.0);
		service.addActivity("cycle", "waterford", 30.0);
		service.listActivities();
		service.follow("test2@test.com");
		service.listFriends();
		service.friendActivityReport("test@test.com");
		service.activityReport();
		service.listActivities();
		service.activityReport("cycle");
		service.messageFriend("test2@test.com", "test message");
		service.listMessages();
		service.logout();
		
		//Test second user
		service.login("test2@test.com", "password");
		service.listUsers();
		service.follow("test@test.com");
		service.listFriends();
		service.distanceLeaderBoard();
		service.listMessages();
	}

}
