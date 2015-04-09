import java.util.LinkedList;
import java.util.Queue;


public class GasStation {

	private boolean isOpen = true;
	private FuelPump fp;
	private Queue<Car> waitingCarsToFuel = new LinkedList<Car>();
	public boolean canStartfuel(){
		
		
		
		return true;
		
		
	}
	
	
	
	
	
	public void addWaitingCar(FuelPump fuelPump){
		
	}
	
	public boolean whatToDoFirst(){
		
		return true;
	}
	
	public FuelPump getPump(){
		return fp;
	}




	
	public void goTocleaning() {
		// TODO Auto-generated method stub
		
	}





	public boolean hasFreePump() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
