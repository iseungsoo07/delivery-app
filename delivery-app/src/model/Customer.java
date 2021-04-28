package model;

public class Customer {
	private String cid;
	private int cpw;
	private String cname;
	private int cphone;

	public Customer(String cid, int cpw, String cname, int cphone) {
		super();
		this.cid = cid;
		this.cpw = cpw;
		this.cname = cname;
		this.cphone = cphone;
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

	@Override
	public String toString() {
		return this.cid + " / " + this.cpw + " / " + this.cname + " / " + this.cphone;
	}
}
