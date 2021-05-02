package view;

import java.util.Scanner;

import controller.CustomerDAO;

// 마이 페이지 화면
public class MyPageView {
	Scanner sc = new Scanner(System.in);
	int act; // 사용자의 입력을 받기 위한 변수
	CustomerDAO cd = new CustomerDAO(); // 고객 테이블에 접근하기 위한 객체 생성
	
	// 생성자
	public MyPageView(String cid) {

		// 전달받은 cid를 저장
		String myID = cid;
		
		while(true) {
			System.out.println("===마이 페이지===");
			System.out.println("1. 잔액 조회   2. 잔액 추가    3. 돌아가기");
			System.out.print("입력 : ");
			act = sc.nextInt();
		
			// 사용자 입력 유효성 검사
			if(act < 1 || act > 3) {
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
				continue;
			}
			else {
				break;
			}
			
		}
		
		// 1. 잔액조회 선택 시
		if(act == 1) {
			// CustomerDAO의 getBalance 메소드 호출
			// 반환 값을 balance에 저장
			int balance = cd.getBalance(myID); 
			System.out.println(cid + "님의 현재 잔액 : " + balance + "원");
			System.out.println();
			
			// 잔액 조회 이후 다시 마이페이지 화면으로 이동
			new MyPageView(myID);
		} else if (act == 2) { // 2. 잔액 추가 선택 시
			int money = 0; // 추가할 금액을 입력받을 변수
			System.out.print("추가할 금액을 입력하세요 : ");
			money = sc.nextInt(); 
			
			// 사용자 아이디와 입력받은 money값을 인자로 addBalance() 메소드 호출
			if(cd.addBalance(money, myID)) {
				// true 반환 시 성공 메시지
				System.out.println("입금 완료되었습니다.");
			} else {
				// false 반환 시 실패 메시지
				System.out.println("입금 실패!");
			}
			
			// 이후 다시 마이페이지 화면으로 이동 
			new MyPageView(myID);
		} else {
			// 3. 돌아가기 선택 시 다시 사용자 화면으로 이동
			new MemberView(myID);
		}
	}
}
