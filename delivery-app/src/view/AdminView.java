package view;

import java.util.Scanner;

import controller.MenuDAO;

// 관리자 로그인 화면
public class AdminView {
	Scanner sc = new Scanner(System.in);
	MenuDAO md = new MenuDAO();
	int act;
	
	public AdminView() {
		
		while(true) {
			System.out.println("===관리자 메뉴===");
			System.out.println("1. 매장 추가  ");
			act = sc.nextInt();
			
			if(act == 1) {
				
				
			}
		}
	
	}
}
