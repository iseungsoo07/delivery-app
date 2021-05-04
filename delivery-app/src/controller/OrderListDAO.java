package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import view.MemberView;

// 주문내역 테이블에 접근하기 위한 클래스
public class OrderListDAO {
	String dName = "com.mysql.jdbc.Driver";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	// 사용자에게 주문 내역을 보여주기 위한 메소드
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

			// 주문내역 테이블에서 넘겨받은 cid를 가진 사람의 주문 내역을 가져옴
			String sql = "SELECT snum, mnum, mcount, order_date FROM orderlist WHERE cid = '" + cid + "'";
			rs = stmt.executeQuery(sql);
			
			// 주문 내역 테이블이 비어있을 경우 주문 내역이 없다는 메시지 출력
			// 이후 다시 사용자 화면으로 돌아감
//			if(!rs.next()) {
//				System.out.println("주문 내역이 없습니다.");
//				new MemberView(cid);
//			}
			
			
			while (rs.next()) {
				// 가게 번호, 메뉴 번호, 수량, 주문 일자를 전부 저장
				int snum = rs.getInt("snum");
				int mnum = rs.getInt("mnum");
				int mcount = rs.getInt("mcount");
				String order_date = rs.getString("order_date");

				// 사용자에게 보여지는 주문 내역은
				// 메뉴 번호, 가게 번호가 아닌 메뉴 이름, 가게 이름이 보여져야 한다.
				
				// 주문 내역에 저장된 snum값을 통해 가게 이름을 가져옴
				sql = "SELECT sname FROM store WHERE snum = " + snum;
				stmt2 = conn.createStatement();
				rs2 = stmt2.executeQuery(sql);

				rs2.next();

				// 가져온 가게 이름 저장
				String sname = rs2.getString("sname");

				// 주문 내역에 저장된 mnum값을 통해 메뉴 이름을 가져옴
				sql = "SELECT menu FROM menu WHERE mnum = " + mnum;
				stmt3 = conn.createStatement();
				rs3 = stmt3.executeQuery(sql);

				rs3.next();

				// 가져온 메뉴 이름 저장
				String menu = rs3.getString("menu");
				
				// 해당 메뉴 번호를 가진 메뉴의 가격을 가져옴
				// 총 금액이 얼마인지 주문내역에서 보여주기 위함
				sql = "SELECT mprice FROM menu WHERE mnum = " + mnum;
				stmt4 = conn.createStatement();
				rs4 = stmt4.executeQuery(sql);
				
				rs4.next();
				
				// 가져온 가격을 저장
				int mprice = rs4.getInt("mprice");

				// 주문 내역 출력
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
