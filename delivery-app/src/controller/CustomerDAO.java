package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Customer;

// 고객 테이블에 접근하기 위한 클래스
public class CustomerDAO {
	String dName = "com.mysql.jdbc.Driver";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	// 고객 리스트를 ArrayList로 저장
	// Customer 모델 클래스 타입을 가진 값을 저장
	ArrayList<Customer> customer_list = new ArrayList<Customer>();

	// 디비에서 모든 고객 데이터를 읽어서
	// ArrayList에 저장하고 해당 List를 반환
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

			// customer 테이블에서 입력받은 cid 값을 가진 고객이 몇 명인지 가져옴 
			String sql = "SELECT count(*) FROM customer WHERE cid = '" + cid + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			// 해당 아이디를 가진 고객이 있다면
			// true 반환 -> 중복 존재
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
		
		// 중복이 없으면 false 반환
		return false;

	}

	// 회원가입
	// id, pw, name, phone을 받음
	public boolean signUp(String cid, int cpw, String cname, int cphone) { 
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// 입력받은 값들을 가지고 고객테이블에 추가
			String sql = "INSERT INTO customer (cid, cpw, cname, cphone) VALUES ('" + cid + "', " + cpw + ", '" + cname + "', " + cphone + ")";
			stmt.executeUpdate(sql);
			
			// ArrayList에도 해당 정보를 추가
			this.customer_list.add(new Customer(cid, cpw, cname, cphone));

			// 회원가입 성공시 true 반환
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

		// 회원가입 실패 시 false 반환
		return false;
	}

	// 로그인
	// 아이디와 비밀번호를 입력받음
	public boolean signIn(String cid, int cpw) {

		// selectAll() 메소드로 전체 고객 리스트를 ArrayList로 반환받음
		// 해당 ArrayList를 순회하면서
		// 입력받은 cid와 cpw가 일치하는 고객이 있다면 true 반환
		for (Customer customer : selectAll()) {
			if (customer.getCid().equals(cid) && customer.getCpw() == cpw) {
				return true;
			}
		}
		
		// 그렇지 않으면 false 반환
		return false;
	}

	// 입력받은 고객의 잔액을 반환 받음
	public int getBalance(String cid) {

		// 잔액을 저장할 변수
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

			// 전달받은 cid를 가진 고객의 잔액을 가져옴
			String sql = "SELECT balance FROM customer WHERE cid = '" + cid + "'";
			rs = stmt2.executeQuery(sql);
			rs.next();

			// 가져온 잔액을 저장
			balance = rs.getInt(1);

			// 잔액 반환
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
	
	// 마이페이지에서 잔액을 추가할 때 호출할 메소드
	// 어떤 사용자에게 얼마의 금액을 추가할지 전달받음
	public boolean addBalance(int money, String cid) {
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// 사용자가 현재 가지고 있는 잔액을 가져옴
			int balance = getBalance(cid);
			
			// 전달받은 값을 현재 잔액에 더해준다.
			balance += money;

			// 사용자가 가진 잔액 값을 업데이트
			String sql = "UPDATE customer SET balance = " + balance + " WHERE cid = '" + cid + "'";
			stmt.executeUpdate(sql);

			// 업데이트 성공 시 true 반환
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

		// 실패 시 false 반환 
		return false;
	}

}
