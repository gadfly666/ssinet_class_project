package states.packet;

import states.State;
import network.Packet;
import network.host.SourceQueue;

public class StateP1 extends State {
	//•	State P1: the packet is generated
	public Packet p;
	
	public StateP1(SourceQueue e)
	{
		this.elem = e;
	}
	
	public StateP1(SourceQueue e, Packet p)
	{
		this.elem = e;
		this.p = p;
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
			
		}
	}
}
