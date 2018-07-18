package net.ukr.shyevhen;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	UsersList usrList = UsersList.getInstance();

	@Override
	public void init() throws ServletException {
		usrList.addUser("admin", "admin");
		usrList.addUser("Alladin", "123456");
		usrList.addUser("Lion", "lionkiller");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String registration = req.getParameter("registration");
		byte[] buf = ("author-BAD").getBytes(StandardCharsets.UTF_8);
		if (registration.equals("no")) {
			if (usrList.getUsers().containsKey(login) && usrList.getUsers().get(login).equals(password)) {
				buf = ("author-OK").getBytes(StandardCharsets.UTF_8);
			}
		} else if (!usrList.getUsers().containsKey(login)) {
			usrList.addUser(login, password);
			buf = ("registr-OK").getBytes(StandardCharsets.UTF_8);
		}
		OutputStream os = resp.getOutputStream();
		os.write(buf);
	}

}
