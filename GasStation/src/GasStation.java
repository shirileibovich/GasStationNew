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
	private CleaningService cs;
	private float fuelingProfits = 0;
	private float cleaningProfits = 0;

	public GasStation(int numOfPumps, MainFuelPool mainPool,
			float pricePerLiter, CleaningService cs) {
		this.numOfPumps = numOfPumps;
		this.mainFuelPool = mainPool;
		this.pricePerLiter = pricePerLiter;
		this.cs = cs;
		Thread csThread = new Thread(cs);
		csThread.start();

		fpArr = new FuelPump[this.numOfPumps];
		for (int i = 0; i < this.numOfPumps; i++) {
			fpArr[i] = new FuelPump(this, i + 1);
			this.addFulePumpToQue(fpArr[i]);
		}
	}

	public boolean isOutsideCleanFree() {
		return !cs.isOutCleaningInUse();
	}

	public void addFulePumpToQue(FuelPump fp) {// que for free pumps
		fpQue.add(fp);
		System.out.println("add fp " + fp.getTheId());

	}

	public boolean whatToDoFirst() {
		if (waitingCarsToClean.size() == waitingCarsToFuel.size())
			return true;
		if (waitingCarsToClean.size() > waitingCarsToFuel.size())
			return true;
		else
			return false;

	}

	public FuelPump getFreePump() {
		System.out.println("fp is out of que");
		return fpQue.poll();
	}

	public void addWaitingCarToFuel(Car c) {

		waitingCarsToFuel.add(c);
		System.out.println("no free pump add car to que " + c.getId());

	}

	public void addWaitingCarToClean(Car c) {

		waitingCarsToClean.add(c);
		System.out.println("outside cleaning is busy, add car to que "
				+ c.getId());

	}

	public synchronized void notifyCarFuel() {
		Car firstCar = waitingCarsToFuel.poll();
		if (firstCar != null) {
			// System.out.println("there is free pump poll car out of que");
			// firstCar.notifyAll();
			synchronized (firstCar) {

				firstCar.notifyAll();
			}
		}

	}

	public CleaningService getCs() {
		return this.cs;
	}

	public synchronized void notifyCarClean() {
		Car firstCar = waitingCarsToClean.poll();
		if (firstCar != null) {
			// System.out.println("there is free pump poll car out of que");
			// firstCar.notifyAll();
			synchronized (firstCar) {

				firstCar.notifyAll();
			}
		}

	}

	public void changeFuelCapacity(float fuelUsed) {
		mainFuelPool.setCurrentCapacity(this.mainFuelPool.getCurrentCapacity()
				- fuelUsed);
		this.fuelingProfits += fuelUsed * this.pricePerLiter;
	}

	public void updateCleaningProfits(float profit) {
		this.cleaningProfits += profit;
	}

	public float getFuelingProfits() {
		return fuelingProfits;
	}

	public float getCleaningProfits() {
		return cleaningProfits;
	}

	public void closeGasStaition(){
		
		if(!this.waitingCarsToFuel.isEmpty()){
			this.waitingCarsToFuel.clear();
		}
		
		while(!this.waitingCarsToFuel.isEmpty()){
			System.out.println("waiting for car to finished fueling ");
		}
		while (!this.cs.getWaitingForInsideCleaningQue().isEmpty()){
			System.out.println("waiting for inside cleanig to finish");
		}
		
		this.cs.setRunning(false);
		this.isOpen = false;
		
		
	}
	public void run() {
		System.out.println("In GasS::run");
		while (isOpen) {
			if (mainFuelPool.getCurrentCapacity() >= mainFuelPool
					.getMaxCapacity() * redLine) {
				if (!(fpQue.isEmpty())) {

					notifyCarFuel();
				} else {
					synchronized (/* dummyWaiter */this) {
						try {

							/* dummyWaiter. */wait(); // wait till there is an
														// car
							// waiting

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			} else {
				System.out.println("not enough fuel");
			}
			if (this.isOutsideCleanFree()) {
				notifyCarClean();
			} else {
				synchronized (/* dummyWaiter */this) {
					try {

						/* dummyWaiter. */wait(); // wait till there is an
													// car
						// waiting

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}

}
