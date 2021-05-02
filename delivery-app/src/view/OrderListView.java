package view;

import controller.OrderListDAO;

public class OrderListView {
	OrderListDAO od = new OrderListDAO();
	
	public OrderListView(String cid) {
		od.showOrderList(cid);
		System.out.println();
		new MemberView(cid);
	}
}
