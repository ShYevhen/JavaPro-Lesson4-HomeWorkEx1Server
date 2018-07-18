package net.ukr.shyevhen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/help")
public class HelpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String, String> help = new HashMap<>();

	@Override
	public void init() throws ServletException {
		help.put("-r", "registration new user");
		help.put("-help", "get list all commands");
		help.put("-exit", "stop chat program");
		help.put("-chr", "go to the chat-rooms menu");
		help.put("-chr:new", "create a new chat-room");
		help.put("-chr:add", "add new user in the chat-room");
		help.put("-chr:exit", "leave chat-room");
		help.put("-chr:users", "get list all users in the chat-room");
		help.put("-chr:list", "get list all your chat-rooms");

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder("HELP").append(System.lineSeparator());
		for (String command : help.keySet()) {
			sb.append(command + "\t" + help.get(command)).append(System.lineSeparator());
		}
		resp.getOutputStream().write(sb.toString().getBytes(StandardCharsets.UTF_8));
	}

}
