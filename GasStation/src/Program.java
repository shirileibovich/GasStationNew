



public class Program {

	public static void main(String[] args) {
		GasStation thegas ;
		
		thegas=MyParcer.parseXmlFile();
		
		Thread GasThread = new Thread(thegas);

		GasThread.start();
		
		ConsoleUI cui=new ConsoleUI(thegas);
		cui.startCUI();
	  
		
		
		
 	}
	

}
