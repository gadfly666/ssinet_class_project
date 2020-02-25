package events;

import elements.Element;
import network.host.*;
import network.Packet;
import states.packet.*;

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
			p.setId(numSent);
			p.state = new StateP1();
		}
	}
}
