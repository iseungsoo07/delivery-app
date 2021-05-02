package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import model.Store;

// ���� ���̺� �����ϱ� ���� Ŭ����
public class StoreDAO {
	String dName = "com.mysql.jdbc.Driver";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	// ���� ����� �����ϱ� ���� ArrayList
	ArrayList<Store> store_list = new ArrayList<Store>();
	
	// ���� ������ �����ϱ� ���� HashSet
	// �ߺ� ���Ÿ� ����
	HashSet<String> food_type = new HashSet<String>();
	
	
	// ���� ������ ��ȣ�� ���̱� ����
	// index�� �ʿ��ؼ� String �迭 ����
	String[] ft = null;
	
	// ���� ��Ͽ� ��ȣ�� ���̱� ����
	// index�� �ʿ��ؼ� String �迭 ����
	String[] sa = null;
	
	// ��� ���� ������ ArrayList�� ��� ���� �޼ҵ�
	public ArrayList<Store> selectAll() {
		try {

			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			String sql = "SELECT * FROM store";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				this.store_list.add(new Store(rs.getInt("snum"), rs.getString("sname"), rs.getString("saddr"),
						rs.getInt("sphone"), rs.getString("menu_type")));
			}

			return store_list;

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

	// ���� ������ ���� ����  Set�� �����ϱ� ���� �޼ҵ�
	// ������ �ߺ��� ���� ���� Set ���
	public void getFoodType() {

		// �迭 �ε���
		int index = 0;

		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			String sql = "SELECT food_type FROM store";
			rs = stmt.executeQuery(sql);

			// HashSet�� ���� �������� ����
			while (rs.next()) {
				food_type.add(rs.getString("food_type"));
			}

			// HashSet�� ũ�⸸ŭ ���� ���� �迭 ũ�� ����
			ft = new String[food_type.size()];
			
			// Set�� ������ �����Ƿ� iterator() �޼ҵ� ���
			Iterator<String> iter = food_type.iterator();

			// �������� �迭�� iter ���鼭
			// ������ ���� ����
			// ft[0] = "�߽�", ft[1] = "ġŲ" ...
			while (iter.hasNext()) {
				ft[index++] = iter.next();
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
	
	// ft �迭�� ������ ���� ������ ���
	public void showFoodType() {
		for (int i = 0; i < ft.length; i++) {
			// 1������ ��ȣ�� �ٿ��� ���
			System.out.print(i + 1 + ". " + ft[i] + "   ");
		}
		System.out.println();
	}

	// ���� ������ ���޹޾Ƽ� �ش� ���� ������ ������
	// ���� ����� �迭 ���·� ��ȯ���ִ� �޼ҵ�
	public String[] getStoreInfo(int type) {
		
		int index = 0;
		
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// ����ڰ� 1. �߽� �� �����ߴٸ� �߽��� ft[0]�� �������
			// �ش� ���� �������� ������ ���Ե��� ������ ������
			String sql = "SELECT count(*) FROM store WHERE food_type = '" + ft[type - 1] + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			// �� ������ store_cnt ������ ����
			int store_cnt = rs.getInt(1);
			
			// ���� ��� �迭�� ũ�⸦ �� ������ŭ ���� 
			sa = new String[store_cnt];

			// �ش� ���� �������� ������ ���Ե��� �̸��� ������
			sql = "SELECT sname FROM store WHERE food_type = '" + ft[type - 1] + "'";

			rs = stmt.executeQuery(sql);
			
			// ���Ե��� �̸��� ���� ��� �迭�� ����
			while(rs.next()) {
				sa[index++] = rs.getString("sname");
			}
			
			return sa;

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
	
	// sa �迭�� ����� ���Ե��� �̸��� ��ȣ�� �ٿ��� ������ִ� �޼ҵ�
	public void showStoreInfo() {
		System.out.println("===���� ���===");
		for (int i = 0; i < sa.length; i++) {
			System.out.println(i + 1 + ". " + sa[i]);
		}
	}
	
	// MenuDAO�� Ŭ���� ���� sa�� �����ϱ� ���� �޼ҵ�
	public String[] getStoreArray() {
		return sa;
	}
	
	// MenuDAO�� sa�� ���̸� �����ϱ� ���� �޼ҵ�
	public int getStoreArrayLength() {
		return sa.length;
	}

	

}
