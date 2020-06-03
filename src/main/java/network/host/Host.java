package network.host;

import events.Event;
import events.GenerationEvent;
import network.Link;
import network.Node;
import network.layers.PhysicalLayer;


/**
 * Created by Dandoh on 6/27/17.
 */
public class Host extends Node {

    public Host(int id) {
        super(id);
        this.physicalLayer = new PhysicalLayer(this);
    }

    public Host link(Link link) {
        this.physicalLayer.links.add(link);
        return this;
    }

    @Override
    public void clear() {

    }

    public void generatePacket(int destination) {
        if (this.physicalLayer.sq == null)
            this.physicalLayer.sq = new SourceQueue(this.id, destination);
        else
            this.physicalLayer.sq.setDestionationID(destination);
        Event ev = new GenerationEvent(this.physicalLayer, this.physicalLayer.sq);
        ev.startTime = this.physicalLayer.sim.time();
        ev.endTime = ev.startTime;
        this.physicalLayer.sq.insertEvents(ev);
    }

    public void receive() {

    }
}