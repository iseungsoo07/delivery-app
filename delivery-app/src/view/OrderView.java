package view;

import java.util.Scanner;

import controller.StoreDAO;

public class OrderView {

	StoreDAO sd = new StoreDAO();
	Scanner sc = new Scanner(System.in);

	public OrderView() {
		while (true) {
			int type = 0;
			System.out.println();
			System.out.println("음식 유형을 선택하세요.");
			sd.getFoodType();
			System.out.print("입력 : ");
			type = sc.nextInt();
			System.out.println();
			sd.getStoreInfo(type);
			System.out.print("가게 선택 : ");
			break;
		}
	}
}
