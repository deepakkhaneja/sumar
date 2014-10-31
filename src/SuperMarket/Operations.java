package SuperMarket;

import java.io.*;

class Operations {
	private SalesManager salesManager;
	private FileInputStream fisManager;
	private FileOutputStream fosManager;
	private ObjectInputStream oisManager;
	private ObjectOutputStream oosManager;
	
	public void initialiseFiles() {
		try{
			File f = new File("SalesManager");
			if(!f.exists()) {
				if(!f.createNewFile()) System.out.println("Operations.initialiseFiles() : File " +
						"\"SalesManager\" not created");
				else {
					salesManager = new SalesManager();
					writeSalesManager();
				}
			}
		} catch (Exception e) {
			System.out.println("Operations.initialiseFiles() : " + e);
		}
	}
	
	private void readSalesManager() {
		try {
			fisManager = new FileInputStream("SalesManager");
			oisManager = new ObjectInputStream(fisManager);
			salesManager = (SalesManager)oisManager.readObject();
			oisManager.close();

		} catch (Exception e) {
			System.out.println("Operations.readSalesManager() deserialization :" + e);
		}	
	}
	
	private void writeSalesManager() {
		try {
			fosManager = new FileOutputStream("SalesManager");
			oosManager = new ObjectOutputStream(fosManager);
			oosManager.writeObject(salesManager);
			oosManager.close();
			
		} catch (Exception e) {
			System.out.println("Operations.writeSalesManager() serialization :" + e);
		}	

	}
	
	public int addCustomer(String resAdd, long telNo, String dl) {
		
		readSalesManager();
		int CN = salesManager.addCustomer(resAdd, telNo, dl);
		writeSalesManager();
		return CN;		
	}
	
	public boolean existsCN(int CN) {
		readSalesManager();
		return salesManager.existsCN(CN);
	}
	
	public int updatePurchase(int CN, int purchaseAdded) {
		readSalesManager();
		int finalPurchase = salesManager.updatePurchase(CN, purchaseAdded);
		writeSalesManager();
		return finalPurchase; 
	}
	
	public void deleteCustomer(int CN) {
		readSalesManager();
		salesManager.deleteCustomer(CN);
		writeSalesManager();		
	}
	
	//reset all customers' purchase values to 0
	public void resetPurchase() {
		readSalesManager();
		salesManager.resetPurchase();
		writeSalesManager();
	}
	
	public void resetEverything() {
		salesManager = new SalesManager();
		writeSalesManager();
		System.out.println("Everything reset");
	}
	
	public void printCustomerDetails() {
		readSalesManager();
		salesManager.printCustomerDetails();
	}
	
	public void printCustomer(int CN) {
		readSalesManager();
		salesManager.printCustomer(CN, 1);		
	}

	public void printWinnerList() {
		readSalesManager();
		salesManager.printWinnerList();
	}
	
	public String getPassword() {
		readSalesManager();
		return salesManager.getPassword();
	}
	
	public void setPassword(String password) {
		readSalesManager();
		salesManager.setPassword(password);
		writeSalesManager();
	}

	public void printYearlySales() {
		readSalesManager();
		salesManager.printYearlySales();
	}
	
	public void incrementNoOfYears() {
		readSalesManager();
		salesManager.incrementNoOfYears();
		writeSalesManager();
	}
	
}
