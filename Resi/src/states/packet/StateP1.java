package states.packet;

import states.State;
import config.Constant;
import elements.ExitBuffer;
import events.Event;
import events.GenerationEvent;
import events.LeavingSourceQueueEvent;
import network.Packet;
import network.host.SourceQueue;

public class StateP1 extends State {
	//•	State P1: the packet is generated
	public Packet p;
	
	public StateP1(SourceQueue sq)
	{
		this.elem = sq;
	}
	
	public StateP1(SourceQueue sq, Packet p)
	{
		this.elem = sq;
		this.p = p;
	}
	
	public StateP1(SourceQueue sq, Packet p, GenerationEvent ev)
	{
		this.elem = sq;
		this.p = p;
		this.ancestorEvent = ev;
	}
	
	public void act()
	{
		SourceQueue sQueue = (SourceQueue)elem;
		if(sQueue == null || this.p == null)
		{
			return;
		}
		if(sQueue.allPackets == null)
		{
			System.out.println("Empty packet in source queue error!\n");
			return;
		}
		if(sQueue.allPackets.get(0) == this.p)
		{
			ExitBuffer exb = sQueue.phyLayer.EXBs[0];//Kiem tra xem EXB co cho trong hay khong?
			int index = exb.indexOfEmpty();
			if(index < Constant.QUEUE_SIZE)//neu EXB con cho trong
			{
				Event e = new LeavingSourceQueueEvent();
				e.startTime = sQueue.phyLayer.sim.time();
				e.endTime = e.startTime;
				e.pid = this.p.id;
				sQueue.insertEvents(e);//chen them su kien moi vao
			}
			/*boolean successfullyInserted = exb.insertPacket(this.p);
			if(successfullyInserted)
			{
				sQueue.allPackets.remove(0);
			}*/
		}
	}
}
