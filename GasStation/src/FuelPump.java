

public class FuelPump extends Thread {
	private GasStation theGasStaion;
	private long id;
	
	
	
	
	
	public FuelPump(GasStation gasStaion, long id){
		this.id = id;
		this.theGasStaion=gasStaion;
	}
	
	
	public void fuel() throws InterruptedException {
		if (theGasStaion.canStartfuel()){
		synchronized (this) {
			theGasStaion.addWaitingCar(this);
			wait();
		}

		synchronized (theGasStaion) {
			System.out.println("start fuel");
			
			long fuelTime = (long) (Math.random() * 10000);
			Thread.sleep(fuelTime );
			fuelUsed(fuelTime/10);	
			theGasStaion.notifyAll();
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
	
}
