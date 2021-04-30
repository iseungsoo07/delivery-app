package view;

import java.util.Scanner;

public class MemberView {
	Scanner sc = new Scanner(System.in);
	int act;

	public MemberView(String cid) {
		
		String myID = cid;

		while (true) {
			System.out.println("===사용자 메뉴===");
			System.out.println("1. 주문하기    2. 주문 내역    3. 마이페이지   4. 돌아가기 ");
			System.out.print("입력 : ");
			act = sc.nextInt();

			if (act < 1 || act > 4) {
				System.out.println("잘못된 입력입니다. 다시입력하세요.");
				continue;
			} else {
				break;
			}
		}

		if (act == 1) {
			new OrderView(myID);
		} else if(act == 2) {
			
		} else if(act == 3) {
			new MyPageView(myID);
		} else {
			new DeliveryView();
		}
		
	}
}
