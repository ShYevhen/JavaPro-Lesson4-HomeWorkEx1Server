package net.ukr.shyevhen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UsersList usrList = UsersList.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		byte[] buf = requestBodyToArray(req);
		String bufStr = new String(buf, StandardCharsets.UTF_8);

		Message msg = Message.fromJSON(bufStr);
		if (msg != null && msg.getTo().equals("All")) {
			Map<String, MessageList> map = usrList.getMesLists();
			for (String key : map.keySet()) {
				map.get(key).add(msg);
			}
		} else if (msg != null && msg.getTo().endsWith("-chr")
				&& usrList.getChatRooms().get(msg.getTo()).contains(msg.getFrom())) {
			List<String> users = usrList.getChatRooms().get(msg.getTo());
			for (String name : users) {
				usrList.getMesList(name).add(msg);
			}
		} else if (msg != null && usrList.getUsers().containsKey(msg.getTo())) {
			usrList.getMesList(msg.getTo()).add(msg);
			usrList.getMesList(msg.getFrom()).add(msg);
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String online = req.getParameter("online");
		StringBuilder sb = new StringBuilder();
		if (online.equals("no")) {
			int count = 1;
			for (String login : usrList.getUsersList()) {
				sb.append(login)
						.append((new Date().getTime() - usrList.getStatus().get(login).getTime()) > 3000 ? " offline"
								: " online")
						.append(count % 4 == 0 ? System.lineSeparator() : "\t");
				count += 1;
			}
		} else {
			int count = 1;
			for (String login : usrList.getUsersList()) {
				if ((new Date().getTime() - usrList.getStatus().get(login).getTime()) < 3000) {
					sb.append(login).append(count % 4 == 0 ? System.lineSeparator() : "\t");
				}
				count += 1;
			}
		}
		resp.getOutputStream().write(sb.toString().getBytes(StandardCharsets.UTF_8));
	}

	private byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
		InputStream is = req.getInputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[10240];
		int r;

		do {
			r = is.read(buf);
			if (r > 0)
				bos.write(buf, 0, r);
		} while (r != -1);

		return bos.toByteArray();
	}
}
