import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;


public class FuelPump implements Runnable{
	private GasStation theGasStaion;
	private long id;
	
	private Semaphore isInUsed;
	private boolean finishedFueling = false;
	
	
	
	public FuelPump(GasStation gasStaion, long id){
		this.id = id;
		this.theGasStaion = gasStaion;
	}
	
	
	public void fuel(Car car) throws InterruptedException {
		if (theGasStaion.canStartfuel()){
		
			if (isInUsed.tryAcquire()){
			System.out.println("start fuel");
			long fuelTime = car.getNumOfLiters();		
			car.sleep(fuelTime*10);
			fuelUsed(fuelTime);	
			isInUsed.release();		
		}
		else{
			
		}
			
	}
		else
			System.out.println("not enough fuel");
	}

	
	
	public float fuelUsed(float numOfLiters){
		return numOfLiters;
	}
	
	@Override
	public void run() {
		try {
			fuel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public long getTheId() {
		return this.id;
	}
	
	
	public synchronized void addWaitingCar(Car car) {
		waitingAirplanes.add(car);

		System.out.println("After adding car #" 
				+ " there are " + waiting.size()
				+ " airplanes waiting");

		synchronized (/*dummyWaiter*/this) {
			if (waitingAirplanes.size() == 1) {
				/*dummyWaiter.*/notify(); // to let know there is an airplane
										// waiting
			}
		}
	}
	
}
