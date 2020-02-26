package states.sourcequeue;

import events.Event;
import events.GenerationEvent;
import network.host.SourceQueue;
import states.State;

public class Sq1 extends State {
	//•	State Sq1: source queue is empty.
	public Sq1(SourceQueue e)
	{
		this.elem = e;
	}
	
	public void act()
	{
		SourceQueue sQueue = (SourceQueue)elem;
		if(sQueue.allEvents.isEmpty())
		{
			Event e = new GenerationEvent();
			e.startTime = (long)sQueue.getNextPacketTime();
			e.endTime = e.startTime;
			sQueue.allEvents.add(e);
		}
	}
}
