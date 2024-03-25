package assignment;

import java.util.ArrayList;

public class Client extends User{
		
		public Client(ArrayList<Rental> rental) 
		{
			super(rental);
		}

		public void createARental(Rental r)
		{
			rentalList.add(r);
		}
		    
		public Rental[] getRentalList()
		{
		        final int maximum=rentalList.size();
		        int []array= new int[maximum];
		        Rental [] list = new Rental[maximum];
		        list = rentalList.toArray(list);
		        int count=0;
		        for(int i=0;i<maximum;i++) {
		            if(list[i].getUserName().equals(getName()))
		            { 
		                array[count]=i;
		                ++count;
		                }
		            }
		        Rental[] newRentalList = new Rental[count];
		        for(int i=0;i<count;i++)
		        {
		            newRentalList[i]=list[array[i]];
		        }
		        return newRentalList;
		 }
		
		public boolean checkDuplicateUserName(String username) {
		boolean temp=false;
		for(int x = 0 ; x < getNumberOfAccounts(); x++)
		{
			Account a = null;
			a = accountList.get(x);
			if(a.getUsername().equals(username))
				temp=true;
	
		}
		return temp;
}

}

