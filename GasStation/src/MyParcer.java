import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.security.AllPermission;
import java.util.LinkedList;
import java.util.Queue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class MyParcer {

	static Document dom;
	static GasStation gs;
	//private static Queue<Car> cars;
	public static Car createCar(long id, boolean wantClean, boolean wantFule, long numOfLiters){
		Car c = new Car(id, wantClean, wantFule, gs, numOfLiters);
		return c;
	}
	public static GasStation parseXmlFile(){
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
 
        try {
 
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
 
            //parse using builder to get DOM representation of the XML file
            dom = db.parse("gas.xml");
            Queue<Car> allCars=parseDocument();
            for( Car c:allCars){
            	c.start();}
            return gs;
 
        }catch(ParserConfigurationException pce) {
            pce.printStackTrace();
        }catch(SAXException se) {
            se.printStackTrace();
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
		return null;
    }
	
	
    private static Queue<Car> parseDocument(){
        //get the root element
    	MainFuelPool mfp=null;
    	CleaningService cs=null;
    	Queue<Car>cars= new LinkedList<Car>();
    	Element rootElement = dom.getDocumentElement();
        int numOfPumps=Integer.parseInt(rootElement.getAttribute("numOfPumps"));
        Float pricePerLiter=Float.parseFloat(rootElement.getAttribute("pricePerLiter"));
        NodeList mList=rootElement.getChildNodes();
        for (int i = 0; i < mList.getLength(); i++) {
            Node node = mList.item(i);
            if (node instanceof Element){
                Element elem = (Element) node;
	        	switch(elem.getNodeName()){
	        	case "MainFuelPool":
	        		 mfp=new MainFuelPool(Float.parseFloat(elem.getAttribute("maxCapacity")),Float.parseFloat(elem.getAttribute("currentCapacity")));            	
	        	break;
	        	case "CleaningService":
	        		cs=new CleaningService(Integer.parseInt(elem.getAttribute("numOfTeams")), Float.parseFloat(elem.getAttribute("price")),Integer.parseInt(elem.getAttribute("numOfTeams")) );
	        		break;
	        	case "Cars":
	        		NodeList nl = elem.getChildNodes();
	                if(nl != null && nl.getLength() > 0) 
	                    for(int j  = 0 ; j < nl.getLength();j++) {
	                    	Node cn = nl.item(j);
	                        if (cn instanceof Element){
	                            Element carElem = (Element) cn;
	                            Element elemWF=null;
	                            boolean wantsFuel=carElem.hasChildNodes()?true:false;
	                            long numofLiters = 0;
	                            if(wantsFuel){
	                            	Node wf=carElem.getFirstChild();
	                            	if (wf instanceof Element){
	                                    elemWF = (Element) wf;
	                                    numofLiters=Long.parseLong(elemWF.getAttribute("numOfLiters"));
	                            	}
	                            }
	                            String want=carElem.getAttribute("wantCleaning");
	                            boolean wantsCleaning=Boolean.parseBoolean(want);
	                            long id= Long.parseLong(carElem.getAttribute("id"));
	                            
	                         //GasStation g = gs; //  System.out.println(gs);
	                         
	                            Car c= createCar(id, wantsCleaning, wantsFuel, wantsFuel?numofLiters:1);
	                            		//new Car(id,,,g,);
	                            System.out.println("create car");
	                            
	                            cars.add(c);
	                        }    
	                    }
	            break;
	        	}
	        	if(mfp!=null&&cs!=null){
	        		System.out.println(">>>in if");
	        		gs=new GasStation(numOfPumps, mfp, pricePerLiter,cs);
	            	mfp=null;
	        	}
	        		
	        }
        }
        return cars;
    }	
}
