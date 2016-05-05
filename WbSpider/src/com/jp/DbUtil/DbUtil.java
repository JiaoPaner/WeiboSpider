package com.jp.DbUtil;
/* 作者：jiaopan
 * 时间：2016.5
 * */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DbUtil {
	public static Connection getConn() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_wbspider","root","");
		return conn;
	}

	public static void closeConn(Connection conn) throws Exception{
		if(conn!=null){
			conn.close();
			conn=null;
		}
	}
	public static void closeRs(ResultSet rs) throws Exception{
		if(rs!=null){
			rs.close();
			rs=null;
		}
	}
	public static void main(String[] args){
		//DbUtil dbUtil=new DbUtil();
		try {
			getConn();
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据库连接失败!");
		}
	}
}
