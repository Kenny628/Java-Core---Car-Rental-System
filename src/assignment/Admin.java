package assignment;

import java.util.ArrayList;

public class Admin extends User{
	
	public Admin(ArrayList<Rental> rental) 
	{
		super(rental);
	}
	
	public Rental[] getRentalList()
    {

        final int maximum= rentalList.size();
        Rental [] list = new Rental[maximum];
        list = rentalList.toArray(list);
        return list;
    }
	
	public void addACar(Car c, CarList cL)
	{
		cL.add(c);
	}
	
	public void deleteACar(int pos, CarList cL)
	{
		cL.removeCar(pos);
	}
}
