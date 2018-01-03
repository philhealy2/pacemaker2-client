package controllers;

import static org.junit.Assert.*;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import models.User;
import static models.Fixtures.users;

public class FriendTest {

	PacemakerAPI pacemaker = new PacemakerAPI("http://localhost:7000");
	User homer = new User("homer", "simpson", "homer@simpson.com", "secret");
	User lisa = new User("lisa", "simpson", "lisa@simpson.com", "secret");

	@Before
	public void setup() {
		pacemaker.deleteUsers();
	}

	@After
	public void tearDown() {
		pacemaker.deleteUsers();
	}

	@Test
	public void testFollowFriend() {
		User user = pacemaker.createUser(homer.firstname, homer.lastname, homer.email, homer.password);
		User user2 = pacemaker.createUser(lisa.firstname, lisa.lastname, lisa.email, lisa.password);

		User friend = pacemaker.followFriend(user.id, user2.email);
		assertNotNull(friend);

	}

	@Test
	public void testListFriends() {
		User user = pacemaker.createUser(homer.firstname, homer.lastname, homer.email, homer.password);
		User user2 = pacemaker.createUser(lisa.firstname, lisa.lastname, lisa.email, lisa.password);

		User friend = pacemaker.followFriend(user.id, user2.email);
		assertNotNull(friend);

		List<User> friendList = pacemaker.listFriends(user.id);
		assertNotNull(friendList);

	}

	@Test
	public void testUnfollowFriend() {
		User user = pacemaker.createUser(homer.firstname, homer.lastname, homer.email, homer.password);
		User user2 = pacemaker.createUser(lisa.firstname, lisa.lastname, lisa.email, lisa.password);

		User friend = pacemaker.followFriend(user.id, user2.email);
		assertNotNull(friend);

		User unfollowFriend = pacemaker.deleteFriend(user.id, user2.email);
		assertNotNull(unfollowFriend);

	}

	@Test
	public void testMessageFriend() {
		User user = pacemaker.createUser(homer.firstname, homer.lastname, homer.email, homer.password);
		User user2 = pacemaker.createUser(lisa.firstname, lisa.lastname, lisa.email, lisa.password);

		User friend = pacemaker.followFriend(user.id, user2.email);
		assertNotNull(friend);

		List<String> msgs = pacemaker.messageFriend(user2.email, "Test message to friend");
		assertEquals(msgs.get(0), "Test message to friend");
		
		List<String> msgs2 = pacemaker.messageFriend(user2.email, "Test message 2 to friend");
		assertNotNull(msgs2);
		
	}
	
	@Test
	public void testListMessages() {
		User user = pacemaker.createUser(homer.firstname, homer.lastname, homer.email, homer.password);
		User user2 = pacemaker.createUser(lisa.firstname, lisa.lastname, lisa.email, lisa.password);

		User friend = pacemaker.followFriend(user.id, user2.email);
		assertNotNull(friend);

		List<String> msgs = pacemaker.messageFriend(user.email, "Test message to friend");
		assertEquals(msgs.get(0), "Test message to friend");
		
		List<String> msgs2 = pacemaker.messageFriend(user.email, "Test message 2 to friend");
		assertNotNull(msgs2);
		
		List<String> msgs3 = pacemaker.listMessages(user.id);
		assertEquals(msgs3.get(1), "Test message 2 to friend");
		
				
		
	}
}