package view;

import java.util.Scanner;

import controller.CustomerDAO;

// ���� ������ ȭ��
public class MyPageView {
	Scanner sc = new Scanner(System.in);
	int act; // ������� �Է��� �ޱ� ���� ����
	CustomerDAO cd = new CustomerDAO(); // �� ���̺� �����ϱ� ���� ��ü ����
	
	// ������
	public MyPageView(String cid) {

		// ���޹��� cid�� ����
		String myID = cid;
		
		while(true) {
			System.out.println("===���� ������===");
			System.out.println("1. �ܾ� ��ȸ   2. �ܾ� �߰�    3. ���ư���");
			System.out.print("�Է� : ");
			act = sc.nextInt();
		
			// ����� �Է� ��ȿ�� �˻�
			if(act < 1 || act > 3) {
				System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��ϼ���.");
				continue;
			}
			else {
				break;
			}
			
		}
		
		// 1. �ܾ���ȸ ���� ��
		if(act == 1) {
			// CustomerDAO�� getBalance �޼ҵ� ȣ��
			// ��ȯ ���� balance�� ����
			int balance = cd.getBalance(myID); 
			System.out.println(cid + "���� ���� �ܾ� : " + balance + "��");
			System.out.println();
			
			// �ܾ� ��ȸ ���� �ٽ� ���������� ȭ������ �̵�
			new MyPageView(myID);
		} else if (act == 2) { // 2. �ܾ� �߰� ���� ��
			int money = 0; // �߰��� �ݾ��� �Է¹��� ����
			System.out.print("�߰��� �ݾ��� �Է��ϼ��� : ");
			money = sc.nextInt(); 
			
			// ����� ���̵�� �Է¹��� money���� ���ڷ� addBalance() �޼ҵ� ȣ��
			if(cd.addBalance(money, myID)) {
				// true ��ȯ �� ���� �޽���
				System.out.println("�Ա� �Ϸ�Ǿ����ϴ�.");
			} else {
				// false ��ȯ �� ���� �޽���
				System.out.println("�Ա� ����!");
			}
			
			// ���� �ٽ� ���������� ȭ������ �̵� 
			new MyPageView(myID);
		} else {
			// 3. ���ư��� ���� �� �ٽ� ����� ȭ������ �̵�
			new MemberView(myID);
		}
	}
}
