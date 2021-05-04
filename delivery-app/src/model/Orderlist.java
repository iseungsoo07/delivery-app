package model;

public class Orderlist {
	private String cid;
	private int snum;
	private int mnum;
	private int mcount;
	private String order_date;
	
	public Orderlist() {;}

	public Orderlist(String cid, int snum, int mnum, int mcount) {
		super();
		this.cid = cid;
		this.snum = snum;
		this.mnum = mnum;
		this.mcount = mcount;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public int getSnum() {
		return snum;
	}

	public void setSnum(int snum) {
		this.snum = snum;
	}

	public int getMnum() {
		return mnum;
	}

	public void setMnum(int mnum) {
		this.mnum = mnum;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public int getMcount() {
		return mcount;
	}

	public void setMcount(int mcount) {
		this.mcount = mcount;
	}

}
