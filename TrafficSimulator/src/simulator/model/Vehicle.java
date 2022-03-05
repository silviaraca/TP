package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{
	
	private List<Junction> itinerary;
	private int maxSpeed;
	private int actSpeed;
	private VehicleStatus status;
	private Road carretera = null;
	private int local = 0;
	private int contClass;
	private int contTotal;
	private String id;
	private int lastJunct;
	private int dist = 0;
	private Junction nextJunct = null;
	private Junction prevJunct = null;

	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) 
	{
		super(id);
			
		if(maxSpeed <= 0) throw new IllegalArgumentException("El valor de la velocidad maxima es inferior a 0");
		if(contClass < 0 || contClass > 10) throw new IllegalArgumentException("El grado de contaminacion debe estar entre 0 y 10");
		if(itinerary.size() < 2) throw new IllegalArgumentException("El itinerario tiene menos de dos elementos");
		
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.status = VehicleStatus.PENDING;
		this.lastJunct = 0;
	}
	
	void setSpeed(int s) //velocidad actual a s o maxSpeed si s > 0
	{
		if (s < 0)throw new IllegalArgumentException("La velocidad no puede ser menor que 0.");
		if (s <= maxSpeed) {actSpeed = s;}
		else {actSpeed = maxSpeed;}
	}
	
	void setContaminationClass(int c)
	{
		if(c < 0 || c > 10) throw new IllegalArgumentException("El grado de contaminacion debe estar entre 0 y 10");
		contClass = c;
	}
	
	void advance(int time) //se modifica la localización y contaminación
	{
		int contProducida, newLoc = 0;
		
		if(status != VehicleStatus.TRAVELING) {actSpeed = 0;}
		else
		{
			if(local + actSpeed < carretera.getLength()) {newLoc += actSpeed;}
			else { newLoc = carretera.getLength();}
		}
		
		contProducida = contClass * (newLoc - local);
		contTotal += contProducida;
		carretera.addContamination(contProducida);
		local = newLoc;
		
		if(local >= carretera.getLength())
		{
			carretera.getDestJunct().enter(this);
			status = VehicleStatus.WAITING;
			actSpeed = 0;
			lastJunct++;
		}
			
	}
	
	void moveToNextRoad()
	{
		Road nextRoad = null;
		
		if(carretera != null)
			{
				carretera.exit(this); //Sale de la carretera
				
				if(status != VehicleStatus.WAITING && status != VehicleStatus.PENDING)
				{
					throw new IllegalArgumentException("No te puedes mover a otra carretera");
				}
				
				if(lastJunct == itinerary.size() - 1) //Ha terminado su recorrido
				{
					status = VehicleStatus.ARRIVED;
					carretera = null;
					local = 0;
				}
				else
				{
					prevJunct = itinerary.get(lastJunct + 1);
					nextJunct = itinerary.get(lastJunct);
					nextRoad = prevJunct.roadTo(nextJunct);
					nextRoad.enter(this);
					carretera = nextRoad;
					status = VehicleStatus.TRAVELING;		
					local = 0;
				}	
			}
		else{ //nuevo vehiculo, se coloca en la primera carretera del itinerario.
			prevJunct = itinerary.get(0);
			nextJunct = itinerary.get(1);
			carretera = prevJunct.roadTo(nextJunct);
			nextRoad.enter(this);
			carretera = nextRoad;
			local = 0;
			
		}
		
	
	}
	
	public JSONObject report() {

		JSONObject json = new JSONObject();
		
		json.put("id", id);
		json.put("speed", actSpeed);
		json.put("distance", dist);
		json.put("co2", contTotal);
		json.put("class", contClass);
		json.put("status", status);
		
		if (status != VehicleStatus.ARRIVED && status != VehicleStatus.PENDING)
		{
			json.put("road", carretera);
			json.put("location", local);
		}		
		
		return json;
	}

	
	
	//GETTERS Y SETTERS
	
	
	
	public List<Junction> getItinerary() {
		return itinerary;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getActSpeed() {
		return actSpeed;
	}
	
	public int getLocal() {
		return local;
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public Road getCarretera() {
		return carretera;
	}

	public int getContClass() {
		return contClass;
	}

	public int getContTotal() {
		return contTotal;
	}

	public String getId() {
		return id;
	}
	
	
	
}