import java.util.Calendar;


public class Car  extends Thread {
	private long id;
	private boolean wantClean, wantFule;
	private GasStation theGasStation;
	private long numOfLiters;
	
	
	public Car (long id,  boolean wantClean,boolean wantFule,GasStation gasStation, long numOfLiters){
		this.id =id;
		this.wantClean = wantClean;
		this.wantFule = wantFule;
		this.theGasStation =gasStation;
		this.numOfLiters =numOfLiters;
		
	}	
	
@Override	
public void run(){
	if (wantFule && wantClean){
		if (this.theGasStation.whatToDoFirst()){ //if true fuel else cleaning
			this.startFuel();
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
	
	public void startFuel(){
		try {
			if (theGasStation.hasFreePump()){
				this.theGasStation.getPump().fuel(this);
				System.out.println("car turn to pump to start fuel");
			}
			else
				
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void startClean(){
		this.theGasStation.goTocleaning();
		System.out.println("start cleaning");
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