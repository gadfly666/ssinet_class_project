package events;

import config.Constant;
import elements.Element;
import elements.ExitBuffer;
import network.Packet;
import network.host.SourceQueue;
import network.layers.PhysicalLayer;
import states.exb.X00;
import states.exb.X01;
import states.exb.X10;
import states.exb.X11;
import states.packet.StateP2;
import states.sourcequeue.Sq2;

enum TypeB
{
	B, B1, B2, B3, B4
}

public class LeavingSourceQueueEvent extends Event {
	public TypeB type = TypeB.B;
	//Event dai dien cho su kien loai (B): goi tin roi khoi Source Queue

	private PhysicalLayer physicalLayer;
	private Packet p;

	public LeavingSourceQueueEvent(PhysicalLayer physicalLayer, Packet p){
		this.physicalLayer = physicalLayer;
		this.p = p;
	}
	
	@Override
	public void execute()
	{
		physicalLayer.sq.removeExecutedEvent(this);
		SourceQueue sQueue = physicalLayer.sq;
		ExitBuffer exb = physicalLayer.EXBs[0];
		if(exb.state instanceof X00 || exb.state instanceof X01){
			exb.insertPacket(p);
			exb.getNextState();
			p.state = new StateP2(physicalLayer, exb, p);
			p.state.act();
			sQueue.allPackets.remove(p);
			sQueue.getNextState();
			sQueue.state.act();
		}
	}
}
