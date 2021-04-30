package view;

import java.util.Scanner;

import controller.MenuDAO;
import controller.StoreDAO;

public class OrderView {

	StoreDAO sd = new StoreDAO();
	MenuDAO md = new MenuDAO();
	Scanner sc = new Scanner(System.in);

	public OrderView(String cid) {
		while (true) {
			int num = 0;
			String ans = null;
			System.out.println();
			System.out.println("음식 유형을 선택하세요.");
			sd.getFoodType();
			sd.showFoodType();
			System.out.print("입력 : ");
			num = sc.nextInt();
			md.initFoodTypeArray();
			System.out.println();
			md.initStoreArray(num);
			sd.getStoreInfo(num);
			sd.showStoreInfo();
			System.out.print("가게 선택 : ");
			num = sc.nextInt();
			System.out.println();
			md.showMenus(num);
			md.orderPhase();
			System.out.print("결제하시겠습니까? (y: 예, n: 아니오) : ");
			ans = sc.next();
			
			if(ans.toLowerCase().equals("y")) {
				md.payMoney(cid);
				new MemberView(cid);
			} else {
				System.out.println("결제가 취소되었습니다.");
				new MemberView(cid);
			}
			
			break;
		}
	}
}
