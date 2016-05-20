package logistics.data.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logistics.data.bean.Data;
import logistics.data.dao.DataDAO;
import logistics.data.util.DBConnection;

/**
 * 数据持久化类, 实现数据持久化接口
 * 
 * @author myluo(lmy0217@126.com)
 * @version 1605
 */
public class DataDAOImpl implements DataDAO {

	@Override
	public boolean create(Data data) {

		if (data == null)
			return false;

		Connection conn = DBConnection.getConnection();
		String SQL = "insert into data(lower,x,y,t,kx,ky,kz,power,alarm,time) values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, data.getLower());
			pstmt.setString(2, data.getX());
			pstmt.setString(3, data.getY());
			pstmt.setDouble(4, data.getT());
			pstmt.setDouble(5, data.getKx());
			pstmt.setDouble(6, data.getKy());
			pstmt.setDouble(7, data.getKz());
			pstmt.setInt(8, data.getPower());
			pstmt.setInt(9, data.getAlarm());
			pstmt.setString(10, data.getTime());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e);
			return false;

		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);

		}

		return true;
	}

	@Override
	public List<Data> get(int lower, String startTime, String endTime) {

		List<Data> dataList = new ArrayList<Data>();

		if (lower == -1 && (startTime == null || endTime == null))
			return dataList;

		Connection conn = DBConnection.getConnection();
		StringBuilder SQL = new StringBuilder();
		SQL.append("select * from data where"
				+ (lower != -1 ? " lower = " + lower + " and" : "")
				+ ((startTime != null && endTime != null) ? " time > \'"
						+ startTime + "\' and time <= \'" + endTime + "\' and"
						: ""));
		SQL.delete(SQL.length() - 4, SQL.length());

		PreparedStatement pstmt = null;
		Data data = null;

		try {
			pstmt = conn.prepareStatement(SQL.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				data = new Data();
				data.setId(rs.getInt(1));
				data.setLower(rs.getString(2));
				data.setX(rs.getString(3));
				data.setY(rs.getString(4));
				data.setT(rs.getDouble(5));
				data.setKx(rs.getDouble(6));
				data.setKy(rs.getDouble(7));
				data.setKz(rs.getDouble(8));
				data.setPower(rs.getInt(9));
				data.setAlarm(rs.getInt(10));
				String time = rs.getString(11);
				data.setTime(time.substring(0, time.length() - 2));
				dataList.add(data);
			}
		} catch (SQLException e) {
			System.out.println(e);

		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

		return dataList;
	}

}
