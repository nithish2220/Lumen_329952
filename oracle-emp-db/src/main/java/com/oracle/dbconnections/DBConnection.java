package com.oracle.dbconnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DBConnection {
	public static Logger log = Logger.getRootLogger();
	
	public static Connection getConnection() {
		Connection con = null;
		String url = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
		String user = "hr";
		String password = "hr";
		try{
			con=DriverManager.getConnection(url,user,password);
			System.out.println("Connection Established!");
			log.info("Connection to Database is Successfull !");
		}
		catch(SQLException e) {
			log.info("Some Connection Exception occurred. Please, Check your connection details");
			System.out.println("Some Connection Error. Please Check!");
			e.printStackTrace();
		}
		return con;
	}
	
}
