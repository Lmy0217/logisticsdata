package logistics.data.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logistics.data.bean.Data;
import logistics.data.util.ServiceFactory;

public class GetPost extends HttpServlet {

	private static final long serialVersionUID = 9130215096845172760L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doGet");
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");

		List<Data> dataList = ServiceFactory.getDataService().get(-1,
				startTime, endTime);

		StringBuilder sb = new StringBuilder();

		sb.append("[");
		for (Data data : dataList) {
			sb.append("[" + data.getLower() + ",\"" + data.getX() + "\",\""
					+ data.getY() + "\"," + data.getT() + "," + data.getKx()
					+ "," + data.getKy() + "," + data.getKz() + ","
					+ data.getPower() + "," + data.getAlarm() + ",\""
					+ data.getTime() + "\"" + "],");
		}
		if (dataList.size() > 0)
			sb.delete(sb.length() - 1, sb.length());
		sb.append("]");

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=utf-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");

		resp.getWriter().print(sb);
		System.out.println(sb);
	}

}
