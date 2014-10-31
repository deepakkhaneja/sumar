package SuperMarket;

import java.io.Serializable;

class Customer implements Serializable {
	private String resAdd;
	private long telNo;
	private String dl;
	private int purchase;
	private int CN; //4-digit number
	private static final long serialVersionUID = 98599734567L;	
	
	public Customer(int CN, String resAdd, long telNo, String dl) {
		purchase = 0;
		this.CN = CN;
		this.resAdd = resAdd;
		this.dl = dl;
		this.telNo = telNo;
	}
	
	public String getResAdd() {
		return resAdd;
	}

	public long getTelNo() {
		return telNo;
	}

	public String getDl() {
		return dl;
	}

	public int getPurchase() {
		return purchase;
	}

	public void setPurchase(int purchase) {
		this.purchase = purchase;
	}

	public int getCN() {
		return CN;
	}

}
