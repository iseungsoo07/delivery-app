package view;

import java.util.Scanner;

// ����� �α��� ȭ��
public class MemberView {
	Scanner sc = new Scanner(System.in);
	int act;

	// DeliveryView���� cid�� �Ѱܹ���
	public MemberView(String cid) {
		
		// �ٸ� �������� �ѱ� cid�� ����
		String myID = cid;

		while (true) {
			System.out.println("===����� �޴�===");
			System.out.println("1. �ֹ��ϱ�    2. �ֹ� ����    3. ����������   4. ���ư��� ");
			System.out.print("�Է� : ");
			act = sc.nextInt();
			
			// ����� �Է� ��ȿ�� �˻�
			if (act < 1 || act > 4) {
				System.out.println("�߸��� �Է��Դϴ�. �ٽ��Է��ϼ���.");
				continue;
			} else {
				break;
			}
		}

		// �ֹ��ϱ�, �ֹ� ����, ���������� ���� �α����� ������� ������ �Ѱ���� �ϹǷ�
		// cid�� ����
		
		if (act == 1) {
			new OrderView(myID); // 1. �ֹ��ϱ� ���� �� �ֹ��ϱ� ȭ���� �����ִ� OrderView() ������ ȣ��
		} else if(act == 2) {
			new OrderListView(myID); // 2. �ֹ� ���� ���� �� �ֹ����� ȭ���� �����ִ� OrderListView() ������ ȣ��
		} else if(act == 3) {
			new MyPageView(myID); // 3. ���������� ���� �� ���������� ȭ���� �����ִ� MyPageView() ������ ȣ��
		} else {
			new DeliveryView(); // 4. ���ư��� ���� �� �ٽ� �α���, ȸ�������� ������ ȭ������ ���ư����� DeliveryView() ������ 
		}
		
	}
}
