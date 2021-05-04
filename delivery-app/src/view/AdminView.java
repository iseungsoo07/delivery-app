package view;

import java.util.Scanner;

import controller.CustomerDAO;
import controller.MenuDAO;
import controller.StoreDAO;

// 관리자 로그인 화면
public class AdminView {
	Scanner sc = new Scanner(System.in);
	MenuDAO md = new MenuDAO();
	CustomerDAO cd = new CustomerDAO();
	StoreDAO sd = new StoreDAO();

	int act;
	String sname;

	public AdminView() {

		while (true) {
			System.out.println("===관리자 메뉴===");
			System.out.println("1. 매장 추가   2. 메뉴 추가   3. 메뉴 삭제 ");
			System.out.println("4. 메뉴 조회  5. 메뉴 가격 변경   6. 재고 추가   7. 돌아가기");
			System.out.print("입력 : ");
			act = sc.nextInt();

			if(act < 1 || act > 7) {
				System.out.println("잘못된 입력입니다. 다시 입력하세요");
				continue;
			}
			
			if (act == 1) {

				String saddr = null;
				int sphone = 0;
				String food_type = null;

				System.out.println();
				System.out.println("===매장 추가 페이지===");

				System.out.print("사용하실 가게명을 입력해주세요 : ");
				sname = sc.next();
				sc.nextLine();
				System.out.print("가게 주소를 입력해주세요 : ");
				saddr = sc.nextLine();
				System.out.print("가게 전화번호를 입력해주세요 : ");
				sphone = sc.nextInt();
				System.out.print("음식 유형을 입력해주세요 : ");
				food_type = sc.next();
				sd.addStore(sname, saddr, sphone, food_type);

				System.out.println("가게 등록이 완료되었습니다.");
				System.out.println();

			} else if (act == 2) {

				System.out.println("===메뉴 추가 페이지===");

				System.out.print("가게 이름을 입력하세요 : ");
				sname = sc.next();

				md.addMenu(sname);
				System.out.println();

			} else if (act == 3) {

				System.out.println("===메뉴 삭제 페이지===");
				System.out.print("가게 이름을 입력하세요 : ");
				sname = sc.next();

				md.deleteMenu(sname);
				System.out.println();
			} else if (act == 4) {
				System.out.println("===메뉴 조회 페이지===");
				System.out.print("가게 이름을 입력하세요 : ");
				sname = sc.next();

				md.selectAllMenus(sname);
				System.out.println();
			} else if (act == 5) {
				System.out.println("===가격 변경 페이지===");
				System.out.print("가게 이름을 입력하세요 : ");
				sname = sc.next();

				md.changePrice(sname);
				System.out.println();
			} else if (act == 6) {
				System.out.println("===재고 추가 페이지===");
				System.out.print("가게 이름을 입력하세요 : ");
				sname = sc.next();
				
				md.addMenuRemain(sname);
				
				System.out.println();
			}

			else {
				new DeliveryView();
			}
		}

	}
}
