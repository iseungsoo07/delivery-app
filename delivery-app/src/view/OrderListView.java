package view;

import controller.OrderListDAO;

// �ֹ� ���� ȭ��
public class OrderListView {

	// �ֹ����� ���̺� �����ϱ� ���� OrderListDAO ��ü ����
	OrderListDAO od = new OrderListDAO();
	
	// ������
	public OrderListView(String cid) {
		// ����ڿ��� �ֹ� ������ �����ֱ� ���� �޼ҵ�
		od.showOrderList(cid); 
		System.out.println();
		// �ֹ� ������ ������ �� �ٽ� ����� ȭ������ ���ư�
		new MemberView(cid);
	}
}
