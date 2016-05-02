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

public class DataDAOImpl implements DataDAO {

	@Override
	public boolean create(int lower, String x, String y, int t, int k) {
		
		if(x == null || y == null)
			return false;
		
		Connection conn = DBConnection.getConnection();
		String SQL = "insert into data(lower,x,y,t,k) values(?,?,?,?,?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, lower);
			pstmt.setString(2, x);
			pstmt.setString(3, y);
			pstmt.setInt(4, t);
			pstmt.setInt(5, k);
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

		if(lower == -1 && (startTime == null || endTime == null))
			return dataList;
		
		Connection conn = DBConnection.getConnection();
		StringBuilder SQL = new StringBuilder();
		SQL.append("select * from data where"
				+ (lower != -1 ? " lower = " + lower + " and" : "")
				+ ((startTime != null && endTime != null) ? " time >= \'" + startTime + "\' and time <= \'" + endTime + "\' and" : ""));
		SQL.delete(SQL.length() - 4, SQL.length());
		
		PreparedStatement pstmt = null;
		Data data = null;
		
		try {
			pstmt = conn.prepareStatement(SQL.toString());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				data = new Data();
				data.setId(rs.getInt(1));
				data.setLower(rs.getInt(2));
				data.setX(rs.getString(3));
				data.setY(rs.getString(4));
				data.setT(rs.getInt(5));
				data.setK(rs.getInt(6));
				String time = rs.getString(7);
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
