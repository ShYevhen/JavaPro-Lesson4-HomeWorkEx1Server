package net.ukr.shyevhen;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UsersList usrList = UsersList.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String fromStr = req.getParameter("from");
		String login = req.getParameter("login");
		usrList.getStatus().put(login, new Date());
		int from = 0;
		try {
			from = Integer.parseInt(fromStr);
		} catch (Exception ex) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		String json = usrList.getMesList(login).toJSON(from);
		if (json != null) {
			OutputStream os = resp.getOutputStream();
			byte[] buf = json.getBytes(StandardCharsets.UTF_8);
			os.write(buf);
		}
	}
}
