package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{
	
	private  List<Pair<String,Weather>> ws;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		
		if(ws == null) throw new IllegalArgumentException("ws no puede ser nulo");
		
		this.ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		
		//Miramos si existe la carretera, y si es así, le cambiamos el weather
		for(Pair<String, Weather> i : ws) {
			if(map.getRoads().contains(map.getRoad(i.getFirst()))) {
				map.getRoad(i.getFirst()).setWeather(i.getSecond());
			}
			else throw new IllegalArgumentException("Esta carretera no existe");
		}
	}

}
