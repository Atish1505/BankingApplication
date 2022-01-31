package com.Mydatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionProvider {

	
	//Creating a Connection
	private static Connection con;
	public static Connection getConnection()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test1","root",null);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	//Closing a Connection for PreparedStatement And Connection
	public void CloseConnection(PreparedStatement pstmt,Connection con)
	{
		try {
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Closing a Connection for ResultSet,PreparedStatement And Connection
		public void CloseConnection(ResultSet rs,PreparedStatement pstmt,Connection con)
		{
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	
	
}
