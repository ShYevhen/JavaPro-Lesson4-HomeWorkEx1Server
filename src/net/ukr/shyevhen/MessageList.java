package net.ukr.shyevhen;

import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageList {
	private final int LIMIT = 100;

	private final Gson gson;
	private final List<Message> list = new LinkedList<Message>();

	public MessageList() {
		gson = new GsonBuilder().create();
	}

	public synchronized void add(Message m) {
		if (list.size() + 1 == LIMIT) {
			list.remove(0);
		}
		list.add(m);
	}

	public synchronized String toJSON(int n) {
		if (n == list.size())
			return null;
		return gson.toJson(new JsonMessages(list, n));
	}
}
