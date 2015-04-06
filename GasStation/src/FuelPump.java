import java.util.Calendar;


public class FuelPump extends Thread {
	private GasStation theGasStaion;
	private int id;
	
	
	
	
	
	public FuelPump(GasStation gasStaion, int id){
		this.id = id;
		this.theGasStaion=gasStaion;
	}
	
	
	public void fuel() throws InterruptedException {
		if (theGasStaion.canStartfuel){
		synchronized (this) {
			theGasStaion.addWaitingAirplaneCar(this);
			wait();
		}

		synchronized (theAirport) {
			System.out.println(start fuel);
			
			long fuelTime = Math.random() * 10000;
			Thread.sleep(fuelTime );
			fuelUsed(fuelTime/10);	
			theAirport.notifyAll();
		}
	}
		else
			System.out.println("not enough fuel");
	}

	
	
	public float fuelUsed(float numOfLiters){
		return numOfLiters;
	}
	
	
	
}
