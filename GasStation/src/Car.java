import java.util.Calendar;


public class Car  extends Thread{
	private long id;
	private boolean wantClean, wantFule;
	private GasStation theGasStation;
	
	
	public Car (long id,  boolean wantClean,boolean wantFule,GasStation gasStation ){
		this.id =id;
		this.wantClean = wantClean;
		this.wantFule = wantFule;
		this.theGasStation =gasStation;
	}
	
	
	
	
	
	public void fule() throws InterruptedException {
		synchronized (this) {
			theGasStation.addWaitingCar(this);

			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " Airplane #" + getId() + " is waiting to take off");

			wait();
		}

		synchronized (theAirport) {
			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " --> Airplane #" + getId() + " started taking off");
			Thread.sleep((long) (Math.random() * 3000));
			System.out.println(Calendar.getInstance().getTimeInMillis()
					+ " <-- Airplane #" + getId() + " finished taking off");
		
			theAirport.notifyAll();
		}
	}
	

	
	public void clean(){
		
	}
	
	
	
}
