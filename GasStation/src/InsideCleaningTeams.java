

public class InsideCleaningTeams {
	private long cleaningTime;

	public InsideCleaningTeams() {
		super();
		this.cleaningTime = (long) (Math.random() * 3000);
	}
	
	public long getCleaningTime() {
		return cleaningTime;
	}
	public void setCleaningTime(long cleaningTime) {
		this.cleaningTime = cleaningTime;
	}
	
	
	
	
}
