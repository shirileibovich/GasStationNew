import java.util.Scanner;


public class ConsoleUI {
	GasStation gs;
	
	public ConsoleUI(GasStation gs){
		this.gs=gs;
	}
	Scanner s = new Scanner(System.in);
	 public void startCUI(){
		 int selection;
		 boolean run=true;
		 while(run){ 
			 System.out.println("Welcome to the gas station! /n please choose what to do:");
			 System.out.println("1.Add new car.");
			 System.out.println("2.Refill fuel to the main fuel pump.");
			 System.out.println("3.Show statistics.");
			 System.out.println("4.Call it a day.");
			 System.out.println("5.Exit");
			 
			 selection=s.nextInt();
			 switch(selection){
			 case 1:
				 addNewCar();
			 break;
			 case 2:
				 refillFuel();
				 break;
			 case 3:
				 showStat();
				 break;
			 case 4:
				 close();
				 break;
			 case 5:
				 System.out.println("Goodbye!");
				 run=false;
			 }
			 s.nextLine();
		 }
	 }
	private void addNewCar() {
		long numOfliters=0;
		boolean wantsClean=false,wantsFuel=false;
		System.out.println("What is the id?");
		long id=s.nextLong();
		System.out.println("Do you want fuel? \nplease enter yes or no");
		String f=s.nextLine();
		if(f.contains("y")){
			wantsFuel=true;
			System.out.println("how many liters?");
			numOfliters=s.nextLong();
		}
		s.next();
		System.out.println("Do you want cleaning?");
		String c=s.nextLine();
		if(f.contains("y")){
			wantsClean=true;
		}
		Car nCar=new Car(id,wantsClean,wantsFuel,gs,numOfliters);
		nCar.start();
		System.out.println("Congrats! new car id="+id+" was added!");
	}
	private void refillFuel() {
		gs.refillMainFuelPool();
	}
	private void showStat() {
		System.out.println("the profits from the fuel:");
		gs.getFuelingProfits();
		System.out.println("the profits from the cleaning:");
		gs.getCleaningProfits();
		System.out.println("The number of cars who fueled: ");
		gs.getFuelCounter();
		System.out.println("The number of cars who cleaned: ");
		gs.getCleanCounter();
		
		// TODO Auto-generated method stub
		
	}
	private void close() {
		gs.closeGasStaition();
		showStat();
		
	}
	 
}
