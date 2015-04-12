import java.util.LinkedList;
import java.util.Queue;



public class GasStation implements Runnable {
	private static float redLine = 0.05f;
	private boolean isOpen = true;
	private Queue<FuelPump> fpQue = new LinkedList<FuelPump>();;
	private Queue<Car> waitingCarsToFuel = new LinkedList<Car>();
	private Queue<Car> waitingCarsToClean = new LinkedList<Car>();
	private MainFuelPool mainFuelPool = new MainFuelPool(500, 300);
	private float pricePerLiter;
	private int numOfPumps;
	private FuelPump[] fpArr;

	public GasStation(int numOfPumps, MainFuelPool mainPool, float pricePerLiter) {
		this.numOfPumps = numOfPumps;
		this.mainFuelPool = mainPool;
		this.pricePerLiter = pricePerLiter;
		fpArr = new FuelPump[this.numOfPumps];
		for (int i = 0; i < this.numOfPumps; i++) {
			fpArr[i] = new FuelPump(this, i + 1);
			this.addFulePumpToQue(fpArr[i]);
		}
	}

	public void addFulePumpToQue(FuelPump fp) {// que for free pumps
		fpQue.add(fp);
		System.out.println("add fp " + fp.getTheId() );
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



	public void addWaitingCarToFuel(Car c) {

		waitingCarsToFuel.add(c);
		System.out.println("no free pump add car to que " + c.getId());

	}

	public synchronized void notifyCar() {
		Car firstCar = waitingCarsToFuel.poll();
		if (firstCar != null) {
			//System.out.println("there is free pump poll car out of que");
			// firstCar.notifyAll();
			synchronized (firstCar) {
				
				firstCar.notifyAll();
			}
		}

	}

	public void changeFuelCapacity(float fuelUsed) {
		mainFuelPool.setCurrentCapacity(this.mainFuelPool.getCurrentCapacity()
				- fuelUsed);
	}

	public void run() {
		System.out.println("In GasS::run");
		while (isOpen) {
			if (mainFuelPool.getCurrentCapacity() >= mainFuelPool
					.getMaxCapacity() * redLine) {
				if (!(fpQue.isEmpty())) {
					
					notifyCar();
				}
				else {
					synchronized (/*dummyWaiter*/this) {
						try {
							System.out.println("Airport has no airplanes waiting");
							/*dummyWaiter.*/wait(); // wait till there is an airplane
												// waiting
							System.out
									.println("Airport recieved a message there is an airplane waiting");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
					

			} else {
				System.out.println("not enough fuel");
			}

		}
	}

	public Car getCarToFuel() {
		// TODO Auto-generated method stub
		return null;
	}
}
