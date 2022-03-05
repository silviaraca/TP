package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
	
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		
		return new RoundRobinStrategy(data.optInt("timeslot", 1));
	}

}
