package view;

import java.util.Scanner;

import controller.MenuDAO;

// ������ �α��� ȭ��
public class AdminView {
	Scanner sc = new Scanner(System.in);
	MenuDAO md = new MenuDAO();
	int act;
	
	public AdminView() {
		
		while(true) {
			System.out.println("===������ �޴�===");
			System.out.println("1. ���� �߰�  ");
			act = sc.nextInt();
			
			if(act == 1) {
				
				
			}
		}
	
	}
}
