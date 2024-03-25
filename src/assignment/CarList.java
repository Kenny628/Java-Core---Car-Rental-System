package assignment;
import java.util.ArrayList;

public class CarList {
	private ArrayList <Car> carList;
    
	
	public CarList()
	{
		carList = new ArrayList <Car>();
		
	}
	public void add(Car car)
	{
		carList.add(car);
	}
	public int getNumOfCar()
	{
		return carList.size();
	}
	
	public Car [] getCarList()
	{
		int count = carList.size();
		Car [] List = new Car [count];
		List = carList.toArray(List);
		return List;
	}
	
	public void removeCar(int pos)
	{
		carList.remove(pos);
	}
}

