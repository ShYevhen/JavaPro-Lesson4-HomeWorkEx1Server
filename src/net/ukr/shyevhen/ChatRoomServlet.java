package net.ukr.shyevhen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chatroom")
public class ChatRoomServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UsersList usrList = UsersList.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String com = req.getParameter("command");
		String login = req.getParameter("login");
		if (com.equals("-chr:new") || com.equals("-chr:exit") || com.equals("-chr:users")) {
			newExitUser(req, resp, com, login);
			return;
		} else if (com.equals("-chr:add")) {
			String name = req.getParameter("name");
			String user = req.getParameter("user");
			if (usrList.getChatRooms().containsKey(name) && usrList.getChatRooms().get(name).contains(login)
					&& usrList.getUsersList().contains(user) && !usrList.getChatRooms().get(name).contains(user)) {
				usrList.getChatRooms().get(name).add(user);
				resp.getOutputStream().write("User added".getBytes(StandardCharsets.UTF_8));
				return;
			}
		} else if (com.equals("-chr:list")) {
			Map<String, List<String>> chr = usrList.getChatRooms();
			StringBuilder sb = new StringBuilder("Chat-room list:").append(System.lineSeparator());
			for (String key : chr.keySet()) {
				if (chr.get(key).contains(login)) {
					sb.append(key).append(System.lineSeparator());
				}
			}
			resp.getOutputStream().write(sb.toString().getBytes(StandardCharsets.UTF_8));
			return;
		}
		resp.getOutputStream().write("Wrong data".getBytes(StandardCharsets.UTF_8));
	}

	private void newExitUser(HttpServletRequest req, HttpServletResponse resp, String com, String login)
			throws IOException {
		String name = req.getParameter("name");
		if (com.equals("-chr:new") && !usrList.getChatRooms().containsKey(name)) {
			List<String> list = new ArrayList<>();
			list.add(login);
			usrList.getChatRooms().put(name, list);
			resp.getOutputStream().write(("Chat-room " + name + " created").getBytes(StandardCharsets.UTF_8));
		} else if (com.equals("-chr:exit") && usrList.getChatRooms().containsKey(name)
				&& usrList.getChatRooms().get(name).contains(login)) {
			usrList.getChatRooms().get(name).remove(login);
			resp.getOutputStream().write(("You left the " + name + " chat-room").getBytes(StandardCharsets.UTF_8));
		} else if (com.equals("-chr:users") && usrList.getChatRooms().containsKey(name)
				&& usrList.getChatRooms().get(name).contains(login)) {
			StringBuilder sb = new StringBuilder();
			int count = 1;
			for (String user : usrList.getChatRooms().get(name)) {
				sb.append(user)
						.append((new Date().getTime() - usrList.getStatus().get(user).getTime()) > 3000 ? " offline"
								: " online")
						.append(count % 4 == 0 ? System.lineSeparator() : "\t");
				count += 1;
			}
			resp.getOutputStream().write(sb.toString().getBytes(StandardCharsets.UTF_8));
		}
	}

}
