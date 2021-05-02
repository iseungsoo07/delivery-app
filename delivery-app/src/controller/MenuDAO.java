package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

import view.MemberView;

public class MenuDAO {
	StoreDAO sd = new StoreDAO();

	String dName = "com.mysql.jdbc.Driver";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	String[] ma = null;
	String[] sa = null;

	int totalPrice;
	int mre;

	HashMap<String, Integer> orderList = new HashMap<String, Integer>();

	public void initStoreArray(int num) {
		sa = sd.getStoreInfo(num);
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

	public void orderPhase(String cid, int storeNum) {
		Scanner sc = new Scanner(System.in);
		String ans = null;
		int num = 0;

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
				int mprice = 0;
				int count = 0;
				System.out.print("�޴� ���� : ");
				num = sc.nextInt();
				sql = "SELECT mprice FROM menu WHERE menu = '" + ma[num - 1] + "'";
				rs = stmt.executeQuery(sql);
				rs.next();
				mprice = rs.getInt(1);
				System.out.print("���� : ");
				count = sc.nextInt();
				orderList.put(ma[num - 1], count);
				totalPrice += count * mprice;

				sql = "SELECT mre FROM menu WHERE menu = '" + ma[num - 1] + "'";
				rs = stmt.executeQuery(sql);
				rs.next();

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

			if (balance < totalPrice) {
				System.out.println("�ܾ� ����! �ܾ��� Ȯ���ϰ� �ٽýõ����ּ���.");
				new MemberView(cid);
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

	public void insertOrderList(String cid, int storeNum) {
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			Iterator<Entry<String, Integer>> iter = orderList.entrySet().iterator();

			String sql = "SELECT snum FROM store WHERE sname = '" + sa[storeNum - 1] + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			int snum = rs.getInt(1);

			while (iter.hasNext()) {
				Entry<String, Integer> entry = iter.next();
				sql = "SELECT mnum FROM menu WHERE menu = '" + entry.getKey() + "'";
				rs = stmt.executeQuery(sql);
				rs.next();

				int mnum = rs.getInt(1);

				sql = "INSERT INTO orderlist (cid, snum, mnum, mcount) VALUES ('" + cid + "', " + snum + ", " + mnum
						+ ", " + entry.getValue() + ")";
				stmt.executeUpdate(sql);
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
	

}
