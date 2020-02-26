package events;

import elements.Element;
import network.host.*;
import network.Packet;
import states.packet.*;
import states.sourcequeue.Sq1;
import states.sourcequeue.Sq2;

public class GenerationEvent extends Event {
	//Event dai dien cho su kien loai (A): goi tin duoc sinh ra
	public int numSent;
	@Override
	
	public void execute(Element elem)
	{
		if(elem instanceof SourceQueue)
		{
			SourceQueue sq = (SourceQueue)elem;
			Packet p = sq.dequeue(this.startTime);
			if(p == null) return;
			p.setId(numSent);
			p.state = new StateP1(sq, p);
			if(sq.state instanceof Sq1)
			{
				sq.state = new Sq2(sq);
				sq.state.act();
			}
		}
	}
}
