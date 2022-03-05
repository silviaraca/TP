package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy{

	private int ticks;

	public RoundRobinStrategy(int timeSlot) {
		
		ticks = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
	
		if(roads.isEmpty()) { return -1; } //lista vac�a
		if(currGreen == -1) { return 0; } //semaforo verde
		if((currTime - lastSwitchingTime) < ticks) { return currGreen;}
		
		return (currGreen + 1)%roads.size();
	}
	

}
