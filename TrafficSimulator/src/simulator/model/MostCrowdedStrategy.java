package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{

private int ticks;
	
	public MostCrowdedStrategy(int timeSlot) {
		
		ticks= timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
	
		if(roads.isEmpty()) { return -1; } //lista vacía
		if(currGreen == -1) { return busqueda(0, qs); } 
		if((currTime - lastSwitchingTime) < ticks) { return currGreen;}
		
		return busqueda((currGreen + 1)%qs.size(), qs);
	}

	private int busqueda(int pos, List<List<Vehicle>> qs)
	{
		int max = qs.get(pos).size();
		
		for (int i = 1; i < qs.size(); i++) {
			if(max < qs.get(i).size()) {max = qs.get(i).size();} 
		}
		
		return max;
		
	}
}
