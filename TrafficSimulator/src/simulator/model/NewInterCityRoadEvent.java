package simulator.model;

public class NewInterCityRoadEvent extends Event{
	
	private String id, srcJun, destJun;
	private int length, co2Limit, maxSpeed;
	private Weather weather;
	
	public NewInterCityRoadEvent(int time, String id, String srcJun,String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
			super(time);
			
			this.id = id;
			this.srcJun = srcJun;
			this.destJun = destJunc;
			this.length = length;
			this.co2Limit = co2Limit;
			this.maxSpeed = maxSpeed;
			this.weather = weather;
			
			}


	@Override
	void execute(RoadMap map) {
		
		Road interCityRoad = new InterCityRoad(id, map.getJunction(srcJun), map.getJunction(destJun), maxSpeed, co2Limit, length, weather);
		
		map.addRoad(interCityRoad);
		
		
	}

}
