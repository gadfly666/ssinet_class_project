package network.host;

import config.Constant;
import network.Packet;
import elements.Buffer;
import states.State;

import java.util.*;

import states.sourcequeue.*;

public class SourceQueue extends Buffer {
    private int sourceId;
    private int destinationId;
    private long generatedPacketCount;


    public ArrayList<Packet> allPackets = new ArrayList<Packet>();

    public SourceQueue(int sourceId) {
        this.sourceId = sourceId;
        this.destinationId = sourceId;
        this.generatedPacketCount = -1;
        state = new Sq1(this.phyLayer, this);
    }

    public SourceQueue(int sourceId, int destinationId) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.generatedPacketCount = -1;
        state = new Sq1(this.phyLayer, this);
    }

    public void setDestionationID(int destionationID) {
        this.destinationId = destionationID;
    }

    public int getDestionationID() {
        return this.destinationId;
    }

    public Packet generateNewPacket(long currentTime) {
        if (this.isDelayed(currentTime)) return null;

        generatedPacketCount++;
        double timeSent = generatedPacketCount * Constant.HOST_DELAY;
        Packet p = new Packet(0, sourceId, destinationId, timeSent);
        allPackets.add(p);
        return p;
    }

    public boolean isDelayed(long currentTime) {
        long r = currentTime / Constant.HOST_DELAY;
        return r <= generatedPacketCount;
    }

    public double getNextPacketTime() {
        return (double) (generatedPacketCount + 1) * Constant.HOST_DELAY;
    }

    public int getSourceId() {
        return this.sourceId;
    }

    public void getNextState() {
        if (allPackets.isEmpty()) {
            state = new Sq1(this.phyLayer, this);
        } else {
            state = new Sq2(this.phyLayer, this);
        }
    }

}
