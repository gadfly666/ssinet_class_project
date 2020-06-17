package states.packet;

import states.State;
import states.exb.X00;
import states.exb.X01;
import config.Constant;
import elements.ExitBuffer;
import events.Event;
import events.GenerationEvent;
import events.LeavingSourceQueueEvent;
import network.Packet;
import network.host.SourceQueue;

public class StateP1 extends State {
    //�	State P1: the packet is generated
    public Packet p;

    public StateP1(SourceQueue sq) {
        this.elem = sq;
    }

    public StateP1(SourceQueue sq, Packet p) {
        this.elem = sq;
        this.p = p;
    }

    public StateP1(SourceQueue sq, Packet p, GenerationEvent ev) {
        this.elem = sq;
        this.p = p;
        this.ancestorEvent = ev;
    }

    @Override
    public void act() {
        SourceQueue sQueue = (SourceQueue) elem;
        if (sQueue == null || this.p == null) {
            return;
        }
        if (sQueue.allPackets == null) {
            System.out.println("Empty packet in source queue error!\n");
            return;
        }
        if (sQueue.allPackets.get(0) == this.p) {
            Event e = new LeavingSourceQueueEvent(sQueue.phyLayer, this.p);
            e.startTime = sQueue.phyLayer.sim.time();
            e.endTime = e.startTime;
            e.packet = this.p;
            sQueue.insertEvents(e);
			/*boolean successfullyInserted = exb.insertPacket(this.p);
			if(successfullyInserted)
			{
				sQueue.allPackets.remove(0);
			}*/
        }
    }
}
