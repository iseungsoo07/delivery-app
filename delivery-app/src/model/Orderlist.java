package model;

public class Orderlist {
	private String cid;
	private int snum;
	private int mnum;

	public Orderlist(String cid, int snum, int mnum) {
		super();
		this.cid = cid;
		this.snum = snum;
		this.mnum = mnum;
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

}
