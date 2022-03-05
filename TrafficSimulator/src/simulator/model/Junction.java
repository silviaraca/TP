package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> inRoadList;
	private Map<Junction,Road> outRoadList;
	private List<List<Vehicle>> qs;
	private Map<Road,List<Vehicle>> carretera_cola;
	private int indGreen;
	private int ultCambSem = 0;
	private LightSwitchingStrategy lss;
	private  DequeuingStrategy ds;
	private int x_coord;
	private int y_coord;
	private String id;
	
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
			
		if(lsStrategy == null) throw new IllegalArgumentException("Las estrategias no pueden ser nulas");
		if(dqStrategy == null) throw new IllegalArgumentException("Las estrategias no pueden ser nulas");
		if(xCoor < 0) throw new IllegalArgumentException("Las coordenadas no pueden ser negativas");
		if(yCoor < 0) throw new IllegalArgumentException("Las coordenadas no pueden ser negativas");

		this.id = id;
		this.lss = lsStrategy;
		this.ds = dqStrategy;
		this.x_coord = xCoor;
		this.y_coord = yCoor;
		
		outRoadList = new HashMap<Junction, Road>();
		inRoadList = new ArrayList<Road>();
		qs = new ArrayList<List<Vehicle>>();
		carretera_cola = new HashMap<Road, List<Vehicle>>();
			
	}
	
	void addIncommingRoad(Road r) 
	{
		List<Vehicle> qs_r = new LinkedList<Vehicle>();		
		
		if(r.getDestJunct() == this) //Comprobamos que el cruce destino es este, y si es as�, a�adimos la carretera a la lista
		{
			inRoadList.add(r);
			qs.add(qs_r);
			carretera_cola.put(r, qs_r);
			
		}
		else {throw new IllegalArgumentException("El cruce destino debe ser this");}

	}
	
	void addOutGoingRoad(Road r)
	{

		if(this.equals(r.getDestJunct())){ throw new IllegalArgumentException("El cruce es incorrecto");}
		if(outRoadList.get(r.getDestJunct()) != null){ throw new IllegalArgumentException("Ya existe una carretera entre esos cruces");}
		
		outRoadList.put(r.getDestJunct(), r);
	}
	
	void enter(Vehicle v)
	{
		Road r = v.getCarretera();
		
		List<Vehicle> q = carretera_cola.get(r); //q es la cola de la carretera r
		q.add(v);
	}
	
	Road roadTo(Junction j)
	{
		return outRoadList.get(j);
	}
	
	
	@Override
	void advance(int time) {
		
		int strat;
		List<Vehicle> qr_next;
		
		if (indGreen != -1 && !inRoadList.isEmpty())
		{	
			qr_next = ds.dequeue(qs.get(indGreen));
		
			for (int i = 0; i < qr_next.size(); i++) 
			{
				qr_next.get(i).moveToNextRoad();
				qs.get(indGreen).remove(i);
				
			}
		}
		
		
		indGreen = lss.chooseNextGreen(inRoadList, qs, indGreen, ultCambSem, time);
		ultCambSem = time;
		
	}

	@Override
	public JSONObject report() {
		
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		JSONObject jCarreteras = new JSONObject();
		JSONArray jArray2 = new JSONArray();
		
		jo.put("id", id);
		
		if (indGreen == -1)	{jo.put("green", "none");}
		else{jo.put("green", inRoadList.get(indGreen).getId());}
		
		jo.put("queues", ja);
		
		if(!qs.isEmpty())
		{
			for (Road roads : inRoadList)
			{
				ja.put(jCarreteras);
				jCarreteras.put("road", roads.getId());
				jCarreteras.put("vehicles", jArray2);
				for (Vehicle v : carretera_cola.get(roads))
				{
					jArray2.put(v.getId());
				}
			}
		}
			
		return jo;
}
	
	

}
