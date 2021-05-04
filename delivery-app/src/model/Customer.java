package model;

public class Customer {
	private String cid;
	private int cpw;
	private String cname;
	private int cphone;
	private int balance;
	
	public Customer() {;}

	public Customer(String cid, int cpw, String cname, int cphone) {
		super();
		this.cid = cid;
		this.cpw = cpw;
		this.cname = cname;
		this.cphone = cphone;
		this.balance = 0;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public int getCpw() {
		return cpw;
	}

	public void setCpw(int cpw) {
		this.cpw = cpw;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public int getCphone() {
		return cphone;
	}

	public void setCphone(int cphone) {
		this.cphone = cphone;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

}
