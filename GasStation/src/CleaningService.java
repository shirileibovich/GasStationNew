import java.util.LinkedList;
import java.util.Queue;

public class CleaningService implements Runnable {
	private int numOfTeams;
	private float price;
	private int secondsPerAutoClean;
	private boolean outsideCleaningInUse;
	private Queue<Car> WaitingForInsideCleaningQue = new LinkedList<Car>();
	private Queue<InsideCleaningTeams> freeCleaningTeam = new LinkedList<InsideCleaningTeams>();
	private InsideCleaningTeams[] insideTeamArr;
	private boolean isRunning;

	public CleaningService(int numOfTeams, float price, int secondsPerAutoClean) {
		super();
		this.numOfTeams = numOfTeams;
		this.price = price;
		this.secondsPerAutoClean = secondsPerAutoClean;
		this.outsideCleaningInUse = false;
		this.insideTeamArr = new InsideCleaningTeams[this.numOfTeams];
		this.isRunning = true;

		for (int i = 0; i < this.numOfTeams; i++) {
			insideTeamArr[i] = new InsideCleaningTeams();
			this.addCleaningTeamToQue(insideTeamArr[i]);
		}

	}

	public int getNumOfTeams() {
		return numOfTeams;
	}

	public void setNumOfTeams(int numOfTeams) {
		this.numOfTeams = numOfTeams;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getSecondsPerAutoClean() {
		return secondsPerAutoClean;
	}

	public void setSecondsPerAutoClean(int secondsPerAutoClean) {
		this.secondsPerAutoClean = secondsPerAutoClean;
	}

	public boolean isOutCleaningInUse() {
		return outsideCleaningInUse;
	}

	public void setOutCleaningInUse(boolean outCleaningInUse) {
		this.outsideCleaningInUse = outCleaningInUse;
	}

	public Queue<Car> getWaitingForInsideCleaningQue() {
		return WaitingForInsideCleaningQue;
	}

	public void setWaitingForInsideCleaningQue(
			Queue<Car> waitingForInsideCleaningQue) {
		WaitingForInsideCleaningQue = waitingForInsideCleaningQue;
	}

	public void addCarToOutsideCleaningQue(Car c) {
		this.WaitingForInsideCleaningQue.add(c);
	}

	public synchronized void notifyCarClean() {
		Car firstCar = WaitingForInsideCleaningQue.poll();
		if (firstCar != null) {
			// System.out.println("there is free pump poll car out of que");
			// firstCar.notifyAll();
			synchronized (firstCar) {

				firstCar.notifyAll();
			}
		}

	}

	public void addCleaningTeamToQue(InsideCleaningTeams inTeam) {
		freeCleaningTeam.add(inTeam);

	}

	public InsideCleaningTeams getFreeCleaningTeam() {
		if (!freeCleaningTeam.isEmpty()) {
			System.out.println("cleaning team is out of que");
			return freeCleaningTeam.poll();
		}
		return null;
	}

	public boolean isthereFreeTeam() {
		return !freeCleaningTeam.isEmpty();
	}

	public synchronized void notifyCarInsideClean() {
		Car firstCar = WaitingForInsideCleaningQue.poll();
		if (firstCar != null) {
			// System.out.println("there is free pump poll car out of que");
			// firstCar.notifyAll();
			synchronized (firstCar) {

				firstCar.notifyAll();
			}
		}

	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	@Override
	public void run() {
		while (this.isRunning) {
			if (!freeCleaningTeam.isEmpty()) {
				notifyCarInsideClean();
			}
		}

	}

}
