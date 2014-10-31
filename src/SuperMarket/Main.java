package SuperMarket;
import java.io.*; 
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Main {
	private static int CN;
	private static int count;
	private static Scanner sc = new Scanner(System.in); 
	private static Operations o = new Operations();
	private static JFrame f = new JFrame("Manager's Access");
	private static JPasswordField p1 = new JPasswordField(9);
	private static JPasswordField p2 = new JPasswordField(9);
	private static JPasswordField p3 = new JPasswordField(9);
	private static JLabel l1 = new JLabel();	
	private static JLabel l2 = new JLabel();	
	private static JLabel l3 = new JLabel();	
	private static boolean a = false; 
	private static String password1, password2;	


	public static void main (String args[]) throws IOException {
		int choice;
		String resAdd, dl;
		long telNo = 0;
		int purchase;

		System.out.println("\nCUSTOMER PURCHASE SCHEME");
		do {			
			System.out.println("");
			System.out.println("1)  Add a Customer");
			System.out.println("2)  Update purchase");
			System.out.println("  Manager Access : ");	
			System.out.println("3)  Change password for manager's access");				
			System.out.println("4)  Delete a Customer");
			System.out.println("5)  View Prize List");
			System.out.println("6)  Reset all purchases to zero");
			System.out.println("7)  View Annual Sales Position");
			System.out.println("8)  Reset everything");
			System.out.println("9)  Print Customer details");		
			System.out.println("10) Print a customer's account");
			System.out.println("11) Increment years");
			System.out.println("12) Quit");		
			System.out.println("Enter Choice(1-12) : ");		
			choice = sc.nextInt();
			sc.nextLine();
			o.initialiseFiles();
			
			switch(choice) {
				case 1 : System.out.println("Enter residence address : ");
						 resAdd = sc.nextLine();
						 System.out.println("resAdd : " + resAdd);
						 try {						 
							 System.out.println("Enter telephone no.(only digits)");
							 telNo = sc.nextLong();
						 } catch(InputMismatchException e) {
							 System.out.println("Invalid telephone no.");
							 sc.nextLine();
							 break;
						 }
						 sc.nextLine();
						 System.out.println("telNo : " + telNo);
						 System.out.println("Enter driving license no. : ");
						 dl = sc.nextLine();
						 System.out.println("dl : " + dl);
						 CN = o.addCustomer(resAdd, telNo, dl);
						 System.out.println("Your unique 4-digit Customer Number(CN) : " + CN);
						 break;
						 
				case 2 : if(!inputCN()) break;		
						 try {
							 System.out.println("Enter purchase to be added : ");
							 purchase = sc.nextInt();
							 sc.nextLine();
						 } catch(InputMismatchException e) {
							 System.out.println("Invalid purchase entered");
							 sc.nextLine();
							 break;
						 }
						 purchase = o.updatePurchase(CN, purchase);
						 System.out.println("Customer Number(CN) " + CN + 
								 " is updated with current purchase : " + purchase);						 
						 break;
						 
				case 3 : changePassword();		 
				 		 break;
				 		 
				case 4 : if(!isAccess()) break;
						 System.out.println("Customer deletion.");
				 		 if(!inputCN()) break;
				 		 o.deleteCustomer(CN);
				 		 break;
				 
				case 5 : if(!isAccess()) break;
						 o.printWinnerList();
				 		 break;
						
				case 6 : if(!isAccess()) break;
						 o.resetPurchase();		 
				 		 break;
							 
				case 7 : if(!isAccess()) break;
						 o.printYearlySales();		 
				 		 break;
	
				case 8 : if(!isAccess()) break;
				 		 o.resetEverything();
				 		 break;
				 
				case 9 : if(!isAccess()) break;
						 o.printCustomerDetails();
						 break;		 
						 
				case 10 :if(!isAccess()) break;
						 System.out.println("Customer Information Retrieval.");
						 if(!inputCN()) break;
						 o.printCustomer(CN);
						 break;		 
				
				case 11 :if(!isAccess()) break;
				 		 o.incrementNoOfYears();
						 break;		 
						 
				case 12 :break;		 
						
				default : System.out.println("Invalid choice entered");
			}
		}while(choice != 12);
	
		System.exit(0);
	}
	
	public static boolean inputCN() {
		try {
			System.out.println("Enter 4-digit CN(Customer Number) : ");
			CN = sc.nextInt();
			sc.nextLine();
			if(CN > 9999 || CN < 1000) {
				System.out.println("Incorrect CN(Customer Number) entered");				
				return false;
			}
			if(!o.existsCN(CN)) {
				System.out.println("Entered CN(Customer Number) is not registered yet");				
				return false;				
			}
			return true;
		} catch(InputMismatchException e) {
			System.out.println("Incorrect CN(Customer Number) entered");
			sc.nextLine();
			return false;
		}
	}
	
	public static boolean isAccess() {
		count = 0;
		f.getContentPane().setLayout(new FlowLayout());		
		f.getContentPane().add(l1);
		f.getContentPane().add(p1);
		f.setSize(375, 90);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		l1.setText("Enter password for manager's access");
		p1.setText("");
		a = false;
		p1.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(count != 0) return;
					count++;
					String password = new String(p1.getPassword());
					if(password.equals(o.getPassword())) {
						a = true;
						System.out.println("Correct Password Entered");
					} else {
						System.out.println("Incorrect Password Entered : Manager's access denied.");
						a = false;
					}
					f.remove(l1);						
					f.remove(p1);
					f.setVisible(false);
					System.out.println("Press an Enter to continue.");
				}
			}
		);
		sc.nextLine();
		if(count == 0) { //if not setVisible(false);
			f.remove(l1);
			f.remove(p1);
			f.setVisible(false);
		}	
		
		return a;
	}
	
	public static void changePassword() {
		count = 0;
		f.setTitle("Change Password for Manager's Access");
		f.getContentPane().setLayout(new FlowLayout());		
		f.getContentPane().add(l1);
		l1.setText("Enter Old Password");
		f.getContentPane().add(p1);
		f.getContentPane().add(l2);
		l2.setText("Enter New Password");
		f.getContentPane().add(p2);
		f.getContentPane().add(l3);
		l3.setText("Re-enter New Password");
		p1.setText("");	
		p2.setText("");	
		p3.setText("");
		f.getContentPane().add(p3);
		f.setVisible(true);
		f.setSize(400, 125);
		f.setLocationRelativeTo(null);
		p3.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(count != 0) return;
						count++;
						String password = new String(p1.getPassword());
						if(!(password.equals(o.getPassword()))) {
							System.out.println("Incorrect Password Entered : Manager's access denied");
							System.out.println("Press an Enter to continue.");
							f.remove(l1);
							f.remove(p1);
							f.remove(l2);
							f.remove(p2);
							f.remove(l3);
							f.remove(p3);
							f.setVisible(false);
							return;
						}
						password1 = new String(p2.getPassword());
						password2 = new String(p3.getPassword());
						if(password1.equals(password2)) {
							o.setPassword(password1);
							System.out.println("Password changed successfully");
						} else {
							System.out.println("Password re-entered doesnot match");
							System.out.println("Password not changed");							
						}
						f.remove(l1);
						f.remove(p1);
						f.remove(l2);
						f.remove(p2);
						f.remove(l3);
						f.remove(p3);
						f.setVisible(false);
						System.out.println("Press an Enter to continue.");
					}
				}
		);
		
		sc.nextLine();
		if(count == 0) { //if not setVisible(false);
			f.remove(l1);
			f.remove(p1);
			f.remove(l2);
			f.remove(p2);
			f.remove(l3);
			f.remove(p3);
			f.setVisible(false);
		}	
	}
	
}	
