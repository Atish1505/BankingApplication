package com.main;

import java.util.Scanner;

import com.dao.AccountHolderDao;
import com.model.AccountHolder;

public class App 
{
    public static void main( String[] args )
    {
    	
    	//Passing a values statically.
    	/*AccountHolder ah=new AccountHolder();
    	ah.setUacname("Avinash Shelke");
    	ah.setUactype("Current");
    	ah.setUacbalance(40000);
    	String a[]=new AccountHolderDao().createAccount(ah);
    	System.out.println("User Account No :"+a[0]);
    	System.out.println("User Pin No : "+a[1]);*/
    	
    	
    	//passing a values to checklogin method
    	//System.out.println(new AccountHolderDao().checkLogin(100002, "8800"));
    	
    	
    	//passing a values to checkBalance method
    	//System.out.println(new AccountHolderDao().checkBalance(100002, "8800"));
    	
    	//Passing a values to WithdrawalBalance Method
    	//System.out.println(new AccountHolderDao().withdrawlBalance(100002, "8800", 30000));
    	
    	//Passing a values to DepositBalance Method
    	//System.out.println(new AccountHolderDao().depositeBalance(100002, "8800", 40000));
    	
    	//Passing a values to AccountDetails Method
    	/*AccountHolder a=new AccountHolderDao().accountDetails(100002, "8800");
    	System.out.println(a);*/
    	
    	int ch = 0;
		do
		{
		System.out.println("---------------NEW BANK--------------");
		System.out.println("1. Create Account");
		System.out.println("2. Existing Account");
		System.out.println("3. Exit");
		System.out.println("-------------------------------------");
		System.out.print("Enter Your Choice:");
		ch = new Scanner(System.in).nextInt();
		switch (ch) {
		case 1:
		{
			AccountHolder u = new AccountHolder();
			System.out.print("Enter Your Name:");
			u.setUacname(new Scanner(System.in).nextLine());
			System.out.print("Enter A/c Type:");
			u.setUactype(new Scanner(System.in).nextLine());
			System.out.print("Enter Your Opening Balance:");
			u.setUacbalance(new Scanner(System.in).nextDouble());
			String s1[] = new AccountHolderDao().createAccount(u);
			if(s1!=null)
			{
				System.out.println("Your "+u.getUactype()+" account created with A/c No:"+s1[0]);
				System.out.println("Your A/c Pin:"+s1[1]);
			}
			break;
		}
		case 2:
		{
			System.out.print("Enter A/c No:");
			int acno = new Scanner(System.in).nextInt();
			System.out.print("Enter A/c Pin:");
			String pin = new Scanner(System.in).next();
			if(new AccountHolderDao().checkLogin(acno, pin))
			{
				int ch1 = 0;
				do
				{
					System.out.println("---------------NEW BANK--------------");
					System.out.println("1. Deposite Amount");
					System.out.println("2. Withdrawl Amount");
					System.out.println("3. Check Balance");
					System.out.println("4. Account Details");
					System.out.println("5. Change Pin");
					System.out.println("6. Close A/c");
					System.out.println("7. Exit");
					System.out.println("-------------------------------------");
					System.out.print("Enter Your Choice:");
					ch1 = new Scanner(System.in).nextInt();
					switch(ch1)
					{
					case 1:{
						System.out.print("Enter Amount:");
						double amount = new Scanner(System.in).nextDouble();
						double updateamount = new AccountHolderDao().depositBalance(acno, pin, amount);
						System.out.println(amount+" deposited successfully");
						System.out.println("Your updated Balance is:"+updateamount);
						break;
					}
					case 2:{
						System.out.print("Enter Amount:");
						double amount = new Scanner(System.in).nextDouble();
						if(amount>new AccountHolderDao().checkBalance(acno, pin))
						{
							System.out.println("Insufficient Balance!!!");
						}
						else
						{
						double updateamount = new AccountHolderDao().withdrawlBalance(acno, pin, amount);
						System.out.println(amount+" withdrawl successfully");
						System.out.println("Your updated Balance is:"+updateamount);
						}
						break;
					}
					case 3:{
						double amount = new AccountHolderDao().checkBalance(acno, pin);
						System.out.println("Your Updated balance is:"+amount);
						break;
					}
					case 4:{
						AccountHolder u = new AccountHolderDao().accountDetails(acno, pin);
						if(u!=null)
						{
							System.out.println("A/c No:"+u.getUacno());
							System.out.println("A/c Holder Name:"+u.getUacname());
							System.out.println("A/c Type:"+u.getUactype());
							System.out.println("A/c Balance:"+u.getUacbalance());
						}
						else
						{
							System.out.println("incorrent credential!!!");
						}
						break;
					}
					case 5:
					{
						AccountHolder u = new AccountHolder();
						System.out.print("Enter A/c No:");
						u.setUacno(new Scanner(System.in).nextInt());
						System.out.print("Enter A/c Name:");
						u.setUacname(new Scanner(System.in).nextLine());
						System.out.print("Enter A/c Type:");
						u.setUactype(new Scanner(System.in).nextLine());
						System.out.print("Enter New Pin No:");
						u.setUacpin(new Scanner(System.in).nextLine());
						if(new AccountHolderDao().updateAcPin(u))
							System.out.println("Pin changed successfully with :"+u.getUacpin());
						else
							System.out.println("incorret credential!!!");
						break;
					}
					case 6:
						AccountHolder u = new AccountHolderDao().CloseAccount(acno, pin);
						if(u!=null)
						{
							System.out.println("Account Closed Successfully..!");
						}
						else
						{
							System.out.println("incorrent credential!!!");
						}
						break;
					}
				}while(ch1<=4);
				
			}
			else
			{
				System.out.println("incorrect credential!!!");
			}
			break;
		}
			
		}
		}while(ch<=2);

    	
    	
    	
          
    }
}
