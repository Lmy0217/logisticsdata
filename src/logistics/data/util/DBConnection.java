package logistics.data.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * 数据库连接配置类
 * 
 */
public class DBConnection {

	/** 驱动类类名 */
	private static final String DBDRIVER = "com.mysql.jdbc.Driver";

	/** 连接URL */
	private static final String DBURL = "jdbc:mysql://182.254.210.110:3306/data?useUnicode=true&characterEncoding=GBK&useSSL=true";

	/** 数据库用户名 */
	private static final String DBUSER = "root";

	/** 数据库密码 */
	private static final String DBPASSWORD = "Lmy419608";

	/**
	 * 获得数据库连接
	 * 
	 * @return 数据库连接
	 */
	public static Connection getConnection() {

		/** 声明一个连接对象 */
		Connection conn = null;

		try {

			/** 注册驱动 */
			Class.forName(DBDRIVER);

			/** 获得连接对象 */
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return conn;
	}

	/**
	 * 关闭连接对象
	 * 
	 * @param conn
	 *            连接对象
	 */
	public static void close(Connection conn) {

		/** 如果连接对象不为空 */
		if (conn != null) {

			try {

				/** 关闭连接对象 */
				conn.close();

			} catch (SQLException e) {

				e.printStackTrace();

			}
		}
	}

	/**
	 * 关闭预处理对象
	 * 
	 * @param pstmt
	 *            预处理对象
	 */
	public static void close(PreparedStatement pstmt) {

		/** 如果预处理对象不为空 */
		if (pstmt != null) {

			try {

				/** 关闭预处理对象 */
				pstmt.close();

			} catch (SQLException e) {

				e.printStackTrace();

			}
		}
	}

	/**
	 * 关闭结果集对象
	 * 
	 * @param rs
	 *            结果集对象
	 */
	public static void close(ResultSet rs) {

		/** 如果结果集对象不为空 */
		if (rs != null) {

			try {

				/** 关闭结果集对象 */
				rs.close();

			} catch (SQLException e) {

				e.printStackTrace();

			}
		}
	}

}
