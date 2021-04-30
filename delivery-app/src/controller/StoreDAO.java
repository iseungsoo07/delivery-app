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

public class StoreDAO {
	String dName = "com.mysql.jdbc.Driver";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	ArrayList<Store> store_list = new ArrayList<Store>();
	HashSet<String> food_type = new HashSet<String>();

	String[] ft = null;
	String[] sa = null;

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

	public void getFoodType() {

		int num = 0;

		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			String sql = "SELECT food_type FROM store";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				food_type.add(rs.getString("food_type"));
			}

			ft = new String[food_type.size()];
			Iterator<String> iter = food_type.iterator();

			while (iter.hasNext()) {
				ft[num++] = iter.next();
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
	
	public void showFoodType() {
		for (int i = 0; i < ft.length; i++) {
			System.out.print(i + 1 + ". " + ft[i] + "   ");
		}
		System.out.println();
	}

	public void getStoreInfo(int type) {
		
		int i = 0;
		
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			String sql = "SELECT count(*) FROM store WHERE food_type = '" + ft[type - 1] + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			int store_cnt = rs.getInt(1);
			sa = new String[store_cnt];

			sql = "SELECT sname FROM store WHERE food_type = '" + ft[type - 1] + "'";

			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				sa[i++] = rs.getString("sname");
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
	
	public void showStoreInfo() {
		System.out.println("===가게 목록===");
		for (int i = 0; i < sa.length; i++) {
			System.out.println(i + 1 + ". " + sa[i]);
		}
	}
	
	public String[] getStoreArray() {
		return sa;
	}
	
	public int getStoreArrayLength() {
		return sa.length;
	}

	

}
