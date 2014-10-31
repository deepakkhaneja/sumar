package SuperMarket;

import java.io.Serializable;

class SalesManager implements Serializable {
	private static final int MAX_CUSTOMER = 9000, MAX_YEAR = 40; 	
	private int noOfCustomers;
	private int available[] = new int[MAX_CUSTOMER];	
	private int rank[] = new int[MAX_CUSTOMER]; //sorted CN in decreasing order of purchase
	private int purchase[] = new int[MAX_CUSTOMER]; //purchse value indexed by CN
	private int noOfYears; //no of years from the start of this software
	private int yearlySales[] = new int[MAX_YEAR]; //current year no = noOfYears
	private String password;
	private Customer c[] = new Customer[MAX_CUSTOMER]; 
	private static final long serialVersionUID = 98599739738L;
	
	public SalesManager() {
		noOfCustomers = 0;
		noOfYears = 1;
		password = "SuperMarket";
		for(int i = 0; i < MAX_CUSTOMER; i++) {
			available[i] = 1000 + i;
		}
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean existsCN(int CN) {
		for(int i = 0; i < noOfCustomers; i++) {
			if(rank[i] == CN) return true;
		}
		return false;
	}
	
	public void incrementNoOfYears() {
		noOfYears++; 
		System.out.println("Years incremented");		
	}

	private int index(int CN) {		
		return CN - 1000;
	}
	
	private int searchRank(int CN) {
		
		for(int i = 0; i < noOfCustomers; i++)
			if(rank[i] == CN) return i; 
		return -1;
	} 

	private int searchCustomer(int CN) {
		
		for(int i = 0; i < noOfCustomers; i++)
			if(c[i].getCN() == CN) return i; 
		return -1;
	} 

	//CN = available[0];
	//while adding a customer its purchase = 0 minimum
	public int addCustomer(String resAdd, long telNo, String dl) {
		int lastAvailable = MAX_CUSTOMER - noOfCustomers - 1;
		int tmp, CN;
		CN = available[0];
		tmp = available[lastAvailable];
		available[lastAvailable] = available[0];
		available[0] = tmp;
		noOfCustomers++;
		rank[noOfCustomers - 1] = CN;
		purchase[index(CN)] = 0;
		c[noOfCustomers - 1] = new Customer(CN, resAdd, telNo, dl);
		return CN;
	}

	
	public void deleteCustomer(int CN) {
		int firstNotAvailable = MAX_CUSTOMER - noOfCustomers;
		int tmp, i;
		for(i = firstNotAvailable; i < MAX_CUSTOMER; i++) {
			if(available[i] == CN) break;
		}
		tmp = available[i];
		available[i] = available[firstNotAvailable];
		available[firstNotAvailable] = tmp;
		
		i = searchRank(CN);
		
		for(int j = i+1; j < noOfCustomers; j++) {
			rank[j - 1] = rank[j];
		}		
		
		i = searchCustomer(CN);
		c[i] = c[noOfCustomers - 1];
		c[noOfCustomers - 1] = null;
		noOfCustomers--;
		System.out.println("Customer Number(CN) " + CN + " is deleted");
	}
	
	public int updatePurchase(int CN, int purchaseAdded) {
		int i = searchRank(CN);
		int j;
		purchase[index(CN)] += purchaseAdded;		
		for(j = 0; j < i; j++) {
			if(purchase[index(rank[j])] < purchase[index(CN)]) break;
		}
		
		for(int k = i; k > j; k--) {
			rank[k] = rank[k - 1];
		}
		
		rank[j] = CN;
		
		yearlySales[noOfYears - 1] += purchaseAdded;
		
		i = searchCustomer(CN);
		c[i].setPurchase(purchase[index(CN)]); 
		return purchase[index(CN)];
	}
	
	public void resetPurchase() {
		for(int i = 0; i < noOfCustomers; i++) {
			purchase[index(rank[i])] = 0;
			c[i].setPurchase(0);
		}
		System.out.println("All customers purchases set to zero");		
	}

	public void printCustomer(int CN, int i) {
		int j = searchCustomer(CN);
		System.out.println("");
		System.out.printf("%-6s", i + ") ");
		System.out.println("CN : " + CN + "  Purchase : " + c[j].getPurchase());		
		System.out.println("Residence address : " + c[j].getResAdd());
		System.out.println("Telephone Number: " + c[j].getTelNo()); 
		System.out.println("Driving License Number: " + c[j].getDl());		
	}
	
	public void printWinnerList() {
		int p = (noOfCustomers > 10)? 10 : noOfCustomers;
	
		System.out.println("");
		System.out.println("Prize Winner Customers with Highest Total Purchase :");
		for(int i = 0; i < p; i++) {
			printCustomer(rank[i], i + 1);
		}
		
		System.out.println("\n\n22 Caret Gold Coin Winner Customers with Total Purchase exceeding Rs.10000 :");
		for(int i = 0; i < noOfCustomers; i++) {
			if(purchase[index(rank[i])] < 10000) break;
			printCustomer(rank[i], i + 1);
		}
	}
	
	public void printYearlySales() {
		System.out.println("Annual Sales Positions :");		
		for(int i = 0; i < noOfYears; i++) {
			System.out.println((2012 + i) + " : Rs." + yearlySales[i]);
		}
	}
	
	public void printCustomerDetails() {
		System.out.println("No. of registered customers : " + noOfCustomers);		
		System.out.println("Current year : "  + (2011 + noOfYears));		
		for(int i = 0; i < noOfCustomers; i++) {
			System.out.printf("%-6s", (i + 1) + ") ");
			System.out.println("CN : " + rank[i] + "    Purchase : " + purchase[index(rank[i])]);
		}
	}

}
