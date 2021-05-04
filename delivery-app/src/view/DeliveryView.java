package view;

import java.util.Scanner;

import controller.CustomerDAO;

// ���α׷� ���� �� ó�� ������ ȭ��
public class DeliveryView {
	Scanner sc = new Scanner(System.in);
	int act; // ����� �Է��� �ޱ� ���� ����
	CustomerDAO cd = new CustomerDAO(); // �� ���̺�� ������ �ְ�ޱ� ����
	
	String cid; // ���� �������� ����� ���̵� �����ϱ� ���� ����
	boolean isAdmin; // �����ڷ� �α��� ���� �� ���������� Ȯ���ϱ� ���� ����

	public DeliveryView() {
		while (true) {
			System.out.println("===delivery-service===");
			System.out.println("1. �α���   2. ȸ������   3. ����");
			System.out.print("�Է� : ");

			act = sc.nextInt();

			if (act == 3) {
				break;
			} else if (act == 1) {
				// �α���
				// �α��� �ÿ��� ����� ���̵� ������ ������ �ٸ� �������� �Ѿ�� ����
				// Ŭ���� ���� cid�� ���
				System.out.println("�α����� ...");
				System.out.print("���̵� : ");
				this.cid = sc.next();
				System.out.print("��й�ȣ : ");
				int cpw = sc.nextInt();
				if (cd.signIn(this.cid, cpw)) { // CustomerDAO signIn() �޼ҵ� ȣ��
					if (cd.isAdmin(this.cid, cpw)) { // ������ id, pw�� �α��� ��
						System.out.println("������ �α��� ����!");
						isAdmin = true; // ���������� Ȯ���ϴ� flag ���� ����
					} else {
						System.out.println("����� �α��� ����!");
					}
					break;
				} else {
					System.out.println("�α��� ����! ���̵� ��й�ȣ�� �ٽ� Ȯ���ϼ���.");
				}
			} else {
				// ȸ������
				String cid = null; // Ŭ���� ������ �ƴ� �������� ����
				int cpw = 0;
				String cname = null;
				int cphone = 0;

				while (true) {
					System.out.println("ȸ�������� ...");
					while (true) {
						System.out.print("���̵� : ");
						cid = sc.next();
						
						// ȸ�����Խ� �Է¹��� ���̵� ���ڷ� CustomerDAO�� checkCID() �޼ҵ� ȣ��
						// �ߺ��Ǵ� ���̵� �ִٸ� true, �ƴϸ� false ��ȯ
						if (cd.checkCID(cid)) { 
							System.out.println("�̹� �����ϴ� ���̵� �Դϴ�. �ٽ� �õ��ϼ���.");
							continue;
						} else {
							System.out.print("��й�ȣ : ");
							cpw = sc.nextInt();
							System.out.print("�̸� : ");
							cname = sc.next();
							System.out.print("�ڵ��� ��ȣ : ");
							cphone = sc.nextInt();
							break;
						}
					}

					// �Է¹��� ������ ���ڷ� signUP() �޼ҵ� ȣ�� 
					// ȸ������ ������ true, ���н� false ��ȯ
					if (cd.signUp(cid, cpw, cname, cphone)) {
						System.out.println("ȸ������ ����!");
						break;
					} else {
						System.out.println("ȸ������ ����! �ٽýõ��ϼ���.");
						continue;
					}
				}

			}
			
			// ����� �Է� ��ȿ�� �˻�
			if (act < 1 || act > 3) {
				System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��ϼ���.");
				continue;
			}
		}

		// �α����� �������� ��
		// �����ڷ� ���ٽ� AdminView() ������ ȣ��
		// ����ڷ� ���ٽ� MemberView() ������ ȣ��
		// �� �� �α����� ����ڰ� �������� �˱� ���� Ŭ���� ���� cid ���
		if (act == 1) {
			if(!isAdmin) {
				new MemberView(this.cid);
			} else {
				new AdminView();
			}
		}
	}
}
