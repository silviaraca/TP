package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event> {

	private String srcJun, destJun;
	private int time;
	private int length;
	private int co2Limit;
	private int maxSpeed;
	private Weather weather;
	private String id;
	
	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
	}

	
	@Override
	protected Event createTheInstance(JSONObject data) {
		
		time = data.getInt("time");
		id = data.getString("id");
		srcJun = data.getString("src");
		destJun = data.getString("dest");
		length = data.getInt("length");
		co2Limit = data.getInt("co2limit");
		maxSpeed = data.getInt("maxspeed");
		weather = Weather.valueOf(data.getString("weather"));
		
		return new NewInterCityRoadEvent(time, id, srcJun, destJun, length, co2Limit, maxSpeed, weather);
	}
}
