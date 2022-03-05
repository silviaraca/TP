package simulator.model;

public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contTotal, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contTotal, length, weather);

	}
	
	@Override
	void reduceTotalContamination()
	{
		int x = 0, tc;
		
		if(getWeather() == Weather.SUNNY) { x = 2; }
		if(getWeather() == Weather.CLOUDY) { x = 3; }
		if(getWeather() == Weather.RAINY) { x = 10; }
		if(getWeather() == Weather.WINDY) { x = 15; }
		if(getWeather() == Weather.STORM) { x = 20; }
		
		tc = ((100 - x)*getContTotal())/100;
		setContTotal(tc);
		
	}

	@Override
	void updateSpeedLimit() {
	
		int limitSpeed;
		
		if(getContTotal() > getLimiteCont()) { limitSpeed = getMaxSpeed()/2;}
		else{ limitSpeed = getMaxSpeed();}
		
		setLimitSpeed(limitSpeed);	
		
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int speed;
		
		if(getWeather() == Weather.STORM){ speed = (getLimitSpeed()*8)/10;}
		else{ speed = getLimitSpeed();}
		
		return speed;
	}
	

}
