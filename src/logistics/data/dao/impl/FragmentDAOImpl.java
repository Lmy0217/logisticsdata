package logistics.data.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logistics.data.dao.FragmentDAO;
import logistics.data.util.DBConnection;

/**
 * 碎片持久化类, 实现碎片持久化接口
 * 
 * @author myluo(lmy0217@126.com)
 * @version 1605
 */
public class FragmentDAOImpl implements FragmentDAO {

	@Override
	public boolean create(String id, String value) {

		if (id == null || value == null)
			return false;

		Connection conn = DBConnection.getConnection();
		String SQL = "insert into fragment(id,value) values(?,?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			pstmt.setString(2, value);
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
	public boolean delete(String id) {

		if (id == null)
			return false;
		;

		Connection conn = DBConnection.getConnection();
		String SQL = "delete from fragment where id = ?";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
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
	public String getValue(String id) {

		if (id == null)
			return null;

		Connection conn = DBConnection.getConnection();
		String SQL = "select value from fragment where id = ?";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getString(1);
			}

		} catch (SQLException e) {
			System.out.println(e);
			return null;

		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);

		}

		return null;
	}

}
