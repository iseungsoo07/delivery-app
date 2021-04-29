package view;

import java.util.Scanner;

import controller.CustomerDAO;

public class DeliveryView {
	Scanner sc = new Scanner(System.in);
	int act;
	CustomerDAO cd = new CustomerDAO();

	public DeliveryView() {
		while(true) {
			System.out.println("1. �α���   2. ȸ������   3. ����");
			System.out.print("�Է� : ");
			
			act = sc.nextInt();
			
			if(act == 3) {
				break;
			} else if(act == 1) {
				// �α���
				System.out.println("�α����� ...");
				System.out.print("���̵� : ");
				String cid = sc.next();
				System.out.print("��й�ȣ : ");
				int cpw = sc.nextInt();
				if(cd.signIn(cid, cpw)) {
					System.out.println("�α��� ����!");
					break;
				} else {
					System.out.println("�α��� ����! ���̵� ��й�ȣ�� �ٽ� Ȯ���ϼ���.");
				}
			} else {
				// ȸ������
				String cid = null;
				int cpw = 0;
				String cname = null;
				int cphone = 0;
				
				System.out.println("ȸ�������� ...");
				System.out.print("���̵� : ");
				cid = sc.next();
				System.out.print("��й�ȣ : ");
				cpw = sc.nextInt();
				System.out.print("�̸� : ");
				cname = sc.next();
				System.out.print("�ڵ��� ��ȣ : ");
				cphone = sc.nextInt();
				
				if(cd.signUp(cid, cpw, cname, cphone)) {
					System.out.println("ȸ������ ����!");
				} else {
					System.out.println("ȸ�����Կ� �����ϼ̽��ϴ�. �ٽ� �õ��ϼ���");
					continue;
				}
				
			}
			
			if(act < 1 || act > 3) {
				System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��ϼ���.");
				continue;
			}
		}
		
		if(act == 1) {
			new MemberView();
		}
	}
}
