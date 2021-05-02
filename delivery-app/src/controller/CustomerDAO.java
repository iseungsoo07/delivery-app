package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Customer;

public class CustomerDAO {
	String dName = "com.mysql.jdbc.Driver";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	ArrayList<Customer> customer_list = new ArrayList<Customer>();

	public ArrayList<Customer> selectAll() {
		try {

			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			String sql = "SELECT * FROM customer";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				this.customer_list.add(new Customer(rs.getString("cid"), rs.getInt("cpw"), rs.getString("cname"),
						rs.getInt("cphone")));
			}

			return customer_list;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public boolean checkCID(String cid) { // 아이디 중복 검사
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			String sql = "SELECT count(*) FROM customer WHERE cid = '" + cid + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			if (rs.getInt(1) == 1) {
				return true;
			}

		} catch (

		ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	public boolean signUp(String cid, int cpw, String cname, int cphone) { // 회원가입
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			String sql = "INSERT INTO customer (cid, cpw, cname, cphone) VALUES ('" + cid + "', " + cpw + ", '" + cname + "', " + cphone + ")";
			stmt.executeUpdate(sql);
			this.customer_list.add(new Customer(cid, cpw, cname, cphone));

			return true;

		} catch (

		ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public boolean signIn(String cid, int cpw) { // 로그인

		for (Customer customer : selectAll()) {
			if (customer.getCid().equals(cid) && customer.getCpw() == cpw) {
				return true;
			}
		}

		return false;
	}
	


	public int getBalance(String cid) {

		int balance = 0;
		Connection conn2 = null;
		Statement stmt2 = null;

		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn2 = DriverManager.getConnection(url, user, password);
			stmt2 = conn2.createStatement();

			String sql = "SELECT balance FROM customer WHERE cid = '" + cid + "'";
			rs = stmt2.executeQuery(sql);
			rs.next();

			balance = rs.getInt(1);

			return balance;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt2.close();
				conn2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

	public boolean addBalance(int money, String cid) {
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			int balance = getBalance(cid);
			balance += money;

			String sql = "UPDATE customer SET balance = " + balance + " WHERE cid = '" + cid + "'";
			stmt.executeUpdate(sql);

			return true;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

}
