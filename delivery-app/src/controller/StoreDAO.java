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

// 가게 테이블에 접근하기 위한 클래스
public class StoreDAO {
	String dName = "com.mysql.jdbc.Driver";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	// 가게 목록을 저장하기 위한 ArrayList
	ArrayList<Store> store_list = new ArrayList<Store>();
	
	// 음식 유형을 저장하기 위한 HashSet
	// 중복 제거를 위함
	HashSet<String> food_type = new HashSet<String>();
	
	
	// 음식 유형에 번호를 붙이기 위한
	// index가 필요해서 String 배열 선언
	String[] ft = null;
	
	// 가게 목록에 번호를 붙이기 위한
	// index가 필요해서 String 배열 선언
	String[] sa = null;
	
	// 모든 가게 정보를 ArrayList에 담기 위한 메소드
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

	// 음식 유형을 음식 유형  Set에 저장하기 위한 메소드
	// 유형의 중복을 막기 위해 Set 사용
	public void getFoodType() {

		// 배열 인덱스
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

			// HashSet에 음식 유형값을 저장
			while (rs.next()) {
				food_type.add(rs.getString("food_type"));
			}

			// HashSet의 크기만큼 음식 유형 배열 크기 지정
			ft = new String[food_type.size()];
			
			// Set은 순서가 없으므로 iterator() 메소드 사용
			Iterator<String> iter = food_type.iterator();

			// 음식유형 배열에 iter 돌면서
			// 나오는 값들 대입
			// ft[0] = "중식", ft[1] = "치킨" ...
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
	
	// ft 배열에 저장한 음식 유형을 출력
	public void showFoodType() {
		for (int i = 0; i < ft.length; i++) {
			// 1번부터 번호를 붙여서 출력
			System.out.print(i + 1 + ". " + ft[i] + "   ");
		}
		System.out.println();
	}

	// 음식 유형을 전달받아서 해당 음식 유형을 가지는
	// 가게 목록을 배열 형태로 반환해주는 메소드
	public String[] getStoreInfo(int type) {
		
		int index = 0;
		
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// 사용자가 1. 중식 을 선택했다면 중식은 ft[0]에 들어있음
			// 해당 음식 유형값을 가지는 가게들의 개수를 가져옴
			String sql = "SELECT count(*) FROM store WHERE food_type = '" + ft[type - 1] + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			// 그 개수를 store_cnt 변수에 저장
			int store_cnt = rs.getInt(1);
			
			// 가게 목록 배열의 크기를 그 개수만큼 지정 
			sa = new String[store_cnt];

			// 해당 음식 유형값을 가지는 가게들의 이름을 가져옴
			sql = "SELECT sname FROM store WHERE food_type = '" + ft[type - 1] + "'";

			rs = stmt.executeQuery(sql);
			
			// 가게들의 이름을 가게 목록 배열에 저장
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
	
	// sa 배열에 저장된 가게들의 이름을 번호를 붙여서 출력해주는 메소드
	public void showStoreInfo() {
		System.out.println("===가게 목록===");
		for (int i = 0; i < sa.length; i++) {
			System.out.println(i + 1 + ". " + sa[i]);
		}
	}
	
	// MenuDAO로 클래스 변수 sa를 전달하기 위한 메소드
	public String[] getStoreArray() {
		return sa;
	}
	
	// MenuDAO로 sa의 길이를 전달하기 위한 메소드
	public int getStoreArrayLength() {
		return sa.length;
	}

	

}
