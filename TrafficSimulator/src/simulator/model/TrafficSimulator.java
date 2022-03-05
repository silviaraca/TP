package simulator.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	
	private RoadMap rm;
	private List<Event> eventList;
	private int time;
	
	public TrafficSimulator() {
		this.rm = new RoadMap();
		this.eventList = new SortedArrayList<Event>();
		time = 0;
	}

	public void addEvent(Event e) {
		eventList.add(e);
		
	}

	public void advance() {
		
		time++;
		if(!eventList.isEmpty()) //Vamos ejecutando los eventos que tengan el mismo time
		{
			for (Event e : eventList) {
				if(e.getTime() == time)
				{
					e.execute(rm);
					eventList.remove(e);
				}
			}
		}
		
		for (Junction j : rm.getJunctions()) //Advance de cruces
		{
			j.advance(time);
		}
		
		for (Road r : rm.getRoads()) //Advance de carreteras
		{
			r.advance(time);
		}	
		
	}

	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("time", time);
		jo.put("state", rm.report());
		
		return jo;	
	}

	public void reset() {
		time = 0;
		rm.reset();
		eventList.clear();		
	}

}
