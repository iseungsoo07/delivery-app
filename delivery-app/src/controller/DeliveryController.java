package controller;

import view.DeliveryView;

public class DeliveryController {
	DeliveryView dv;
	CustomerDAO cd;

	public DeliveryController() {
		
		new DBConnector();
		this.dv = new DeliveryView();
		
		
	}
}
