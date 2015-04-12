
public class MainFuelPool {
	private float maxCapacity;
	private float currentCapacity;
	

	
	public MainFuelPool(float maxCapacity, float currentCapacity) {
		super();
		this.maxCapacity = maxCapacity;
		this.currentCapacity = currentCapacity;
	}
	
	
	public float getCurrentCapacity() {
		return currentCapacity;
	}
	public void setCurrentCapacity(float currentCapacity) {
		this.currentCapacity = currentCapacity;
	}
	public float getMaxCapacity() {
		return maxCapacity;
	}

	
	
	
}
