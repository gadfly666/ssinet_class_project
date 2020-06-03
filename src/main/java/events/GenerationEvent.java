package events;

import elements.Element;
import network.host.*;
import network.layers.PhysicalLayer;
import simulator.Simulator;
import network.Packet;
import states.packet.*;
import states.sourcequeue.Sq1;
import states.sourcequeue.Sq2;

public class GenerationEvent extends Event {
    //Event dai dien cho su kien loai (A): goi tin duoc sinh ra
    private static int numSent = 0;

    private SourceQueue sq;
    private PhysicalLayer physicalLayer;

    public GenerationEvent(PhysicalLayer physicalLayer, SourceQueue sq) {
        this.physicalLayer = physicalLayer;
        this.sq = sq;
    }

//	public GenerationEvent(Element elem)
//	{
//		this.elem = elem;
//	}

    @Override

    public void execute() {
        //if(elem instanceof SourceQueue)
        {
            System.out.println("Gen event");
            sq.removeExecutedEvent(this);
            Packet p = this.sq.dequeue(this.startTime);
            if (p == null) return;

            Event leavingSourceQueue = new LeavingSourceQueueEvent(this.sq.phyLayer, p);
            leavingSourceQueue.startTime = (long) p.getStartTime();
            leavingSourceQueue.endTime = (long) p.getStartTime();

            this.sq.insertEvents(leavingSourceQueue);

//			p.setId(numSent);
//			this.pid = p.id;
//			p.state = new StateP1((SourceQueue)elem, p);
//			p.state.act(this);
//
//			if(elem.state instanceof Sq1)//it means that elem is an instance of SourceQueue
//			{
//				elem.state = new Sq2((SourceQueue)elem);
//				elem.state.act(this);
//			}
        }
    }
}
