import java.util.Calendar;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car extends Thread {
	private long id;
	private boolean wantClean, wantFule;
	private GasStation theGasStation;
	private long numOfLiters;
	private boolean isRunning;

	public Car(long id, boolean wantClean, boolean wantFule,
			GasStation gasStation, long numOfLiters) {
		this.id = id;
		this.wantClean = wantClean;
		this.wantFule = wantFule;
		this.theGasStation = gasStation;
		this.numOfLiters = numOfLiters;
		this.isRunning = true;

	}

	@Override
	public void run() {
		while (this.isRunning) {
			synchronized (this) {
				// System.out.println("car start" + this.getId());
				if (wantFule && wantClean) {
					if (this.theGasStation.whatToDoFirst()) { // if true fuel
																// else
																// cleaning
						// System.out.println("start fule" + this.getId());
						try {
							this.fule();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						System.out.println("car finished fuel go to cleaning"
								+ this.getId());
						try {
							this.clean();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						try {
							this.clean();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							this.fule();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else if (wantFule) {
					try {
						this.fule();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
				} else {
					try {
						this.clean();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				this.isRunning = false;
			}
		}
	}

	public void fule() throws InterruptedException {
		synchronized (this) {
			theGasStation.addWaitingCarToFuel(this);
			System.out.println("added car to waiting list to fuel "
					+ this.getId());
			wait();
		}
		synchronized (theGasStation) {

			try {
				FuelPump freePump = theGasStation.getFreePump();
				System.out.println("start fuel in fule pump " + this.getId()
						+ " fuel pump num " + freePump.getTheId());
				System.out.println("num of liter " + this.getNumOfLiters()
						+ " car num " + this.getId());
				this.sleep(this.getNumOfLiters() * 10);
				theGasStation.setFuelCounter();
				System.out.println("after sleep = finisined fueling"
						+ this.getId());

				this.theGasStation.addFulePumpToQue(freePump);
				this.theGasStation.changeFuelCapacity(this.getNumOfLiters());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void clean() throws InterruptedException {
		synchronized (this) {
			theGasStation.addWaitingCarToClean(this);
			System.out.println("added car to waiting list to clean "
					+ this.getId());
			wait();
		}
		synchronized (theGasStation.getCs()) {

			try {
				InsideCleaningTeams cleanTeam = theGasStation.getCs()
						.getFreeCleaningTeam();
				System.out.println("start clean inside " + this.getId());
				this.sleep(cleanTeam.getCleaningTime());
				theGasStation.setCleanCounter();
				System.out.println("after sleep = finisined insideCleaning"
						+ this.getId());

				this.theGasStation.getCs().addCleaningTeamToQue(cleanTeam);
				this.theGasStation.updateCleaningProfits(theGasStation.getCs()
						.getPrice());

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isWantClean() {
		return wantClean;
	}

	public void setWantClean(boolean wantClean) {
		this.wantClean = wantClean;
	}

	public boolean isWantFule() {
		return wantFule;
	}

	public void setWantFule(boolean wantFule) {
		this.wantFule = wantFule;
	}

	public GasStation getTheGasStation() {
		return theGasStation;
	}

	public void setTheGasStation(GasStation theGasStation) {
		this.theGasStation = theGasStation;
	}

	public long getNumOfLiters() {
		return numOfLiters;
	}

	public void setNumOfLiters(long numOfLiters) {
		this.numOfLiters = numOfLiters;
	}

}