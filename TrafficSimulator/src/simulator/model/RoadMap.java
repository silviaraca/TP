package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	private List<Junction> junctList;
	private List<Road> roadList;
	private List<Vehicle> vehicList;
	private Map<String,Junction> junctMap;
	private Map<String,Road> roadMap;
	private Map<String,Vehicle> vehicMap;
	
	protected RoadMap(){
		
		//Inicilizamos listas
		junctList = new ArrayList<Junction>();
		roadList = new ArrayList<Road>();
		vehicList = new ArrayList<Vehicle>();
		
		//Inicializamos mapas
		junctMap = new HashMap<String,Junction>();
		roadMap = new HashMap<String,Road>();
		vehicMap = new HashMap<String,Vehicle>();
	}
	
	
	void addJunction(Junction j) {
		int pos = 0;
		boolean found = false;
		
		while(pos < junctList.size() && !found) {
			if(junctList.get(pos) == j) {
				found = true;
			}
			pos++;
		}
		if(!found) {
			junctList.add(j);
			junctMap.put(j.getId(), j);
		}			
	}
	
	
	void addRoad(Road r) {
		int pos = 0;
		boolean found = false;
		
		while(pos < roadList.size() && !found) {
			if(roadList.get(pos) == r) {
				found = true;
			}
			pos++;
		}
		if(!found) 
		{
			if(roadMap.containsValue(r.getDestJunct()) && roadMap.containsValue(r.getOriJunct()))
			{	
				roadList.add(r);
				roadMap.put(r.getId(), r);
			}
			else {throw new IllegalArgumentException("Los cruces no exsten en el mapa de caretera");}
		
		}
			else {throw new IllegalArgumentException("Ya existe una caretera con el mismo identificador");}
	}
	
	
	void addVehicle(Vehicle v) { 
		int pos = 0;
		boolean found = false;
		
		while(pos < vehicList.size() && !found) {
			if(vehicList.get(pos) == v) {
				found = true;
			}
			pos++;
		}
		if(!found) 
		{
			for (int i = 0; i < v.getItinerary().size()-1; i++)
			{
				if (v.getItinerary().get(i).roadTo(v.getItinerary().get(i + 1)) == null)
				{
					throw new IllegalArgumentException("No tiene  itinerario");
				}
			}
			
			vehicList.add(v);
			vehicMap.put(v.getId(), v);
		
		}
			else {throw new IllegalArgumentException("Ya existe una caretera con el mismo identificador");}
	}
	
	void reset() {
		
		roadList.clear();
		vehicList.clear();
		junctList.clear();
		roadMap.clear();
		junctMap.clear();
		vehicMap.clear();
		
	}
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		JSONArray jJunc = new JSONArray();
		JSONArray jRoad = new JSONArray();
		JSONArray jVehic = new JSONArray();

		for(Road i : roadList) {
			jRoad.put(i.report());
		}
		jo.put("road", jRoad);
		
		for(Junction i : junctList) {
			jJunc.put(i.report());
		}
		jo.put("junctions", jJunc);
		
		for(Vehicle i : vehicList) {
			jVehic.put(i.report());
		}
		jo.put("vehicles", jVehic);
		
		return jo;
		
	}
	

	public Junction getJunction(String id) {
		return null;
		
		
	}
	public Road getRoad(String id) {
		return null;
		
	}
	public Vehicle getVehicle(String id) {
		return null;
		
	}
	public List<Junction>getJunctions(){
		return junctList;
		
	}
	public List<Road>getRoads(){
		return roadList;
		
	}
	public List<Vehicle>getVehicles(){
		return vehicList;
		
	}
	
}
