package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Customer;

// �� ���̺� �����ϱ� ���� Ŭ����
public class CustomerDAO {
	String dName = "com.mysql.jdbc.Driver";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	// �� ����Ʈ�� ArrayList�� ����
	// Customer �� Ŭ���� Ÿ���� ���� ���� ����
	ArrayList<Customer> customer_list = new ArrayList<Customer>();

	// ��񿡼� ��� �� �����͸� �о
	// ArrayList�� �����ϰ� �ش� List�� ��ȯ
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

	public boolean checkCID(String cid) { // ���̵� �ߺ� �˻�
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// customer ���̺��� �Է¹��� cid ���� ���� ���� �� ������ ������ 
			String sql = "SELECT count(*) FROM customer WHERE cid = '" + cid + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			// �ش� ���̵� ���� ���� �ִٸ�
			// true ��ȯ -> �ߺ� ����
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
		
		// �ߺ��� ������ false ��ȯ
		return false;

	}

	// ȸ������
	// id, pw, name, phone�� ����
	public boolean signUp(String cid, int cpw, String cname, int cphone) { 
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// �Է¹��� ������ ������ �����̺� �߰�
			String sql = "INSERT INTO customer (cid, cpw, cname, cphone) VALUES ('" + cid + "', " + cpw + ", '" + cname + "', " + cphone + ")";
			stmt.executeUpdate(sql);
			
			// ArrayList���� �ش� ������ �߰�
			this.customer_list.add(new Customer(cid, cpw, cname, cphone));

			// ȸ������ ������ true ��ȯ
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

		// ȸ������ ���� �� false ��ȯ
		return false;
	}

	// �α���
	// ���̵�� ��й�ȣ�� �Է¹���
	public boolean signIn(String cid, int cpw) {

		// selectAll() �޼ҵ�� ��ü �� ����Ʈ�� ArrayList�� ��ȯ����
		// �ش� ArrayList�� ��ȸ�ϸ鼭
		// �Է¹��� cid�� cpw�� ��ġ�ϴ� ���� �ִٸ� true ��ȯ
		for (Customer customer : selectAll()) {
			if (customer.getCid().equals(cid) && customer.getCpw() == cpw) {
				return true;
			}
		}
		
		// �׷��� ������ false ��ȯ
		return false;
	}

	// �Է¹��� ���� �ܾ��� ��ȯ ����
	public int getBalance(String cid) {

		// �ܾ��� ������ ����
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

			// ���޹��� cid�� ���� ���� �ܾ��� ������
			String sql = "SELECT balance FROM customer WHERE cid = '" + cid + "'";
			rs = stmt2.executeQuery(sql);
			rs.next();

			// ������ �ܾ��� ����
			balance = rs.getInt(1);

			// �ܾ� ��ȯ
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
	
	// �������������� �ܾ��� �߰��� �� ȣ���� �޼ҵ�
	// � ����ڿ��� ���� �ݾ��� �߰����� ���޹���
	public boolean addBalance(int money, String cid) {
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// ����ڰ� ���� ������ �ִ� �ܾ��� ������
			int balance = getBalance(cid);
			
			// ���޹��� ���� ���� �ܾ׿� �����ش�.
			balance += money;

			// ����ڰ� ���� �ܾ� ���� ������Ʈ
			String sql = "UPDATE customer SET balance = " + balance + " WHERE cid = '" + cid + "'";
			stmt.executeUpdate(sql);

			// ������Ʈ ���� �� true ��ȯ
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

		// ���� �� false ��ȯ 
		return false;
	}

}
