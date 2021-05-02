package view;

import java.util.Scanner;

// 사용자 로그인 화면
public class MemberView {
	Scanner sc = new Scanner(System.in);
	int act;

	// DeliveryView에서 cid를 넘겨받음
	public MemberView(String cid) {
		
		// 다른 페이지에 넘길 cid를 저장
		String myID = cid;

		while (true) {
			System.out.println("===사용자 메뉴===");
			System.out.println("1. 주문하기    2. 주문 내역    3. 마이페이지   4. 돌아가기 ");
			System.out.print("입력 : ");
			act = sc.nextInt();
			
			// 사용자 입력 유효성 검사
			if (act < 1 || act > 4) {
				System.out.println("잘못된 입력입니다. 다시입력하세요.");
				continue;
			} else {
				break;
			}
		}

		// 주문하기, 주문 내역, 마이페이지 전부 로그인한 사용자의 정보를 넘겨줘야 하므로
		// cid를 전달
		
		if (act == 1) {
			new OrderView(myID); // 1. 주문하기 선택 시 주문하기 화면을 보여주는 OrderView() 생성자 호출
		} else if(act == 2) {
			new OrderListView(myID); // 2. 주문 내역 선택 시 주문내역 화면을 보여주는 OrderListView() 생성자 호출
		} else if(act == 3) {
			new MyPageView(myID); // 3. 마이페이지 선택 시 마이페이지 화면을 보여주는 MyPageView() 생성자 호출
		} else {
			new DeliveryView(); // 4. 돌아가기 선택 시 다시 로그인, 회원가입이 나오는 화면으로 돌아가도록 DeliveryView() 생성자 
		}
		
	}
}
