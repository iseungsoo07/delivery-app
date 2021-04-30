package view;

import java.util.Scanner;

import controller.CustomerDAO;

public class MyPageView {
	Scanner sc = new Scanner(System.in);
	int act = 0;
	CustomerDAO cd = new CustomerDAO();
	
	public MyPageView(String cid) {
		
		String myID = cid;
		
		while(true) {
			System.out.println("===���� ������===");
			System.out.println("1. �ܾ� ��ȸ   2. �ܾ� �߰�    3. ���ư���");
			System.out.print("�Է� : ");
			act = sc.nextInt();
		
			if(act < 1 || act > 3) {
				System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��ϼ���.");
				continue;
			}
			else {
				break;
			}
			
		}
		
		if(act == 1) {
			int balance = cd.getBalance(myID);
			System.out.println(cid + "���� ���� �ܾ� : " + balance + "��");
			System.out.println();
			new MyPageView(myID);
		} else if (act == 2) {
			int money = 0;
			System.out.print("�߰��� �ݾ��� �Է��ϼ��� : ");
			money = sc.nextInt();
			if(cd.addBalance(money, myID)) {
				System.out.println("�Ա� �Ϸ�Ǿ����ϴ�.");
			} else {
				System.out.println("�Ա� ����!");
			}
			new MyPageView(myID);
		} else {
			new MemberView(myID);
		}
	}
}
