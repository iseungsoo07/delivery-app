package view;

import java.util.Scanner;

import controller.MenuDAO;
import controller.StoreDAO;

// �ֹ� ȭ��
public class OrderView {

	StoreDAO sd = new StoreDAO(); // ���� �����Ϳ� �����ϱ� ���� StoreDAO ��ü ����
	MenuDAO md = new MenuDAO(); // �޴� �����Ϳ� �����ϱ� ���� MenuDAO ��ü ����
	Scanner sc = new Scanner(System.in);
	
	String[] sa = null; // store_array, ���� ����� �����ϱ� ���� String �迭
	String[] ma = null; // menu_array, �޴� ����� �����ϱ� ���� String �迭

	public OrderView(String cid) {

		// ����� �Է��� �ޱ� ���� ����
		int num = 0; 
		String ans = null;
		
		System.out.println();
		System.out.println("���� ������ �����ϼ���.");
		sd.getFoodType(); // ���� ������ StoreDAO�� food_type HashSet�� ����
		sd.showFoodType(); // ���� ���� ����� �����ִ� �޼ҵ�
		System.out.print("�Է� : ");
		num = sc.nextInt();
		md.initFoodTypeArray();
		System.out.println();
		md.initStoreArray(num);
		sa = sd.getStoreInfo(num); // sa�� ���� ��� ����
		sd.showStoreInfo(); // �����̸� ���
		System.out.print("���� ���� : ");
		num = sc.nextInt(); // ���� ����
		System.out.println();
		
		// � ������ �޴��� ������ ������ �˸��� ����
		// ����ڰ� �Է��� ���� ��ȣ�� ���� ����
		md.showMenus(num);
		
		// � ����ڰ� � ���Կ� �����ߴ��� �˱� ����
		// ����� ���̵�� ���� ��ȣ�� ���� ����
		md.orderPhase(cid, num);
		System.out.print("�����Ͻðڽ��ϱ�? (y: ��, n: �ƴϿ�) : ");

		ans = sc.next();

		// ������ �Ѵٰ� ���� ���
		if (ans.toLowerCase().equals("y")) {
			md.payMoney(cid); // ������ �����ϴ� �޼ҵ� ȣ��, ����� ���̵� ����
			md.insertOrderList(cid, num); // �ֹ������� �����ϱ� ���� �޼ҵ�
			new MemberView(cid); // ���� �Ϸ� �� �ٽ� ����� ȭ�� �����
		} else { 
			// ���� ������� ���
			// ��� �������� ���۵� ����
			// �ٽ� ����� ȭ������ ���ư�
			System.out.println("������ ��ҵǾ����ϴ�.");
			new MemberView(cid);
		}
		
		
	}
}
