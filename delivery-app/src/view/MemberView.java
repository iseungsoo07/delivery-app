package view;

import java.util.Scanner;

public class MemberView {
	Scanner sc = new Scanner(System.in);
	int act;

	public MemberView(String cid) {
		
		String myID = cid;

		while (true) {
			System.out.println("===����� �޴�===");
			System.out.println("1. �ֹ��ϱ�    2. �ֹ� ����    3. ����������   4. ���ư��� ");
			System.out.print("�Է� : ");
			act = sc.nextInt();

			if (act < 1 || act > 4) {
				System.out.println("�߸��� �Է��Դϴ�. �ٽ��Է��ϼ���.");
				continue;
			} else {
				break;
			}
		}

		if (act == 1) {
			new OrderView(myID);
		} else if(act == 2) {
			
		} else if(act == 3) {
			new MyPageView(myID);
		} else {
			new DeliveryView();
		}
		
	}
}
