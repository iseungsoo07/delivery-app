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

// �޴� ���̺� �����ϱ� ���� Ŭ����
public class MenuDAO {
	// StoreDAO ��ü ����
	StoreDAO sd = new StoreDAO();

	String dName = "com.mysql.jdbc.Driver";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	// �޴� ���
	String[] ma = null;

	// ���� ���
	String[] sa = null;

	// �� ����
	int totalPrice;

	// ���
	int mre;

	// �ֹ������� �����ϱ� ���� HashMap
	// String Ÿ���� �޴� �̸��� Integer Ÿ���� ����
	HashMap<String, Integer> orderList = new HashMap<String, Integer>();

	// ����ڰ� �Է��� ���� ���� ��ȣ�� num���� �Ѱ��ش�.
	// ���� ��ϵ� ���������� null�� �ʱ�ȭ �Ǿ��ֱ� ������ StoreDAO����
	// ���Ը���� ������ �� MenuDAO�� ��ȯ
	public void initStoreArray(int num) {
		sa = sd.getStoreInfo(num);
	}

	// MenuDAO�� ȣ���� ��� ���������迭�� null�� �ʱ�ȭ�Ǿ��ֱ� ������
	// ���� ���� �迭�� ���� �־��ֱ� ���� �޼ҵ�
	public void initFoodTypeArray() {
		sd.getFoodType();
	}

	// ����ڰ� �Է��� ���� ��ȣ�� �޾Ƽ�
	// �ش� ���԰� ���� �޴����� ����� ������ִ� �޼ҵ�
	public void showMenus(int num) {

		// StoreDAO�� ���� ��� �迭�� ���̸� ����
		// MenuDAO�� ���ο� ���Ը�� �迭�� ����
		String[] sa = new String[sd.getStoreArrayLength()];

		// StoreDAO�� ����� sa���� ���޹���
		sa = sd.getStoreArray();

		// �޴� ��� �ε����� ����� ����
		int i = 0;

		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// ����ڰ� �Է��� ���� ��ȣ - 1�� ���� �ش� ������ ��ġ
			// ���� �̸��� sname�� ����
			String sname = sa[num - 1];

			// �ش� ���� �̸��� ���� ������ ���� ��ȣ�� ������
			String sql = "SELECT snum FROM store WHERE sname = '" + sname + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			// ������ ���� ��ȣ ����
			int snum = rs.getInt("snum");

			// �޴� ���̺��� �ش� ���Թ�ȣ�� ���� �޴��� ������ ������
			sql = "SELECT count(*) FROM menu WHERE snum = " + snum;
			rs = stmt.executeQuery(sql);
			rs.next();

			// ������ �޴������� �����ϰ�
			// �� ������ŭ �޴� ��� �迭�� ũ�� ����
			int menu_cnt = rs.getInt(1);
			ma = new String[menu_cnt];

			// �޴����̺��� �ش� ���Թ�ȣ�� ������ �޴��� �̸��� ������ ������
			sql = "SELECT menu, mprice FROM menu WHERE snum = " + snum;
			rs = stmt.executeQuery(sql);

			System.out.println("===�޴� ���===");

			// �޴��� �̸��� �޴� ��� �迭�� ����
			// �޴� �̸��� ��ȣ�� ���̰� ���ݵ� ���� ���
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

	// ����� ���̵�� ���Թ�ȣ�� ���ڷ� �޾ƿ�
	// �޴��� �ش� �޴��� ������ �����ϴ� �޼ҵ�
	public void orderPhase(String cid) {
		Scanner sc = new Scanner(System.in);
		String ans = null;
		int num = 0; // ����� �Է��� �ޱ� ���� ����
		orderList.clear();

		// Ư���� ����� ������
		// �ֹ� �Ҷ� �ּҸ� �Է¹ޱ� ����
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
				int mprice = 0; // ������ �޴��� ������ ����
				int count = 0; // ������ �޴��� ������ ����
				System.out.print("�޴� ���� : ");
				num = sc.nextInt();

				// ����ڰ� ������ �޴��� ������ ������
				sql = "SELECT mprice FROM menu WHERE menu = '" + ma[num - 1] + "'";
				rs = stmt.executeQuery(sql);
				rs.next();

				// �ش� �޴��� ������ mprice�� ����
				mprice = rs.getInt(1);

				System.out.print("���� : ");
				count = sc.nextInt(); // ������ count�� ����

				// �ش� �޴��� ��� ������
				sql = "SELECT mre FROM menu WHERE menu = '" + ma[num - 1] + "'";
				rs = stmt.executeQuery(sql);
				rs.next();

				// ������ ��� ����
				mre = rs.getInt(1);

				// �ֹ� �������� ��� ���ٸ�
				if (count > mre) {
					// ���� ��� ������ �Բ� �ٽ� �õ��ش޶�� �޽��� ���
					System.out.println("��� �����մϴ�." + ma[num - 1] + "�� ���� ���� " + mre + "�� �Դϴ�.");
					System.out.println("�ٽ� �õ����ּ���.");
					continue;
				} else {
					// orderList HashMap�� {�޴�, ����} ������ ����
					orderList.put(ma[num - 1], count);

					// �� ���� ���
					totalPrice += count * mprice;

					// �߰� �ֹ� ���θ� ���
					System.out.print("�߰� �ֹ��Ͻðڽ��ϱ�? (y: ��, n: �ƴϿ�) : ");
					ans = sc.next();

					// Ȥ�� �빮�ڸ� �Է����� ��츦 ���
					if (ans.toLowerCase().equals("y")) {
						continue;
					} else {
						// �߰� �ֹ��� ���� ���� ��� �ּҸ� �Է¹ް�
						System.out.print("�ּҸ� �Է����ּ��� : ");
						address = sc.next();
						break;
					}
				}
			}

			// ��� �ּҿ� �� ����� �������
			System.out.println("============");
			System.out.println("��� �ּ� :" + address);
			System.out.println("�� ��� : " + totalPrice);

			System.out.println();
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
	}

	// ������� ���̵� ���޹޾�
	// �ش� ������� �ܾ׿� �����ϱ� ����
	// ���� �޼ҵ�
	public void payMoney(String cid) {
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// ������� ���� �ܾ��� �޾ƿ��� ���� ����
			int balance = 0;

			// �Ѱܹ��� cid�� ���� ������� �ܾ��� ������
			String sql = "SELECT balance FROM customer WHERE cid = '" + cid + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			// ������ �ܾ��� ������ ����
			balance = rs.getInt(1);

			// �ܾ��� �ֹ� �ݾ׺��� ���� ��� ���� ����
			// MemberView() ȭ������ ���ư�
			if (balance < totalPrice) {
				System.out.println("�ܾ� ����! �ܾ��� Ȯ���ϰ� �ٽýõ����ּ���.");
				new MemberView(cid);
			} else {
				// �ܾ׿��� �� ���ݸ�ŭ ���ش�
				balance -= totalPrice;
			}

			// �ش� cid�� ���� ������� �ܾ��� ������Ʈ ����
			sql = "UPDATE customer SET balance = " + balance + " WHERE cid = '" + cid + "'";
			stmt.executeUpdate(sql);

			// ������Ʈ �� �ܾ��� �ٽ� ������
			sql = "SELECT balance FROM customer WHERE cid = '" + cid + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			// cid�� ���� ������� ���� �ܾװ� �Բ�
			// ������ �Ϸ�Ǿ��ٴ� �޽��� ���
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
	
	// ���� ���� �޴� ��� ������Ʈ�ϱ� ���� �޼ҵ� 
	public void updateMenuRemain() {
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";
			String sql = null;
			
			// ���� ��� ������ ����
			int mre = 0;
			
			// ������ ������ ����
			int mcount = 0;

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			
			// HashMap�� ����� ���� �� �־� �������� ���� Iterator
			Iterator<Entry<String, Integer>> iter = orderList.entrySet().iterator();
			
			while (iter.hasNext()) {
				// {key:value} �ѽ��� entry�� ����
				Entry<String, Integer> entry = iter.next();

				// entry�� ����� �޴��̸��� ���� �ش� �޴��� ��� ������
				sql = "SELECT mre FROM menu WHERE menu = '" + entry.getKey() + "'";
				rs = stmt.executeQuery(sql);
				rs.next();
				
				// ������ ��� ����
				mre = rs.getInt("mre");
				
				// entry�� ����� �ش� �޴��� �� �� �ֹ��ߴ��� ������ ������
				mcount = entry.getValue();
				
				// ����� ���� ����
				mre -= mcount;
				
				// ��� ������Ʈ
				sql = "UPDATE menu SET mre = " + mre + " WHERE menu = '" + entry.getKey() + "'";
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
	

	// �ֹ����� ���̺� �ֹ������� �����ϱ� ���� �޼ҵ�
	// � ������� �ֹ����� � ���������� �Ķ���ͷ� �޾ƿ�
	public void insertOrderList(String cid, int storeNum) {
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// HashMap�� ����� ���� �� �־� �������� ���� Iterator
			Iterator<Entry<String, Integer>> iter = orderList.entrySet().iterator();

			// store ���̺��� �ش� ���� �̸��� ���� ������ ��ȣ�� ������
			String sql = "SELECT snum FROM store WHERE sname = '" + sa[storeNum - 1] + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			// ������ ���� ��ȣ ����
			int snum = rs.getInt(1);

			while (iter.hasNext()) {
				// {key:value} �ѽ��� entry�� ����
				Entry<String, Integer> entry = iter.next();

				// key���� �޴��̸��� ����
				// �ش� �޴��̸��� ���� �޴��� ��ȣ�� ������
				sql = "SELECT mnum FROM menu WHERE menu = '" + entry.getKey() + "'";
				rs = stmt.executeQuery(sql);
				rs.next();

				// ������ �޴� ��ȣ�� ����
				int mnum = rs.getInt(1);

				// �ֹ� ������ �Ʒ� �������� ����
				// entry.getValue()���� ������ �������
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
