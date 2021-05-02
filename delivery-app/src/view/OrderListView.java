package view;

import controller.OrderListDAO;

// 주문 내역 화면
public class OrderListView {

	// 주문내역 테이블에 접근하기 위한 OrderListDAO 객체 생성
	OrderListDAO od = new OrderListDAO();
	
	// 생성자
	public OrderListView(String cid) {
		// 사용자에게 주문 내역을 보여주기 위한 메소드
		od.showOrderList(cid); 
		System.out.println();
		// 주문 내역을 보여준 뒤 다시 사용자 화면으로 돌아감
		new MemberView(cid);
	}
}
