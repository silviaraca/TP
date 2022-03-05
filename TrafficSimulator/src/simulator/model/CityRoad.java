package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		
	}

	@Override
	void reduceTotalContamination() {
		
		int x = 0, total;

		if(getWeather() == Weather.WINDY || getWeather() == Weather.STORM) { x = 10; }
		else { x = 2;}
	
		total = getContTotal() - x;
		
		if(total >= 0) { setContTotal(total); }
		else { setContTotal(0); }
	}

	@Override
	void updateSpeedLimit() {
		setLimitSpeed(getMaxSpeed());
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		
		int total;
		
		total = ((11 - getContTotal())*getLimitSpeed())/11;
		
		return total;
	}

	
	
}
