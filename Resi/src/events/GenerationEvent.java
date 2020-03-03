package events;

import elements.Element;
import network.host.*;
import simulator.Simulator;
import network.Packet;
import states.packet.*;
import states.sourcequeue.Sq1;
import states.sourcequeue.Sq2;

public class GenerationEvent extends Event {
	//Event dai dien cho su kien loai (A): goi tin duoc sinh ra
	public int numSent = 0;
	
	public GenerationEvent() {
		
	}
	
	public GenerationEvent(Element elem)
	{
		this.elem = elem;
	}
	
	@Override
	
	public void execute()
	{
		if(elem instanceof SourceQueue)
		{
			SourceQueue sq = (SourceQueue)elem;
			sq.allEvents.remove(this);
			Packet p = sq.dequeue(this.startTime);
			if(p == null) return;
			p.setId(numSent);
			this.pid = p.id;
			p.state = new StateP1(sq, p);
			p.state.act();
			if(sq.state instanceof Sq1)
			{
				sq.state = new Sq2(sq);
				sq.state.act();
			}
		}
	}
}
