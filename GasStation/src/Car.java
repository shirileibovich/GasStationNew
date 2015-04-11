import java.util.Calendar;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Car  extends Thread {
	private long id;
	private boolean wantClean, wantFule;
	private GasStation theGasStation;
	private long numOfLiters;
	private boolean finishedFueling, finisedCleaning;
	
	
	
	public Car (long id,  boolean wantClean,boolean wantFule,GasStation gasStation, long numOfLiters){
		this.id =id;
		this.wantClean = wantClean;
		this.wantFule = wantFule;
		this.theGasStation =gasStation;
		this.numOfLiters =numOfLiters;
		
		if (wantFule)
			finishedFueling =false;
		
		if (wantClean)
			finisedCleaning =false;
	}	
	
@Override	
public void run(){
	synchronized(this){
	System.out.println("car start" + this.getId());
	if (wantFule && wantClean){
		if (this.theGasStation.whatToDoFirst()){ //if true fuel else cleaning
			System.out.println("start fule" + this.getId());
			this.startFuel();
			System.out.println("car finished fuel go to cleaning" + this.getId());
			this.startClean();
			
	}
		else{
			this.startClean();
			this.startFuel();
		}
		
	}
	else
		if (wantFule){
			this.startFuel();
		}
		else{
			this.startClean();
		}
	}
			
}
	
	public void startFuel(){
			
			System.out.println("car turn to pump to start fuel " + this.getId());
			this.theGasStation.carWantToFule(this);
		
	}

	public void startClean(){
		this.theGasStation.goTocleaning();
		System.out.println("start cleaning " + this.getId());
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

	public boolean isFinishedFueling() {
		return finishedFueling;
	}

	public void setFinishedFueling(boolean finishedFueling) {
		this.finishedFueling = finishedFueling;
	}

	public boolean isFinisedCleaning() {
		return finisedCleaning;
	}

	public void setFinisedCleaning(boolean finisedCleaning) {
		this.finisedCleaning = finisedCleaning;
	}

	
	
	
}