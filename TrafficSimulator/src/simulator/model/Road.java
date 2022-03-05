package simulator.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{
	
	private Junction oriJunct = null;
	private Junction destJunct = null;
	private int length;
	private int maxSpeed;
	private int limitSpeed;
	private int contTotal;
	private int limiteCont;
	private Weather weather = null;
	private int excCont;
	private List<Vehicle> vehicles;
	private String id;

	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		
		if(maxSpeed <= 0) throw new IllegalArgumentException("La velocidad maxima no puede ser negativa");
		if(contLimit < 0) throw new IllegalArgumentException("El limite de contaminacion no puede ser negativo");
		if(length <= 0) throw new IllegalArgumentException("La longitud no puede ser negativa");
		if(srcJunc == null) throw new IllegalArgumentException("El cruce de origen no puede ser nulo.");
		if(destJunc == null) throw new IllegalArgumentException("El cruce de destino no puede ser nulo.");
		if(weather == null) throw new IllegalArgumentException("La condicion meteorologica no puede ser nula.");
		
		this.oriJunct = srcJunc;
		this.destJunct = destJunc;
		this.maxSpeed = maxSpeed;
		this.limiteCont = contLimit;
		//this.contTotal = contLimit;
		this.length = length;
		this.weather = weather;
		this.id = id;
		
		//srcJunc.addOutGoingRoad(this);
		//destJunc.addIncommingRoad(this);
		
	}
	
	void advance(int time)
	{
		reduceTotalContamination();
		updateSpeedLimit();
		
		for (Vehicle v: vehicles)
		{
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		
	/*	Collections.sort(vehicle, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle v1, Vehicle v2) {
				if(v1.getLocation() > v2.getLocation()) return 1;
				else if(v1.getLocation() < v2.getLocation()) return -1;
				else {
					return v1.getId().compareToIgnoreCase(v2.getId());
				}
			}
		});*/
	}
	
	void enter(Vehicle v)
	{
		if(v.getLocal() != 0 || v.getActSpeed() != 0) throw new IllegalArgumentException("La velocidad y localización deben ser 0");
		vehicles.add(v);
	}
	
	void exit(Vehicle v)
	{
		vehicles.remove(v);
	}
	
	void setWeather(Weather w)
	{
		if(w == null) throw new IllegalArgumentException("La condición meteorológica no puede ser nula");
		weather = w;
	}
	
	void addContamination(int c)
	{
		if(c < 0) throw new IllegalArgumentException("La contaminación no puede ser negativa");
		contTotal += c;
	}
	
	public JSONObject report() 
	{
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		
		jo.put("id", id);
		jo.put("speedlimit", maxSpeed);
		jo.put("weather", weather);
		jo.put("co2", excCont);
		jo.put("vehicles", ja);
		for (Vehicle vehicle : vehicles)
		{
			ja.put(vehicle.getId());
		}
		
		return jo;
	}
	
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);

	
	
	public Weather getWeather() {
		return weather;
	}

	public int getContTotal() {
		return contTotal;
	}

	public void setContTotal(int contTotal) {
		this.contTotal = contTotal;
	}

	public int getLimiteCont() {
		return limiteCont;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	
	public int getLimitSpeed() {
		return limitSpeed;
	}

	public void setLimitSpeed(int limitSpeed) {
		this.limitSpeed = limitSpeed;
	}

	public Junction getDestJunct() {
		return destJunct;
	}

	public Junction getOriJunct() {
		return oriJunct;
	}

	public int getLength() {
		return length;
	}

	
}
