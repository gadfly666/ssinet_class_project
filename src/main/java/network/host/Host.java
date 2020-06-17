package network.host;

import config.Constant;
import events.Event;
import events.GenerationEvent;
import network.Link;
import network.Node;
import network.Packet;
import network.layers.PhysicalLayer;
import simulator.DiscreteEventSimulator;


/**
 * Created by Dandoh on 6/27/17.
 */
public class Host extends Node {

    protected int receivedPacketInNode;
    protected double lastRx = 0; // thoi gian goi tin cuoi cung den host
    protected double firstTx = -1; //la thoi gian goi tin dau tien den host

    public Host(int id) {
        super(id);
        this.physicalLayer = new PhysicalLayer(this);
    }

    public Host link(Link link) {
        this.physicalLayer.links.put(this.id, link);
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

    public void receive(Packet p) {
        double currentTime = ((DiscreteEventSimulator)this.physicalLayer.sim).getTime();
        ((DiscreteEventSimulator)this.physicalLayer.sim).numReceived++;
//        if(this.receivedPacketInNode == 0) {
////            this.firstTx = packet.getStartTime();
//            this.firstTx = currentTime;
//            //System.out.println("Thoi gian goi tin dau tien den voi host " + self.id + " la: " + this.firstTx);
//        }
        this.receivedPacketInNode ++;
//        this.lastRx = currentTime;
        ((DiscreteEventSimulator)this.physicalLayer.sim).receivedPacketPerUnit[(int)(currentTime / Constant.EXPERIMENT_INTERVAL + 1)]++;
        p.setEndTime(currentTime);

        ((DiscreteEventSimulator)this.physicalLayer.sim).totalPacketTime += p.timeTravel();
        ((DiscreteEventSimulator)this.physicalLayer.sim).totalHop += p.nHop;
    }

    public int getReceivedPacketInNode() {
        return receivedPacketInNode;
    }

    public double getLastRx() {
        return lastRx;
    }

    public double getFirstTx() {
        return firstTx;
    }
}