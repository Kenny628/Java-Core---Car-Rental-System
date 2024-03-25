package assignment;

public class Rental {
	private String username;
	private String carName;
	private String carType;
	private String plateNum;
	private double rentalPrice;
	private String startDate;
	private String endDate;
	private int rentalDuration;
	private double totalRental;
	private boolean expired;
	
	public String getUserName()
	{
		return username;
	}
	
	public boolean isExpired()
	{
	     return expired;
	}
	
	public String getCarName()
	{
		return carName;
	}
	
	public String getCType()
	{
		return carType;
	}
	
	public String getPNum()
	{
		return plateNum;
	}
	
	public double getRentalPrice()
	{
		return rentalPrice;
	}
	
	public String getStartDate() 
	{
		return startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	public int getRentalDuration() {
		return rentalDuration;
	}

	public double getTotalRental() {
		return totalRental;
	}
	public Rental(String name,String carname, String cartype , String Pnum, double price, String start, String end, int rentalduration, double total,boolean expired)
	{
		username = name;
		carName = carname;
		carType = cartype;
		plateNum = Pnum;
		rentalPrice = price;
		startDate = start;
		endDate = end;
		rentalDuration = rentalduration;
		totalRental = total;
		this.expired = expired;
	}
}

