

public class FuelPump {
	private GasStation theGasStaion;
	private long id;
	


	public FuelPump(GasStation gasStaion, long id) {
		this.id = id;
		this.theGasStaion = gasStaion;
	}

	
	public long getTheId() {
		return this.id;
	}

	

	

}
