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
			System.out.println("���� ������ �����ϼ���.");
			sd.getFoodType();
			System.out.print("�Է� : ");
			type = sc.nextInt();
			System.out.println();
			sd.getStoreInfo(type);
			System.out.print("���� ���� : ");
			break;
		}
	}
}
