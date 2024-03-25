package assignment;

import java.util.ArrayList;

public abstract class User {
	protected ArrayList <Account> accountList;
	protected ArrayList <Rental> rentalList;
	private String Name;
	private String Password;
	
	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String Password) {
		this.Password = Password;
	}
	
	public User(ArrayList<Rental> rental) {
		rentalList=rental;
        accountList= new ArrayList<Account>();
    }
	
	public void createAccount(int userID, int age2, int phone, String username, String password, String gender, String email)
	{
		accountList.add(new Account(userID,age2,phone,username,password,gender,email));// best
	}

	public Account findAccount(String username,String password)
	{
	
		Account a = null;
		boolean found=false;
		for(int x = 0 ; x < getNumberOfAccounts(); x++)
		{
		  a = accountList.get(x);
		if(a.getPassword().equals(password) && a.getUsername().equals(username))
		{
		found = true;
		}
		}
		if(found)
			return a;
		else
			return null;
	}
	
	public int getNumberOfAccounts()
	{
		return accountList.size();
	}
	
	public int getNumberOfRental()
    {
        return rentalList.size();

    }
	
    public abstract Rental [] getRentalList();
   
}

