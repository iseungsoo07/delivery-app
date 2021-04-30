package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import view.MemberView;

public class MenuDAO {
	StoreDAO sd = new StoreDAO();

	String dName = "com.mysql.jdbc.Driver";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	String[] ma = null;

	int totalPrice = 0;

	public void initStoreArray(int num) {
		sd.getStoreInfo(num);
	}

	public void initFoodTypeArray() {
		sd.getFoodType();
	}

	public void showMenus(int num) {

		String[] sa = new String[sd.getStoreArrayLength()];

		sa = sd.getStoreArray();
		int i = 0;

		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			String sname = sa[num - 1];

			String sql = "SELECT snum FROM store WHERE sname = '" + sname + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			int snum = rs.getInt("snum");

			sql = "SELECT count(*) FROM menu WHERE snum = " + snum;
			rs = stmt.executeQuery(sql);
			rs.next();

			int menu_cnt = rs.getInt(1);
			ma = new String[menu_cnt];

			sql = "SELECT menu, mprice FROM menu WHERE snum = " + snum;
			rs = stmt.executeQuery(sql);

			System.out.println("===�޴� ���===");

			while (rs.next()) {
				ma[i] = rs.getString("menu");
				System.out.println(i + 1 + ". " + ma[i] + "(" + rs.getInt("mprice") + "��)");
				i++;
			}

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
	}

	public void orderPhase() {
		Scanner sc = new Scanner(System.in);
		int num = 0;
		int count = 0;
		String ans = null;
		int mprice = 0;

		String address = null;

		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			String sql = null;

			while (true) {
				System.out.print("�޴� ���� : ");
				num = sc.nextInt();
				sql = "SELECT mprice FROM menu WHERE menu = '" + ma[num - 1] + "'";
				rs = stmt.executeQuery(sql);
				rs.next();
				mprice = rs.getInt(1);
				System.out.print("���� : ");
				count = sc.nextInt();
				totalPrice += count * mprice;
				System.out.print("�߰� �ֹ��Ͻðڽ��ϱ�? (y: ��, n: �ƴϿ�) : ");
				ans = sc.next();

				if (ans.toLowerCase().equals("y")) {
					continue;
				} else {
					System.out.print("�ּҸ� �Է����ּ��� : ");
					address = sc.next();
					break;
				}
			}

			System.out.println("============");
			System.out.println("��� �ּ� :" + address);
			System.out.println("�� ��� : " + totalPrice);

			System.out.println();
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
	}

	public void payMoney(String cid) {
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			int balance = 0;
		

			String sql = "SELECT balance FROM customer WHERE cid = '" + cid + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			balance = rs.getInt(1);
			
			if(balance < totalPrice) {
				System.out.println("�ܾ� ����! �ܾ��� Ȯ���ϰ� �ٽýõ����ּ���.");
			} else {
				balance -= totalPrice;				
			}

			sql = "UPDATE customer SET balance = " + balance + " WHERE cid = '" + cid + "'";
			stmt.executeUpdate(sql);
			
			sql = "SELECT balance FROM customer WHERE cid = '" + cid + "'";
			rs = stmt.executeQuery(sql);
			rs.next();
			
			System.out.println("������ �Ϸ�Ǿ����ϴ�.");
			System.out.println("���� �� " + cid + "���� �ܾ��� " + rs.getInt(1) + "�� �Դϴ�.");
			System.out.println();
			
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
	}

}
