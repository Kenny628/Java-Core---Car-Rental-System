package assignment;

public class Car {
	private String carName;
	public String getCName()
	{
		return carName;
	}
	
	private String carType;
	public String getCType()
	{
		return carType;
	}
	
	private String plateNum;
	public String getPNum()
	{
		return plateNum;
	}
	
	private double rentalPrice;
	public double getRPrice()
	{
		return rentalPrice;
	}
    
	public Car(String carname, String carType, String plateNum, double rentalPrice)
	{
		this.carName = carname;
		this.carType = carType;
		this.plateNum= plateNum;
		this.rentalPrice = rentalPrice;
	}
}

