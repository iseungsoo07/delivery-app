package controller;

import view.DeliveryView;

// ���ο��� ������ ��Ʈ�ѷ�
public class DeliveryController {
	DeliveryView dv; // ó�� ȭ���� ����� ��

	public DeliveryController() {
		
		new DBConnector(); // ���α׷� ���۽� ��� ����
		this.dv = new DeliveryView(); // DeliveryView ������ ȣ��
		
		
	}
}
