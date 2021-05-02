package controller;

import view.DeliveryView;

// 메인에서 실행할 컨트롤러
public class DeliveryController {
	DeliveryView dv; // 처음 화면을 띄워줄 뷰

	public DeliveryController() {
		
		new DBConnector(); // 프로그램 시작시 디비 연결
		this.dv = new DeliveryView(); // DeliveryView 생성자 호출
		
		
	}
}
