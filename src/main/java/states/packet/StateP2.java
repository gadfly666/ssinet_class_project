package states.packet;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import elements.ExitBuffer;
import elements.Way;
import events.Event;
import events.LeavingEXBEvent;
import network.Link;
import network.Node;
import network.Packet;
import network.layers.PhysicalLayer;
import simulator.DiscreteEventSimulator;
import states.State;
import states.unidirectionalway.W0;

public class StateP2 extends State {
	//�	State P2: the packet is located at EXB of the source node.
    private ExitBuffer exb;
    private PhysicalLayer physicalLayer;
    private Packet p;

    public StateP2(PhysicalLayer physicalLayer, ExitBuffer exb, Packet p){
        this.exb = exb;
        this.physicalLayer = physicalLayer;
        this.p = p;
    }

    public void act(){
        Link link = physicalLayer.links.get(physicalLayer.sq.getSourceId());
        Node currentNode = link.ways.get(exb.nodeId).from;
        Way way = link.ways.get(currentNode.id);
        if(way.state instanceof W0) {
            Event leavingEXBEvent = new LeavingEXBEvent(physicalLayer.links.get(physicalLayer.sq.getSourceId()), exb);
            leavingEXBEvent.startTime = physicalLayer.sim.time();
            leavingEXBEvent.endTime = physicalLayer.sim.time();
            exb.insertEvents(leavingEXBEvent);
            p.id = ((DiscreteEventSimulator)physicalLayer.sim).numSent++;
        }
    }
}
