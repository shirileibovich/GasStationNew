import java.util.LinkedList;
import java.util.Queue;





public class GasStation implements Runnable{

	private boolean isOpen = true;
	private Queue<FuelPump> fpQue = new LinkedList<FuelPump>();;
	private Queue<Car> waitingCarsToFuel = new LinkedList<Car>();
	private Queue<Car> waitingCarsToClean = new LinkedList<Car>();
	
	public GasStation(){
		
	}
	
	
	
	public void addFulePumpToQue(FuelPump fp){//que for free pumps
		fpQue.add(fp);
		System.out.println("add fp");
	}
	
	
	public boolean canStartfuel() {

		return true;
	}

	

	public boolean whatToDoFirst() {

		return true;
	}

	public FuelPump getFreePump() {
		System.out.println("fp is out of que");
		return fpQue.poll();
	}
	
	
	

	public void goTocleaning() {
		// TODO Auto-generated method stub

	}

	public boolean hasFreePump() {
		// TODO Auto-generated method stub
		return false;
	}

	public void addWaitingCarToFuel(Car c) {
		
			System.out.println("no free pump add car to que " + c.getId());
			waitingCarsToFuel.add(c);
			
		}
		
	
	//public Car getCarToFule(Car c){
		
	//}
	
	public synchronized void notifyCar() {
		Car firstCar = waitingCarsToFuel.poll();
		if (firstCar != null) {
			System.out.println("there is free pump poll car out of que");
			//firstCar.notifyAll();
			this.carWantToFule(firstCar);
		
			}
		}
		
		

	
	
	
	public void carWantToFule(Car c){
		if (fpQue.size()>=1){
			try {
				System.out.println("go to pump to start fuel");
				waitingCarsToFuel.toString();
				fpQue.poll().fuel(c);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			addWaitingCarToFuel(c);
		}
	}
	
	public void run() {
		System.out.println("In GasS::run");
		while (isOpen) {
			if (!(fpQue.isEmpty())) {
				waitingCarsToFuel.toString();
				notifyCar();
			}/* else {
//				/*synchronized (/*dummyWaiter*///this) {
//					try {
//						System.out.println("gas has no cars waiting");
//						/*dummyWaiter.*/wait(); // wait till there is an airplane
//											// waiting
//						
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}*/
			//}
		}
	}
}
