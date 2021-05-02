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

// 메뉴 테이블에 접근하기 위한 클래스
public class MenuDAO {
	// StoreDAO 객체 생성
	StoreDAO sd = new StoreDAO();

	String dName = "com.mysql.jdbc.Driver";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	// 메뉴 목록
	String[] ma = null;

	// 가게 목록
	String[] sa = null;

	// 총 가격
	int totalPrice;

	// 재고
	int mre;

	// 주문내역을 저장하기 위한 HashMap
	// String 타입의 메뉴 이름과 Integer 타입의 수량
	HashMap<String, Integer> orderList = new HashMap<String, Integer>();

	// 사용자가 입력한 음식 유형 번호를 num으로 넘겨준다.
	// 가게 목록도 마찬가지로 null로 초기화 되어있기 때문에 StoreDAO에서
	// 가게목록을 저장한 뒤 MenuDAO로 반환
	public void initStoreArray(int num) {
		sa = sd.getStoreInfo(num);
	}

	// MenuDAO를 호출할 경우 음식유형배열이 null로 초기화되어있기 때문에
	// 음식 유형 배열에 값을 넣어주기 위한 메소드
	public void initFoodTypeArray() {
		sd.getFoodType();
	}

	// 사용자가 입력한 가게 번호를 받아서
	// 해당 가게가 가진 메뉴들의 목록을 출력해주는 메소드
	public void showMenus(int num) {

		// StoreDAO의 가게 목록 배열의 길이를 통해
		// MenuDAO에 새로운 가게목록 배열을 선언
		String[] sa = new String[sd.getStoreArrayLength()];

		// StoreDAO에 저장된 sa값을 전달받음
		sa = sd.getStoreArray();

		// 메뉴 목록 인덱스로 사용할 변수
		int i = 0;

		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// 사용자가 입력한 가게 번호 - 1이 실제 해당 가게의 위치
			// 가게 이름을 sname에 저장
			String sname = sa[num - 1];

			// 해당 가게 이름을 가진 가게의 가게 번호를 가져옴
			String sql = "SELECT snum FROM store WHERE sname = '" + sname + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			// 가져온 가게 번호 저장
			int snum = rs.getInt("snum");

			// 메뉴 테이블에서 해당 가게번호를 가진 메뉴의 개수를 가져옴
			sql = "SELECT count(*) FROM menu WHERE snum = " + snum;
			rs = stmt.executeQuery(sql);
			rs.next();

			// 가져온 메뉴개수를 저장하고
			// 그 개수만큼 메뉴 목록 배열의 크기 지정
			int menu_cnt = rs.getInt(1);
			ma = new String[menu_cnt];

			// 메뉴테이블에서 해당 가게번호를 가지는 메뉴의 이름과 가격을 가져옴
			sql = "SELECT menu, mprice FROM menu WHERE snum = " + snum;
			rs = stmt.executeQuery(sql);

			System.out.println("===메뉴 목록===");

			// 메뉴의 이름을 메뉴 목록 배열에 저장
			// 메뉴 이름에 번호를 붙이고 가격도 같이 출력
			while (rs.next()) {
				ma[i] = rs.getString("menu");
				System.out.println(i + 1 + ". " + ma[i] + "(" + rs.getInt("mprice") + "원)");
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

	// 사용자 아이디와 가게번호를 인자로 받아옴
	// 메뉴와 해당 메뉴의 수량을 선택하는 메소드
	public void orderPhase(String cid) {
		Scanner sc = new Scanner(System.in);
		String ans = null;
		int num = 0; // 사용자 입력을 받기 위한 변수
		orderList.clear();

		// 특별한 기능은 없지만
		// 주문 할때 주소를 입력받기 위함
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
				int mprice = 0; // 선택한 메뉴의 가격을 저장
				int count = 0; // 선택한 메뉴의 개수를 저장
				System.out.print("메뉴 선택 : ");
				num = sc.nextInt();

				// 사용자가 선택한 메뉴의 가격을 가져옴
				sql = "SELECT mprice FROM menu WHERE menu = '" + ma[num - 1] + "'";
				rs = stmt.executeQuery(sql);
				rs.next();

				// 해당 메뉴의 가격을 mprice에 저장
				mprice = rs.getInt(1);

				System.out.print("수량 : ");
				count = sc.nextInt(); // 수량을 count에 저장

				// 해당 메뉴의 재고를 가져옴
				sql = "SELECT mre FROM menu WHERE menu = '" + ma[num - 1] + "'";
				rs = stmt.executeQuery(sql);
				rs.next();

				// 가져온 재고 저장
				mre = rs.getInt(1);

				// 주문 수량보다 재고가 적다면
				if (count > mre) {
					// 남은 재고 개수와 함께 다시 시도해달라는 메시지 출력
					System.out.println("재고가 부족합니다." + ma[num - 1] + "의 현재 재고는 " + mre + "개 입니다.");
					System.out.println("다시 시도해주세요.");
					continue;
				} else {
					// orderList HashMap에 {메뉴, 수량} 쌍으로 저장
					orderList.put(ma[num - 1], count);

					// 총 가격 계산
					totalPrice += count * mprice;

					// 추가 주문 여부를 물어봄
					System.out.print("추가 주문하시겠습니까? (y: 예, n: 아니오) : ");
					ans = sc.next();

					// 혹시 대문자를 입력했을 경우를 대비
					if (ans.toLowerCase().equals("y")) {
						continue;
					} else {
						// 추가 주문을 하지 않을 경우 주소를 입력받고
						System.out.print("주소를 입력해주세요 : ");
						address = sc.next();
						break;
					}
				}
			}

			// 배달 주소와 총 비용을 출력해줌
			System.out.println("============");
			System.out.println("배달 주소 :" + address);
			System.out.println("총 비용 : " + totalPrice);

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

	// 사용자의 아이디를 전달받아
	// 해당 사용자의 잔액에 접근하기 위함
	// 결제 메소드
	public void payMoney(String cid) {
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// 사용자의 현재 잔액을 받아오기 위한 변수
			int balance = 0;

			// 넘겨받은 cid를 가진 사용자의 잔액을 가져옴
			String sql = "SELECT balance FROM customer WHERE cid = '" + cid + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			// 가져온 잔액을 변수에 저장
			balance = rs.getInt(1);

			// 잔액이 주문 금액보다 작을 경우 결제 실패
			// MemberView() 화면으로 돌아감
			if (balance < totalPrice) {
				System.out.println("잔액 부족! 잔액을 확인하고 다시시도해주세요.");
				new MemberView(cid);
			} else {
				// 잔액에서 총 가격만큼 빼준다
				balance -= totalPrice;
			}

			// 해당 cid를 가진 사용자의 잔액을 업데이트 해줌
			sql = "UPDATE customer SET balance = " + balance + " WHERE cid = '" + cid + "'";
			stmt.executeUpdate(sql);

			// 업데이트 된 잔액을 다시 가져옴
			sql = "SELECT balance FROM customer WHERE cid = '" + cid + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			// cid를 가진 사용자의 남은 잔액과 함께
			// 결제가 완료되었다는 메시지 출력
			System.out.println("결제가 완료되었습니다.");
			System.out.println("결제 후 " + cid + "님의 잔액은 " + rs.getInt(1) + "원 입니다.");
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
	
	// 결제 이후 메뉴 재고를 업데이트하기 위한 메소드 
	public void updateMenuRemain() {
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";
			String sql = null;
			
			// 현재 재고를 가져올 변수
			int mre = 0;
			
			// 수량을 저장할 변수
			int mcount = 0;

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			
			// HashMap에 저장된 값을 한 쌍씩 가져오기 위한 Iterator
			Iterator<Entry<String, Integer>> iter = orderList.entrySet().iterator();
			
			while (iter.hasNext()) {
				// {key:value} 한쌍이 entry에 저장
				Entry<String, Integer> entry = iter.next();

				// entry에 저장된 메뉴이름에 따른 해당 메뉴의 재고를 가져옴
				sql = "SELECT mre FROM menu WHERE menu = '" + entry.getKey() + "'";
				rs = stmt.executeQuery(sql);
				rs.next();
				
				// 가져온 재고를 저장
				mre = rs.getInt("mre");
				
				// entry에 저장된 해당 메뉴를 몇 개 주문했는지 수량을 가져옴
				mcount = entry.getValue();
				
				// 재고에서 수량 빼줌
				mre -= mcount;
				
				// 재고 업데이트
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
	

	// 주문내역 테이블에 주문내역을 저장하기 위한 메소드
	// 어떤 사용자의 주문인지 어떤 가게인지를 파라미터로 받아옴
	public void insertOrderList(String cid, int storeNum) {
		try {
			Class.forName(dName);

			String url = "jdbc:mysql://localhost:3307/delivery-service";
			String user = "root";
			String password = "1234";

			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();

			// HashMap에 저장된 값을 한 쌍씩 가져오기 위한 Iterator
			Iterator<Entry<String, Integer>> iter = orderList.entrySet().iterator();

			// store 테이블에서 해당 가게 이름을 가진 가게의 번호를 가져옴
			String sql = "SELECT snum FROM store WHERE sname = '" + sa[storeNum - 1] + "'";
			rs = stmt.executeQuery(sql);
			rs.next();

			// 가져온 가게 번호 저장
			int snum = rs.getInt(1);

			while (iter.hasNext()) {
				// {key:value} 한쌍이 entry에 저장
				Entry<String, Integer> entry = iter.next();

				// key에는 메뉴이름이 저장
				// 해당 메뉴이름을 가진 메뉴의 번호를 가져옴
				sql = "SELECT mnum FROM menu WHERE menu = '" + entry.getKey() + "'";
				rs = stmt.executeQuery(sql);
				rs.next();

				// 가져온 메뉴 번호를 저장
				int mnum = rs.getInt(1);

				// 주문 내역에 아래 정보들을 저장
				// entry.getValue()에는 수량이 들어있음
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
