package model;

public class Menu {
	private int mnum;
	private String menu;
	private int snum;
	private int mre;

	public Menu(int mnum, String menu, int snum, int mre) {
		super();
		this.mnum = mnum;
		this.menu = menu;
		this.snum = snum;
		this.mre = mre;
	}

	public int getMnum() {
		return mnum;
	}

	public void setMnum(int mnum) {
		this.mnum = mnum;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public int getSnum() {
		return snum;
	}

	public void setSnum(int snum) {
		this.snum = snum;
	}

	public int getMre() {
		return mre;
	}

	public void setMre(int mre) {
		this.mre = mre;
	}

}
