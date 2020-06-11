package events;

import network.Packet;
import network.host.SourceQueue;
import network.layers.PhysicalLayer;
import simulator.DiscreteEventSimulator;
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
            Packet p = this.sq.generateNewPacket(this.startTime);
            p.id = ((DiscreteEventSimulator)sq.phyLayer.sim).numSent ++;
            if (p == null) return;

            sq.getNextState();
            sq.state.act();


//            Event event = new GenerationEvent(sq.phyLayer, sq);
//            event.startTime = (long)sq.getNextPacketTime();
//            event.endTime = (long)sq.getNextPacketTime();
//            this.sq.insertEvents(event);

        }
    }
}
