package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import view.MemberView;

public class OrderListDAO {
	String dName = "com.mysql.jdbc.Driver";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	public void showOrderList(String cid) {
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			Statement stmt2 = null;
			Statement stmt3 = null;
			Statement stmt4 = null;
			ResultSet rs2 = null;
			ResultSet rs3 = null;
			ResultSet rs4 = null;

			String sql = "SELECT snum, mnum, mcount, order_date FROM orderlist WHERE cid = '" + cid + "'";
			rs = stmt.executeQuery(sql);
			
			if(!rs.next()) {
				System.out.println("주문 내역이 없습니다.");
				new MemberView(cid);
			}
			
			while (rs.next()) {
				int snum = rs.getInt("snum");
				int mnum = rs.getInt("mnum");
				int mcount = rs.getInt("mcount");
				String order_date = rs.getString("order_date");

				sql = "SELECT sname FROM store WHERE snum = " + snum;
				stmt2 = conn.createStatement();
				rs2 = stmt2.executeQuery(sql);

				rs2.next();

				String sname = rs2.getString("sname");

				sql = "SELECT menu FROM menu WHERE mnum = " + mnum;
				stmt3 = conn.createStatement();
				rs3 = stmt3.executeQuery(sql);

				rs3.next();

				String menu = rs3.getString("menu");
				
				sql = "SELECT mprice FROM menu WHERE mnum = " + mnum;
				stmt4 = conn.createStatement();
				rs4 = stmt4.executeQuery(sql);
				
				rs4.next();
				
				int mprice = rs4.getInt("mprice");

				System.out.println(order_date + " / " + sname + " / " + menu + " / " + mcount + "개 / " + mcount * mprice + "원") ;

			}

			stmt2.close();
			stmt3.close();
			stmt4.close();

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
