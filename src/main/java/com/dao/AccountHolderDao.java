package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;

import com.Mydatabase.ConnectionProvider;
import com.model.AccountHolder;

public class AccountHolderDao {

	//Generating a Secure Pin for Account Holder
	private String generatePin() {
		int n = new Random().nextInt(9999);
		int n1 = n;
		for (int i = 4; i > new Integer(n).toString().length(); i--)
			n1 *= 10;
		return new Integer(n1).toString();
	}
	
    //Getting a Maximum Account Number
	public int getMaxAcNo() {
		int check = 0;
		Connection con = new ConnectionProvider().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select max(uacno) as uacno from accountholder";
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next())
				check = (int) rs.getObject("uacno");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			new ConnectionProvider().CloseConnection(rs, pst, con);
		}

		return check;
	}
	
	//Encrypt a Pin 
	private String encryptPin(String pin) {
		return Base64.getEncoder().encodeToString(pin.getBytes());
	}
	
	//Creating a method CreateAccount thats save a Data of AccountHolder to Database 
	public String[] createAccount(AccountHolder u) {
		String s1[] = new String[2];
		//generating pin
		u.setUacpin(generatePin());
		Connection con = new ConnectionProvider().getConnection();
		PreparedStatement pst = null;
		String sql = "insert into accountholder(uacname,uactype,uacbalance,uacpin)values(?,?,?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, u.getUacname());
			pst.setString(2, u.getUactype());
			pst.setDouble(3, u.getUacbalance());
			pst.setString(4, encryptPin(u.getUacpin()));
			if (pst.executeUpdate() > 0) {
				s1[0] = new Integer(getMaxAcNo()).toString();
				s1[1] = u.getUacpin();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			new ConnectionProvider().CloseConnection(pst, con);
		}
		return s1;
	}
	
	//Its check a Login Credintial of Account Holder
	public boolean checkLogin(int uacno, String pin) {
		boolean b = false;
		Connection con = new ConnectionProvider().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select uacno from accountholder where uacno=? and uacpin=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, uacno);
			pst.setString(2, encryptPin(pin));
			rs = pst.executeQuery();
			while (rs.next())
				b = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			new ConnectionProvider().CloseConnection(rs, pst, con);
		}
		return b;
	}
	
	//this method returns a balance of Account Holder
	public double checkBalance(int uacno, String pin) {
		double balance = 0;
		Connection con = new ConnectionProvider().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select uacbalance from accountholder where uacno=? and uacpin=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, uacno);
			pst.setString(2, encryptPin(pin));
			rs = pst.executeQuery();
			while (rs.next())
				balance = (double) rs.getObject("uacbalance");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			new ConnectionProvider().CloseConnection(rs, pst, con);
		}
		return balance;
	}
	
	//it is used to withdrwal a Balance from Account Holders Account
	public double withdrawlBalance(int acno, String pin, double balance) {
		double updatebalance = -1;
		if (checkBalance(acno, pin) < balance) {
			System.out.println("Insufficient fund!!!!!");
			System.out.println("Your Current balance is:" + checkBalance(acno, pin));
		} else {
			Connection con = new ConnectionProvider().getConnection();
			balance = checkBalance(acno, pin) - balance;
			PreparedStatement pst = null;
			String sql = "update accountholder set uacbalance=? where uacno=? and uacpin=?";
			try {
				pst = con.prepareStatement(sql);
				pst.setDouble(1, balance);
				pst.setInt(2, acno);
				pst.setString(3, encryptPin(pin));
				if (pst.executeUpdate() > 0) {
					updatebalance = checkBalance(acno, pin);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(updatebalance>0)
		{
		return updatebalance;
		}
		else
			return 0;
	}
			
	//it is used to deposit a Balance from Account Holders Account
	public double depositBalance(int uacno, String pin, double amount) {
		double balance = -1;
		amount+= checkBalance(uacno, pin);
		Connection con = new ConnectionProvider().getConnection();
		PreparedStatement pst = null;
		String sql = "update accountholder set uacbalance=? where uacno=? and uacpin=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setDouble(1, amount);
			pst.setInt(2, uacno);
			pst.setString(3, encryptPin(pin));
			if (pst.executeUpdate() > 0)
				balance = amount;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			new ConnectionProvider().CloseConnection(pst, con);
		}
		return balance;
	}
	
	//this method return a details about Account Holder
	public AccountHolder accountDetails(int uacno, String pin) {
		AccountHolder u = null;
		Connection con = new ConnectionProvider().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select uacno,uacname,uactype,uacbalance from accountholder where uacno=? and uacpin=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, uacno);
			pst.setString(2, encryptPin(pin));
			rs = pst.executeQuery();
			while (rs.next()) {
				u = new AccountHolder();
				u.setUacno((int) rs.getObject("uacno"));
				u.setUacname((String) rs.getObject("uacname"));
				u.setUactype((String) rs.getObject("uactype"));
				u.setUacbalance((double) rs.getObject("uacbalance"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			new ConnectionProvider().CloseConnection(rs, pst, con);
		}
		return u;
	}

	//it is to change a Pin of Account Holder
	public boolean updateAcPin(AccountHolder u) {
		boolean b = false;
		Connection con = new ConnectionProvider().getConnection();
		PreparedStatement pst = null;
		String sql = "update accountholder set uacpin=? where uacno=? and uacname like '%" + u.getUacname()
				+ "%' and uactype like '%" + u.getUactype() + "%'";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, encryptPin(u.getUacpin()));
			pst.setInt(2, u.getUacno());
			if (pst.executeUpdate() > 0)
				b = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			new ConnectionProvider().CloseConnection(pst, con);
		}
		return b;
	}
	//this method is used to close the Account
	public AccountHolder CloseAccount(int uacno,String pin)
	{
		AccountHolder u = null;
		Connection con = new ConnectionProvider().getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "delete from accountholder where uacno=? and uacpin=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, uacno);
			pst.setString(2, encryptPin(pin));
		if(pst.executeUpdate()>0)
		{
			u=new AccountHolder();
			System.out.println("Account Close Successfully");
		}
		else
			System.out.println("Account Still In database");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			new ConnectionProvider().CloseConnection(pst, con);
		}
		return u;
			
	}
}




