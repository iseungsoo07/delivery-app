package view;

import java.util.Scanner;

import controller.CustomerDAO;
import controller.MenuDAO;
import controller.StoreDAO;

// ������ �α��� ȭ��
public class AdminView {
	Scanner sc = new Scanner(System.in);
	MenuDAO md = new MenuDAO();
	CustomerDAO cd = new CustomerDAO();
	StoreDAO sd = new StoreDAO();

	int act;
	String sname;

	public AdminView() {

		while (true) {
			System.out.println("===������ �޴�===");
			System.out.println("1. ���� �߰�   2. �޴� �߰�   3. �޴� ���� ");
			System.out.println("4. �޴� ��ȸ  5. �޴� ���� ����   6. ��� �߰�   7. ���ư���");
			System.out.print("�Է� : ");
			act = sc.nextInt();

			if(act < 1 || act > 7) {
				System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��ϼ���");
				continue;
			}
			
			if (act == 1) {

				String saddr = null;
				int sphone = 0;
				String food_type = null;

				System.out.println();
				System.out.println("===���� �߰� ������===");

				System.out.print("����Ͻ� ���Ը��� �Է����ּ��� : ");
				sname = sc.next();
				sc.nextLine();
				System.out.print("���� �ּҸ� �Է����ּ��� : ");
				saddr = sc.nextLine();
				System.out.print("���� ��ȭ��ȣ�� �Է����ּ��� : ");
				sphone = sc.nextInt();
				System.out.print("���� ������ �Է����ּ��� : ");
				food_type = sc.next();
				sd.addStore(sname, saddr, sphone, food_type);

				System.out.println("���� ����� �Ϸ�Ǿ����ϴ�.");
				System.out.println();

			} else if (act == 2) {

				System.out.println("===�޴� �߰� ������===");

				System.out.print("���� �̸��� �Է��ϼ��� : ");
				sname = sc.next();

				md.addMenu(sname);
				System.out.println();

			} else if (act == 3) {

				System.out.println("===�޴� ���� ������===");
				System.out.print("���� �̸��� �Է��ϼ��� : ");
				sname = sc.next();

				md.deleteMenu(sname);
				System.out.println();
			} else if (act == 4) {
				System.out.println("===�޴� ��ȸ ������===");
				System.out.print("���� �̸��� �Է��ϼ��� : ");
				sname = sc.next();

				md.selectAllMenus(sname);
				System.out.println();
			} else if (act == 5) {
				System.out.println("===���� ���� ������===");
				System.out.print("���� �̸��� �Է��ϼ��� : ");
				sname = sc.next();

				md.changePrice(sname);
				System.out.println();
			} else if (act == 6) {
				System.out.println("===��� �߰� ������===");
				System.out.print("���� �̸��� �Է��ϼ��� : ");
				sname = sc.next();
				
				md.addMenuRemain(sname);
				
				System.out.println();
			}

			else {
				new DeliveryView();
			}
		}

	}
}
