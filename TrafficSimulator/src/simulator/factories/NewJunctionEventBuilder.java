package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

	private Factory<LightSwitchingStrategy> _lss;
	private Factory<DequeuingStrategy> _dqs;
	private int x, y, time;
	private LightSwitchingStrategy lss;
	private DequeuingStrategy dqs;
	private String id;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {

		super("new_junction");
		_lss = lssFactory;
		_dqs = dqsFactory;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		
		time = data.getInt("time");
		id = data.getString("id");
		x = data.getJSONArray("coor").getInt(0);
		y = data.getJSONArray("coor").getInt(1);
		JSONObject ls = data.getJSONObject("ls_strategy");
		JSONObject dq = data.getJSONObject("dq_strategy");
		lss = _lss.createInstance(ls);
		dqs = _dqs.createInstance(dq);
		
		return new NewJunctionEvent(time, id, lss, dqs, x, y);
	}

}
