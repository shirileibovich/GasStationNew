



public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GasStation thegas = new GasStation();
		Thread GasThread = new Thread(thegas);
		
		
		Car[] allCars = new Car[5];
		
		Car c1 = new Car(001, true, true, thegas, 47);
		Car c2 = new Car(002, false, true, thegas, 50);
		Car c3 = new Car(003, true, false, thegas, 38);
		Car c4 = new Car(004, true, true, thegas, 16);
		Car c5 = new Car(005, true, true, thegas, 20);
		
		allCars[0] =c1;
		allCars[1] =c2;
		allCars[2] =c3;
		allCars[3] =c4;

		allCars[4] =c5;

		
		FuelPump fp1 = new FuelPump(thegas, 001);
		thegas.addFulePumpToQue(fp1);
		GasThread.start();
		
		
		for (int i=0 ; i < allCars.length ; i++){
			System.out.println(i +1 + " car start");
			allCars[i].start();
			}
		
		
 	}
	

}
