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
			System.out.println("===마이 페이지===");
			System.out.println("1. 잔액 조회   2. 잔액 추가    3. 돌아가기");
			System.out.print("입력 : ");
			act = sc.nextInt();
		
			if(act < 1 || act > 3) {
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
				continue;
			}
			else {
				break;
			}
			
		}
		
		if(act == 1) {
			int balance = cd.getBalance(myID);
			System.out.println(cid + "님의 현재 잔액 : " + balance + "원");
			System.out.println();
			new MyPageView(myID);
		} else if (act == 2) {
			int money = 0;
			System.out.print("추가할 금액을 입력하세요 : ");
			money = sc.nextInt();
			if(cd.addBalance(money, myID)) {
				System.out.println("입금 완료되었습니다.");
			} else {
				System.out.println("입금 실패!");
			}
			new MyPageView(myID);
		} else {
			new MemberView(myID);
		}
	}
}
