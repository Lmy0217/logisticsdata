package logistics.data.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * ���ݿ�����������
 * 
 */
public class DBConnection {

	/** ���������� */
	private static final String DBDRIVER = "com.mysql.jdbc.Driver";

	/** ����URL */
	private static final String DBURL = "jdbc:mysql://182.254.210.110:3306/data?useUnicode=true&characterEncoding=GBK&useSSL=true";

	/** ���ݿ��û��� */
	private static final String DBUSER = "root";

	/** ���ݿ����� */
	private static final String DBPASSWORD = "Lmy419608";

	/**
	 * ������ݿ�����
	 * 
	 * @return ���ݿ�����
	 */
	public static Connection getConnection() {

		/** ����һ�����Ӷ��� */
		Connection conn = null;

		try {

			/** ע������ */
			Class.forName(DBDRIVER);

			/** ������Ӷ��� */
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return conn;
	}

	/**
	 * �ر����Ӷ���
	 * 
	 * @param conn
	 *            ���Ӷ���
	 */
	public static void close(Connection conn) {

		/** ������Ӷ���Ϊ�� */
		if (conn != null) {

			try {

				/** �ر����Ӷ��� */
				conn.close();

			} catch (SQLException e) {

				e.printStackTrace();

			}
		}
	}

	/**
	 * �ر�Ԥ�������
	 * 
	 * @param pstmt
	 *            Ԥ�������
	 */
	public static void close(PreparedStatement pstmt) {

		/** ���Ԥ�������Ϊ�� */
		if (pstmt != null) {

			try {

				/** �ر�Ԥ������� */
				pstmt.close();

			} catch (SQLException e) {

				e.printStackTrace();

			}
		}
	}

	/**
	 * �رս��������
	 * 
	 * @param rs
	 *            ���������
	 */
	public static void close(ResultSet rs) {

		/** ������������Ϊ�� */
		if (rs != null) {

			try {

				/** �رս�������� */
				rs.close();

			} catch (SQLException e) {

				e.printStackTrace();

			}
		}
	}

}
