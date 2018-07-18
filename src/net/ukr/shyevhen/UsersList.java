package net.ukr.shyevhen;

import java.util.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UsersList {
	private static final UsersList usrList = new UsersList();
	private static Map<String, String> users = Collections.synchronizedMap(new HashMap<>());
	private static Map<String, MessageList> msgList = Collections.synchronizedMap(new HashMap<>());
	private static Map<String, Date> status = Collections.synchronizedMap(new HashMap<>());
	private static Map<String, List<String>> chatRooms = Collections.synchronizedMap(new HashMap<>());

	private UsersList() {
		super();
	}

	public static UsersList getInstance() {
		return usrList;
	}

	public Map<String, String> getUsers() {
		return users;
	}

	public Set<String> getUsersList() {
		return users.keySet();
	}

	public Map<String, MessageList> getMesLists() {
		return msgList;
	}

	public MessageList getMesList(String login) {
		return msgList.get(login);
	}

	public void addUser(String login, String password) {
		users.put(login, password);
		msgList.put(login, new MessageList());
		status.put(login, new Date());
	}

	public Map<String, Date> getStatus() {
		return status;
	}

	public Map<String, List<String>> getChatRooms() {
		return chatRooms;
	}

}
