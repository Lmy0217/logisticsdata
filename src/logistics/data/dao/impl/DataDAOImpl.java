package logistics.data.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	public Data get(int lower, String startTime, String endTime) {
		// TODO 自动生成的方法存根
		return null;
	}

}
