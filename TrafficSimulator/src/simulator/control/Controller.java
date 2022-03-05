package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	
	private TrafficSimulator ts;
	private Factory<Event> eventF;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory)
	{
		if (sim == null || eventsFactory == null) throw new IllegalArgumentException("sim y eventsFactory no pueden ser nulos");
		
		this.ts = sim;
		this.eventF = eventsFactory;
	}

	public void loadEvents(InputStream in)
	{
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray ja = jo.getJSONArray("events");
		
		if(!jo.has("events"))  throw new IllegalArgumentException("el JSON no tiene eventos");
		
		for(int i = 0; i < ja.length(); i++) {
			ts.addEvent(eventF.createInstance(ja.getJSONObject(i)));
		}
	}
	
	
	public void run(int n, OutputStream out)
	{
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		PrintStream p = new PrintStream(out);
		
		for (int i = 0; i < n; i++) {
			ts.advance();
			ja.put(ts.report());
		}
		
		jo.put("states", ja);
		p.print(jo.toString(3)); 
		p.close();
		
	}
	
	public void reset() 
	{
		ts.reset();
	}
	
	
}

