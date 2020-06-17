package states.enb;

import elements.EntranceBuffer;
import events.Event;
import events.ReachingENBEvent;
import states.State;

public class N0 extends State {
    //�	State N0: ENB is not full.
    private EntranceBuffer entranceBuffer;

    public N0(EntranceBuffer entranceBuffer) {
        this.entranceBuffer = entranceBuffer;
    }

    public void act() {
        if(entranceBuffer.insertPacket(entranceBuffer.way.packet)) {
            Event e = new ReachingENBEvent(entranceBuffer.way.packet, entranceBuffer);
            e.startTime = entranceBuffer.way.to.physicalLayer.sim.time();
            e.endTime = entranceBuffer.way.to.physicalLayer.sim.time()
                    + entranceBuffer.way.link.propagationLatency()
                    + entranceBuffer.way.link.serialLatency(entranceBuffer.way.packet.getSize());
            entranceBuffer.way.insertEvents(e);
            entranceBuffer.way.packet.nHop ++;
            entranceBuffer.way.packet = null;
            entranceBuffer.way.getNextState();
            entranceBuffer.getNextState();
        }
    }
}
