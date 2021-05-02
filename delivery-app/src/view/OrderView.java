package view;

import java.util.Scanner;

import controller.MenuDAO;
import controller.StoreDAO;

// 주문 화면
public class OrderView {

	StoreDAO sd = new StoreDAO(); // 가게 데이터에 접근하기 위한 StoreDAO 객체 생성
	MenuDAO md = new MenuDAO(); // 메뉴 데이터에 접근하기 위한 MenuDAO 객체 생성
	Scanner sc = new Scanner(System.in);
	
	String[] sa = null; // store_array, 가게 목록을 저장하기 위한 String 배열
	String[] ma = null; // menu_array, 메뉴 목록을 저장하기 위한 String 배열

	public OrderView(String cid) {

		// 사용자 입력을 받기 위한 변수
		int num = 0; 
		String ans = null;
		
		System.out.println();
		System.out.println("음식 유형을 선택하세요.");
		sd.getFoodType(); // 음식 유형을 StoreDAO의 food_type HashSet에 저장
		sd.showFoodType(); // 음식 유형 목록을 보여주는 메소드
		System.out.print("입력 : ");
		num = sc.nextInt();
		md.initFoodTypeArray();
		System.out.println();
		md.initStoreArray(num);
		sa = sd.getStoreInfo(num); // sa에 가게 목록 저장
		sd.showStoreInfo(); // 가게이름 출력
		System.out.print("가게 선택 : ");
		num = sc.nextInt(); // 변수 재사용
		System.out.println();
		
		// 어떤 가게의 메뉴를 보여줄 것인지 알리기 위해
		// 사용자가 입력한 가게 번호를 같이 전달
		md.showMenus(num);
		
		// 어떤 사용자가 어떤 가게에 접근했는지 알기 위해
		// 사용자 아이디와 가게 번호를 같이 전달
		md.orderPhase(cid, num);
		System.out.print("결제하시겠습니까? (y: 예, n: 아니오) : ");

		ans = sc.next();

		// 결제를 한다고 했을 경우
		if (ans.toLowerCase().equals("y")) {
			md.payMoney(cid); // 결제를 진행하는 메소드 호출, 사용자 아이디 전달
			md.insertOrderList(cid, num); // 주문내역을 저장하기 위한 메소드
			new MemberView(cid); // 결제 완료 후 다시 사용자 화면 띄워줌
		} else { 
			// 결제 취소했을 경우
			// 어떠한 데이터의 조작도 없이
			// 다시 사용자 화면으로 돌아감
			System.out.println("결제가 취소되었습니다.");
			new MemberView(cid);
		}
		
		
	}
}
