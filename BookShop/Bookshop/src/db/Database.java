package db;

import info.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Database {
	private static Connection conn = null;

	//取得数据库连接
	private static Connection getConnection() {
		if (conn != null) {
			return conn;
		}

		String driver_MySQL = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/booklist";
		String account_MySQL = "root";
		String password_MySQL = "123";

		try {
			Class.forName(driver_MySQL);
			conn = DriverManager.getConnection(url, account_MySQL,
					password_MySQL);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("创建数据库连接失败!");
		}
		return conn;
	}

	private static String toSqlString(String str) {
		return new String(" '" + str + "' ");
	}

	// account is Name
	public static User userQquery(String accountName) {
		User user = null;

		String sql = "select * from userlist where name = " + toSqlString(accountName);
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == true) {
				user = new User();
				user.setId(rs.getLong(1));
				user.setName(rs.getString(2));
				user.setStuNum(rs.getString(3));
				user.setPwd(rs.getString(4));
			}
		} catch (SQLException sqle) {
			System.out.println("查询数据出现异常: " + sqle.getMessage());
		}

		return user;
	}
}
