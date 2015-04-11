import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FuelPump implements Runnable {
	private GasStation theGasStaion;
	private long id;
	private Lock theLock = new ReentrantLock();
	private Condition notFinishedFueling = theLock.newCondition();
	

	//private Semaphore isInUsed;

	public FuelPump(GasStation gasStaion, long id) {
		this.id = id;
		this.theGasStaion = gasStaion;
	}

	public void fuel(Car car) throws InterruptedException {
		if (theGasStaion.canStartfuel()) {

			synchronized (this) {
				System.out.println("start fuel " + car.getId());
				long fuelTime = car.getNumOfLiters();
				System.out.println("num of liter " + fuelTime + " car num " +car.getId());
				car.sleep(fuelTime * 10);
				System.out.println("after sleep = finisined fueling" + car.getId());
				fuelUsed(fuelTime);
				theGasStaion.addFulePumpToQue(this);
				car.setFinishedFueling(true);
				//car.notify();
			}

		}

		else
			System.out.println("not enough fuel");
	}

	public float fuelUsed(float numOfLiters) {
		return numOfLiters;
	}
/*
	@Override
	public void run() {
		try {
			fuel(Car c);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
*/
	public long getTheId() {
		return this.id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	

}
