package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event{
	
	private List<Pair<String,Integer>> cs;

	public SetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		
		if(cs == null) throw new IllegalArgumentException("cs no puede ser null");
		
		this.cs = cs;
	
	}


	@Override
	void execute(RoadMap map) {
		
		//Miramos si existe el vehiculo, y si es así, le cambiamos el cs
				for(Pair<String, Integer> i : cs) {
					if(map.getVehicles().contains(map.getVehicle(i.getFirst()))) {
						map.getVehicle(i.getFirst()).setContaminationClass(i.getSecond());
					}
					else throw new IllegalArgumentException("El vehículo no existe");
				}
		
	}

}
