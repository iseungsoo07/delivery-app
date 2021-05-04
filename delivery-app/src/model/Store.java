package model;

public class Store {
	private int snum;
	private String sname;
	private String saddr;
	private int sphone;
	private String food_type;

	public Store() 	{;}
	
	public Store(int snum, String sname, String saddr, int sphone, String menu_type) {
		super();
		this.snum = snum;
		this.sname = sname;
		this.saddr = saddr;
		this.sphone = sphone;
		this.food_type = menu_type;
	}

	public int getSnum() {
		return snum;
	}

	public void setSnum(int snum) {
		this.snum = snum;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSaddr() {
		return saddr;
	}

	public void setSaddr(String saddr) {
		this.saddr = saddr;
	}

	public int getSphone() {
		return sphone;
	}

	public void setSphone(int sphone) {
		this.sphone = sphone;
	}

	public String getFood_type() {
		return food_type;
	}

	public void setFood_type(String menu_type) {
		this.food_type = menu_type;
	}

}
