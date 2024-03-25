package assignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CarRentalApp {

    public static void main(String[] args) {
        ArrayList<Rental>rentalList =new ArrayList<>();
        Client user = new Client(rentalList);
        Admin admin=new Admin(rentalList);
        CarList carList=new CarList();
        Scanner scan = new Scanner(System.in);
        File carFile = new File("CarList.txt");
        File adminFile = new File("Admin.txt");
        File userFile = new File("User.txt");
        File rentalFile = new File("RentalList.txt");

        try
        {
            if(!carFile.exists())
            {
                carFile.createNewFile();
            }
            if(!adminFile.exists())
            {
                adminFile.createNewFile();
            }
            if(!userFile.exists())
            {
                userFile.createNewFile();
            }
            if(!rentalFile.exists())
            {
                rentalFile.createNewFile();
            }
        }catch (IOException e)
        {
            System.out.println("Sorry, we cannot creat a new file for you!");
        }
        boolean canRegister=false;
        boolean canLogin=false;

        try {
            importCar(carList,carFile);
            importUsers(user, userFile);
            importUsers(admin, adminFile);
            importRental(user, rentalFile, carList,admin);
            exportCarList(carFile,carList);

        }
        catch(FileNotFoundException e) {
            System.out.println("File not found ");
            System.out.println("Sorry there was some problem");
        }
        catch(ParseException pe)
        {
            System.out.println("There was an error, please try again ! ");
        }

        int temp = 0;
        int choice=0;
        System.out.println("            ----------------          ");
        System.out.println("           /                \\         ");
        System.out.println("          /                  \\         ");
        System.out.println("     -------------------------------          ");
        System.out.println("    C|                             |          ");
        System.out.println("    C|     ____          ____      |         ");
        System.out.println("     |    /     \\       /     \\    |");
        System.out.println("     |---|   +   |-----|   +   |---|    ");
        System.out.println("          \\_____/       \\_____/      ");
        System.out.println("WELCOME TO CAR MANAGEMENT SYSTEM! :D");

        do
        {
            boolean pass = false;
            do
            {
                try
                {
                    System.out.println("If you are user: ");
                    System.out.println("1.Register");
                    System.out.println("2.Login");
                    System.out.println();
                    System.out.println("If you are admin: ");
                    System.out.println("3.Login");
                    System.out.println("4.Exit");
                    temp=scan.nextInt();
                    pass = true;
                }
                catch(Exception e)
                {
                    System.out.println("It must be an integer !");
                    scan.nextLine();
                }
            }while(!pass);

            switch(temp){
                case 1:
                    canRegister=registration(user,userFile);
                    break;
                case 2:
                    canLogin=login(user);
                    choice=temp;
                    break;
                case 3:
                    canLogin=login(admin);
                    choice=temp;
                    break;
                case 4:
                    System.out.println("Thanks for using our system !");
                    System.exit(0);
                default:
                    System.out.println("You are not choosing 1,2,3,4,5 or error input !");
                    break;
            }
        }while(canRegister==false&&canLogin==false);
        int adminChoice=0;
        int userChoice=0;
        boolean pass = false;
        if(choice==3){
            do {
                do
                {
                	adminChoice = 0;
                    try
                    {
                        System.out.println("What would you like to do ?");
                        System.out.println("1.Add car");
                        System.out.println("2.View all booking history");
                        System.out.println("3.Delete a car");
                        System.out.println("4.Exit");
                        adminChoice=scan.nextInt();
                        pass = true;
                    }catch(Exception e)
                    {
                        System.out.println("It must be an integer !");
                        scan.nextLine();
                    }
                }while(!pass);
                switch(adminChoice)
                {
                    case 1:
                        addCar(carList, admin);
                        exportCarList(carFile,carList);
                        break;
                    case 2:
                        showBookingHistory(admin);
                        break;
                    case 3:
                        deleteCar(carList, admin);
                        exportCarList(carFile,carList);
                        break;
                    case 4:
                        System.out.println("Thanks for using our system !");
                        System.exit(0);
                    default:
                        System.out.println("You are not choosing 1,2,3,4 or error input !");
                        break;
                }
            }while(adminChoice!=4);
        }
        else{
            do{
                do
                {
                	userChoice = 0;
                    try
                    {
                        System.out.println("Dear, what would you like to do ?");
                        System.out.println("1.Search car");
                        System.out.println("2.Display car list");
                        System.out.println("3.Book car");
                        System.out.println("4.View my booking history");
                        System.out.println("5.Exit");
                        userChoice=scan.nextInt();
                        pass = true;
                    }
                    catch (Exception e)
                    {
                        System.out.println("Integer allowed only !");
                        scan.nextLine();
                    }
                }while(!pass);
                switch(userChoice)
                {
                    case 1:
                        System.out.println("");
                        searchCar(carList);
                        break;
                    case 2:
                        displayCarList(carList);
                        break;
                    case 3:
                        try
                        {
                            bookCar(carList,user, rentalFile, carFile, admin);
                        }
                        catch (ParseException pe)
                        {
                            System.out.println("Sorry, that was an error :D");
                        }
                        break;
                    case 4:
                        showBookingHistory(user);
                        break;
                    case 5:
                        System.out.println("Thanks for using our system !");
                        System.exit(0);
                    default:
                        System.out.println("You are not choosing 1,2,3,4,5");
                        break;
                }
            }while(userChoice!=5);
        }
        //close resource
        scan.close();
    }

    public static void importRental(Client client,File file, CarList carList, Admin admin)throws FileNotFoundException, ParseException
    {
        Scanner scan = new Scanner(file);
        while (scan.hasNext()) {
            String userName = scan.next();
            String carName = scan.next();
            String bookedCarType = scan.next();
            String plateNumber = scan.next();
            String a = scan.next();
            double price = Double.parseDouble(a);
            String startDate = scan.next();
            String endDate = scan.next();
            String b = scan.next();
            int rentalDuration = Integer.parseInt(b);
            String c = scan.next();
            double total = Double.parseDouble(c);
            String d = scan.next();
            boolean expired= Boolean.parseBoolean(d);
            Date date = new Date();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date userEndDate = null;
            userEndDate = formatter.parse(endDate);
            if (expired==false && date.after(userEndDate)) {
                carList.add(new Car(carName, bookedCarType, plateNumber, price));
                expired=true;
            }
                Rental rental = new Rental(userName, carName, bookedCarType, plateNumber, price, startDate, endDate, rentalDuration, total, expired);
                client.createARental(rental);


        }
        outputToRental(client, admin, file);
    }

    public static void importUsers(User user,File file)throws FileNotFoundException
    {
        Scanner scan = new Scanner(file);
        while(scan.hasNext()){
            String a = scan.next();
            int ID = Integer.parseInt(a);
            String b = scan.next();
            int age = Integer.parseInt(b);
            String c = scan.next();
            int phone = Integer.parseInt(c);
            String  username = scan.next();
            String password = scan.next();
            String gender = scan.next();
            String email = scan.next();
            user.createAccount(ID, age , phone , username,password,gender,email);
        }
    }

    public static boolean registration(Client client,File file)
    {

        Scanner input = new Scanner(System.in);
        String lower, gender;
        boolean pass = true;
        int ID,age = 0,phone = 0;
        String username , password , email;
        int count = 0;
        ID = client.getNumberOfAccounts()+1;
        boolean space = true;
        System.out.print("Enter your desired username : ");
        username = input.nextLine();
        username = username.replaceAll(" ","");
        System.out.print("Enter your password : ");
        password = input.next();
        do
        {
            try
            {
                System.out.print("Enter you age : ");
                age = input.nextInt();
                pass = false;
            }
            catch(InputMismatchException e)
            {
                System.out.println("It must be an integer !");
                input.nextLine();
            }
        }while(pass);
        do
        {
            try
            {
                System.out.print("Enter your phone number : ");
                phone = input.nextInt();
                pass = true;
            }
            catch (InputMismatchException ex)
            {
                System.out.println("It must be an integer !");
                input.nextLine();
            }
        }while(!pass);
        do{
            System.out.print("Enter your gender(M OR F) : ");
            lower = input.next();
            gender = lower.toUpperCase();
            if(!gender.equals("M") && !gender.equals("F")){
                System.out.println("Gender can only be Male(M) or Female(F) ");
            }
        }while(!gender.equals("M") && !gender.equals("F"));
        do{
            System.out.print("Enter your personal email : ");
            email = input.next();
        if(!email.contains("@hotmail.com")&&!email.contains("@gmail.com")&&!email.contains("@outlook.com") && !email.contains("@yahoo.com"))
        {
            System.out.println("Please enter valid email address!");
        }
        }while(!email.contains("@hotmail.com")&&!email.contains("@gmail.com")&&!email.contains("@outlook.com")&& !email.contains("@yahoo.com"));

        if(client.checkDuplicateUserName(username)==true){
            System.out.println("Username existed, please register again!");
            return false;
        }
        else{
            System.out.println("Successfully registered, signing in...");
            client.createAccount(ID, age , phone ,username,password,gender,email);
            client.setName(username);
            client.setPassword(password);
            try{

                FileWriter fileWritter = new FileWriter(file,true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                bufferWritter.write("\n");
                bufferWritter.write(ID+" "+age+" "+phone+" "+username+" "+password+" "+gender+" "+email);
                bufferWritter.close();
            }catch(IOException e){
                System.out.println("Error happened when writing to the txt file!");}
            return true;
        }
    }

    public static boolean login(User user)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter username: ");
        String name=scan.next();
        System.out.println("Enter password: ");
        String password=scan.next();
        Account a = user.findAccount(name,password);
        if(a != null){
            user.setName(name);
            user.setPassword(password);
            System.out.println("Signing in......");
            return true;
        }
        else{
            System.out.println("Username or password incorrect, please login again!");
            return false;
        }
    }

    public static void importCar(CarList carList,File file) throws FileNotFoundException
    {
        Scanner scan = new Scanner(file);

        while(scan.hasNext())
        {
            String carid = scan.next();
            String carType = scan.next();
            String plateNum = scan.next();
            String rentalPrice = scan.next();
            double rP = Double.parseDouble(rentalPrice);
            carList.add(new Car (carid,carType,plateNum,rP));
        }
    }

    public static void exportCarList(File cFile , CarList carlist)
    {
        Car [] cList = carlist.getCarList();
        try
        {
            FileWriter fileWritter = new FileWriter(cFile);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            for(int x = 0 ; x < cList.length; x++)
            {
                bufferWritter.write("\n");
                bufferWritter.write(cList[x].getCName() + " " + cList[x].getCType() + " " + cList[x].getPNum() + " " + cList[x].getRPrice());

            }
            bufferWritter.close();
        }catch(IOException e)
        {
            System.out.println("Error happened when writing to the txt file!");
        }
    }

    public static void bookCar(CarList carlist, Client client, File rFile, File cFile,Admin admin) throws ParseException
    {
        Scanner scan = new Scanner(System.in);
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date userEndDate, userStartDate;
        String Date = formatter.format(date); // system Date
        boolean startValid = false; // for the validation for start Date
        boolean endValid = false; // for the validation for end date
        String startDate, endDate, carName, carType, pNum;
        double rentalPrice , totalPrice;
        int rentDays;
        int Year = 0, Month  = 0, Day = 0, maxDay = 0;
        char choice;
        boolean found = false; // for the validation of plate number
        Car [] cList = carlist.getCarList(); // get car's list from class
        String ans;
        int pos = 0;
        do
        {
        	System.out.println("Have you view the car list? (Yes for Y || No for N)");
        	ans = scan.next();
        }while(!ans.equals("y") && !ans.equals("Y") && !ans.equals("N") && !ans.equals("n"));
        
        if(ans.equals("Y") || ans.equals("y"))
        {
        	do{
        		System.out.print("What is the car you want to book (use the plate Number): ");
        		String plateNum = scan.next();
        		plateNum = plateNum.toUpperCase();

        		for(int x = 0 ; x < carlist.getNumOfCar() ; x++)
        		{
        		if(cList[x].getPNum().equals(plateNum))
        		{
        		found = true;
        		pos = x;
        		}
        		}
        		if(!found)
        		System.out.println("No car with this plate number found! Please key in valid plate number!");
        		}while(!found);


        if(found)
        {
            carName = cList[pos].getCName();
            carType = cList[pos].getCType();
            pNum = cList[pos].getPNum();
            rentalPrice = cList[pos].getRPrice();
            System.out.println("Car Found !");
            System.out.println("When had you like to start to rent the car?");
            do
            {
                try
                {
                    System.out.print("Enter the year: ");
                    Year = scan.nextInt();
                    do
                    {
                        System.out.print("Enter the month: ");
                        Month = scan.nextInt();
                        if(Month > 12)
                        {
                            System.out.println("A year only have 12 months.");
                            System.out.println("Please type again! ");
                        }
                    }while(Month > 12);
                    if(Month == 1 || Month == 3 || Month == 5 || Month == 7 ||Month == 8 || Month == 10 || Month == 12)
                    {
                        maxDay = 31;
                    }
                    else if(Month == 4 || Month == 6 || Month == 9 || Month == 11)
                    {
                        maxDay = 30;
                    }
                    else if(Month == 2)
                    {
                        maxDay = 29;
                    }
                    System.out.print("Enter the day: ");
                    Day = scan.nextInt();
                }
                catch(InputMismatchException e)
                {
                    System.out.println("Must be integer since it is a date!");
                    scan.nextLine();
                }
                startDate = Year + "-" + Month + "-" + Day;
                userStartDate = formatter.parse(startDate);
                if (Day > maxDay)
                {
                    System.out.println("Month " + Month + " only have " + maxDay + " days");
                }
                else if(userStartDate.after(date))
                {
                    startValid = true;
                }
                else if (userStartDate.before(date))
                {
                    System.out.println("Must be later than current date : " + Date);
                    System.out.println("Please type again!");
                }
            }while(!startValid || Day > maxDay || Month > 12);

            System.out.println("When would you like to end the rental?");
            do
            {
                try
                {
                    System.out.print("Enter the year: ");
                    Year = scan.nextInt();
                    do
                    {
                        System.out.print("Enter the month: ");
                        Month = scan.nextInt();
                        if(Month > 12)
                        {
                            System.out.println("A year only have 12 months.");
                            System.out.println("Please type again! ");
                        }
                    }while(Month > 12);
                    if(Month == 1 || Month == 3 || Month == 5 || Month == 7 ||Month == 8 || Month == 10 || Month == 12)
                    {
                        maxDay = 31;
                    }
                    else if(Month == 4 || Month == 6 || Month == 9 || Month == 11)
                    {
                        maxDay = 30;
                    }
                    else if(Month == 2)
                    {
                        maxDay = 29;
                    }
                    do
                    {
                        System.out.print("Enter the day: ");
                        Day = scan.nextInt();
                        if(Day > maxDay)
                        {
                            System.out.println("Month " + Month + " only have " + maxDay + " days");
                        }
                    }while(Day > maxDay);
                }
                catch(InputMismatchException e)
                {
                    System.out.println("Must be integer since it is a date!");
                    scan.nextLine();
                }
                endDate = Year + "-" + Month + "-" + Day;
                userEndDate = formatter.parse(endDate);
                if(userEndDate.after(userStartDate))
                {
                    endValid = true;
                }
                else
                {
                    System.out.println("Must be later than the start of your rental date : " + startDate);
                    System.out.println("Please type again!");
                }
            }while(!endValid);

            long timeInBetween = userEndDate.getTime() - userStartDate.getTime();
            int daysInBetween = (int) (timeInBetween / (1000 * 60 * 60 * 24));
            rentDays = daysInBetween + 1;
            totalPrice = rentalPrice * (double)rentDays;
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Car Name     Car Type     Plate Number     Rental Price(day)     StartDate        EndDate        Duration(Days)     total");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-12s %-12s %-16s %.2f \t\t %-10s \t  %-20s %-12s %.2f", carName,carType,pNum,rentalPrice,startDate,endDate, rentDays,totalPrice);
            System.out.println();
            System.out.println();
            do
            {
                System.out.println("Do you want to proceed and make your payment? (Y for yes and N for no) ");
                choice = scan.next().charAt(0);
                if(choice == 'y' || choice == 'Y')
                {
                    System.out.println("Please type in your password: ");
                    String password = scan.next();
                    if(client.getPassword().equals(password))
                    {
                        System.out.println("Please type in the password again: ");
                        String password2 = scan.next();
                        if(client.getPassword().equals(password2))
                        {
                            System.out.println("Congrats, you have successfully rent the car!");
                            Rental rental = new Rental(client.getName(),carName, carType, pNum, rentalPrice, startDate, endDate , rentDays, totalPrice,false);
                            client.createARental(rental);
                            carlist.removeCar(pos);
                            exportCarList(cFile,carlist);
                        }
                        else
                        {
                            System.out.println("You have entered the wrong password!");
                        }
                    }
                    else
                    {
                        System.out.println("You have entered the wrong password!");
                    }
                }

                else if(choice == 'N' || choice == 'n')
                {
                    System.out.println("You chose not to rent the car, Thank you! ");
                }
            }while(choice != 'Y' && choice != 'y' && choice != 'N' && choice != 'n');
        }
        else
        {
            System.out.println("Entered wrong plate number. Please try again!");
        }
        outputToRental(client,admin,rFile);
        }
        else if(ans.equals("N") || ans.equals("n"))
        {
        	System.out.println("Please view car list before booking a car, Thank you !");
        }
    }
    
    public static void outputToRental(User user,Admin admin,File rentalFile)
    {
        Rental [] rental = admin.getRentalList() ;
        try
        {
            FileWriter fileWritter = new FileWriter(rentalFile);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            for(int i=0;i<rental.length;i++) {
                bufferWritter.write("\n");
                bufferWritter.write(rental[i].getUserName()+ " " + rental[i].getCarName()+ " " + rental[i].getCType()+ " " + rental[i].getPNum() + " " + rental[i].getRentalPrice() + " " + rental[i].getStartDate() + " " + rental[i].getEndDate()+ " " + rental[i].getRentalDuration() + " " + rental[i].getTotalRental()+ " " + rental[i].isExpired());

            }
            bufferWritter.close();
        }catch(IOException e)
        {
            System.out.println("Error happened when writing to the txt file!");
        }
    }

    public static void searchCar(CarList car)
    {
        Scanner scan = new Scanner(System.in);
        String choice = "";
        Car[] carlist=car.getCarList();
        String price = "price";
        String brand = "brand";
        boolean found = false;
        boolean pass = true;

        do{

            System.out.println("Do you want to search the car by its brand or price? ");
            choice = scan.next();
            choice = choice.toLowerCase();
            if (price.equals(choice) == false&& brand.equals(choice) == false)
                System.out.println("Only price or type is allowed");
        }while(price.equals(choice) == false&& brand.equals(choice) == false);


        if (brand.equals(choice))
        {
        	String c1;
        	do{
        	System.out.println("What type of car are you finding? ");
        	c1 = scan.next();
        	c1 = c1.toUpperCase();

        	System.out.println("Car Name       Car Brand            Plate Number        Rental Price ");
        	System.out.println("----------------------------------------------------------------------");
        	for (int i=0;i < car.getNumOfCar();i++)
        	{
        	if (c1.equals(carlist[i].getCType()))
        	{
        	System.out.printf("%-14s %-20s %-15s \t%.2f",carlist[i].getCName(), carlist[i].getCType(), carlist[i].getPNum(),carlist[i].getRPrice());
        	found = true;
        	System.out.println();
        	}
        	}
        	if (!found)
        	System.out.println("No matching car found! please search again");
        	}while(!found);
        }
        else if (price.equals(choice))
        {
            double c2 = 0.0;
            do{
                try{
                    System.out.println("What price of car are you finding? ");
                    c2 = scan.nextDouble();
                    pass = false;

                }catch(Exception e){
                    System.out.println("Only numbers are allowed");
                    scan.nextLine();
                }
            }while(pass);
            System.out.println("Car Name       Car Brand            Plate Number        Rental Price ");
            System.out.println("----------------------------------------------------------------------");
            for (int i=0;i < car.getNumOfCar();i++)
            {
                if (carlist[i].getRPrice() <= c2 )
                {
                    System.out.printf("%-14s %-20s %-15s \t%.2f",carlist[i].getCName(), carlist[i].getCType(), carlist[i].getPNum(),carlist[i].getRPrice());
                    found = true;
                    System.out.println();
                }
            }
            if (!found)
                System.out.println("No matching car price found! please search again");

        }
    }

    public static void displayCarList(CarList carList)
    {
        Car[] list = carList.getCarList();

        System.out.println("Car Name       Car Brand            Plate Number        Rental Price      ");
        System.out.println("----------------------------------------------------------------------");
        for(int x = 0 ; x < carList.getNumOfCar(); x ++)
        {
            System.out.printf("%-14s %-20s %-15s \t%.2f",list[x].getCName(), list[x].getCType(), list[x].getPNum(), list[x].getRPrice());
            System.out.println();
        }
    }

    public static void showBookingHistory(User user)
    {
        int count=0;
        Rental[] newRentalList=user.getRentalList();
        System.out.println("Username        Car Name        Rental Price      Car Brand           Plate Number      Renting Start Date           Renting End Date            Total Rental");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for(int i=0;i<newRentalList.length;i++) {
            System.out.printf("%-15s %-15s %.2f \t\t  %-19s %-17s %-28s %-20s \t %.2f %n",newRentalList[i].getUserName(),newRentalList[i].getCarName(),newRentalList[i].getRentalPrice(),newRentalList[i].getCType(),newRentalList[i].getPNum(),newRentalList[i].getStartDate(),newRentalList[i].getEndDate(),newRentalList[i].getTotalRental());
            ++count;
        }
        System.out.println();
        if(count==0)
            System.out.println("No booking history found!");
    }

    public static void addCar(CarList carlist, Admin admin)
    {
        Scanner scan = new Scanner(System.in);
        Car [] cList = carlist.getCarList();
        String carName, carType, plateNum;
        double price = 0.0;
        boolean duplicate = false;
        boolean check = false;
        int  check1, check2 ;
        boolean pass = false;
        System.out.println("You are able to add a car now !");
        System.out.println("What is the car name?");
        carName = scan.next();
        carName = carName.toUpperCase();
        System.out.println("What is the type of the car? ");
        carType = scan.next();
        carType= carType.toUpperCase();
        do {
            try {
                System.out.println("What is the rental price for the car? ");
                price = scan.nextDouble();
                pass=true;
            } catch (InputMismatchException e) {
                System.out.println("You must enter number only!");
                scan.nextLine();
            }
        }while(!pass);
        do
        {
            int count = 0;
            check1 = 0;
            check2 = 0;
            duplicate = false;
            System.out.println("What is the plate Number of the car?");
            System.out.println("Note: First three must be alphabetic and with total of 7 character. EX: BBB0000");
            plateNum = scan.next();
            plateNum = plateNum.toUpperCase();
            for(int y = 0 ; y < carlist.getNumOfCar(); y++)
            {
                if(cList[y].getPNum().equals(plateNum))
                {
                    duplicate = true;
                }
            }
            if(duplicate)
            {
                System.out.println("Duplicate number are not allowed");
            }
            else
                for(int x =0 ; x < plateNum.length();x++)
                {
                    count++;

                }
            if(count > 7 || count < 7)
            {
                System.out.println("Only 7 characters are allowed !");
            }
            else
            {
                String firstThree = plateNum.substring(0,3);
                String lastFour = plateNum.substring(3, 7);
                check = true;
                for(char c : firstThree.toCharArray())
                {
                    if(Character.isLetter(c))
                    {
                        check1++;
                    }
                }
                for(char ch : lastFour.toCharArray())
                {
                    if(Character.isDigit(ch))
                    {
                        check2++;
                    }
                }
            }
        }while(!check || check1 != 3 || check2 != 4);

        Car addedCar = new Car(carName, carType, plateNum, price);
        admin.addACar(addedCar, carlist);
        Car [] newcar = carlist.getCarList();
        System.out.println("Car Name       Car Brand            Plate Number        Rental Price      ");
        System.out.println("----------------------------------------------------------------------");
        for(int x = 0 ; x < carlist.getNumOfCar(); x++)
        {
            System.out.printf("%-14s %-20s %-15s \t%.2f", newcar[x].getCName(), newcar[x].getCType(), newcar[x].getPNum(), newcar[x].getRPrice());
            System.out.println("");
        }
        System.out.println("");
        System.out.println("Car has added successfully!");
        System.out.println("");
    }
    public static void deleteCar(CarList carlist, Admin admin)
    {
        Scanner scan = new Scanner(System.in);
        Car [] cList = carlist.getCarList();
        System.out.println("Car Name       Car Brand            Plate Number        Rental Price      ");
        System.out.println("----------------------------------------------------------------------");
        for(int x = 0 ; x < carlist.getNumOfCar(); x++)
        {
            System.out.printf("%-14s %-20s %-15s \t%.2f", cList[x].getCName(), cList[x].getCType(), cList[x].getPNum(), cList[x].getRPrice());
            System.out.println("");
        }
        String carNum;
        boolean found = false;
        System.out.println("You are able to delete a car now !");
        System.out.println("What is the plate number of the car?");
        carNum = scan.next();
        carNum = carNum.toUpperCase();
        for (int i = 0;i < cList.length;i++)
        {
            if (carNum.equals(cList[i].getPNum()))
            {

                admin.deleteACar(i, carlist);
                found = true;
                System.out.println("Car deleted");
            }
        }
        if (!found)
            System.out.println("No matching car found! please try again");
    }
}
