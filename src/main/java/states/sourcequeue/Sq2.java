package states.sourcequeue;


import events.Event;
import events.LeavingSourceQueueEvent;
import network.Packet;
import network.host.SourceQueue;
import network.layers.PhysicalLayer;

import java.util.Objects;

public class Sq2 extends Sq1 {
	//�	State Sq2: source queue is not empty.

	public Sq2(PhysicalLayer physicalLayer, SourceQueue e)
	{
		super(physicalLayer, e);
	}
	
	public void act(){
		SourceQueue sq = (SourceQueue)elem;
		Packet p = sq.allPackets.get(0);
		if(Objects.isNull(p)) return;

		Event leavingSourceQueue = new LeavingSourceQueueEvent(physicalLayer, p);
		leavingSourceQueue.startTime = sq.phyLayer.sim.time();
		leavingSourceQueue.endTime = sq.phyLayer.sim.time();
		sq.insertEvents(leavingSourceQueue);
		sq.getNextState();
//		sq.state.act();
	}
}
