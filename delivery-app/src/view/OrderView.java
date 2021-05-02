package view;

import java.util.Scanner;

import controller.MenuDAO;
import controller.StoreDAO;

public class OrderView {

	StoreDAO sd = new StoreDAO();
	MenuDAO md = new MenuDAO();
	Scanner sc = new Scanner(System.in);
	
	String[] sa = null;
	String[] ma = null;

	public OrderView(String cid) {

		int num = 0;
		String ans = null;
		System.out.println();
		System.out.println("���� ������ �����ϼ���.");
		sd.getFoodType();
		sd.showFoodType();
		System.out.print("�Է� : ");
		num = sc.nextInt();
		md.initFoodTypeArray();
		System.out.println();
		md.initStoreArray(num);
		sa = sd.getStoreInfo(num);
		sd.showStoreInfo();
		System.out.print("���� ���� : ");
		num = sc.nextInt();
		System.out.println();
		md.showMenus(num);
		md.orderPhase(cid, num);
		System.out.print("�����Ͻðڽ��ϱ�? (y: ��, n: �ƴϿ�) : ");

		ans = sc.next();

		if (ans.toLowerCase().equals("y")) {
			md.payMoney(cid);
			md.insertOrderList(cid, num);
			new MemberView(cid);
		} else {
			System.out.println("������ ��ҵǾ����ϴ�.");
			new MemberView(cid);
		}
		
		
	}
}
