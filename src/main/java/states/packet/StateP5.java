package states.packet;

import elements.EntranceBuffer;
import elements.ExitBuffer;
import events.Event;
import events.LeavingEXBEvent;
import network.Packet;
import states.State;

import java.util.Optional;

public class StateP5 extends State {
	//�	State P5: the packet is located at EXB of switch.
    private EntranceBuffer entranceBuffer;
    private ExitBuffer exitBuffer;
    private Packet p;

    public StateP5(EntranceBuffer entranceBuffer, ExitBuffer exitBuffer, Packet p) {
        this.entranceBuffer = entranceBuffer;
        this.exitBuffer = exitBuffer;
        this.p = p;
    }

    public void act() {
        Long time = entranceBuffer.aSwitch.physicalLayer.sim.time();
        if (exitBuffer.insertPacket(p)) {
            Optional.ofNullable(entranceBuffer.aSwitch.physicalLayer.links.get(exitBuffer.nodeId))
                    .ifPresent(
                            link -> {
                                Event event = new LeavingEXBEvent(link, exitBuffer);
                                event.startTime = time;
                                event.endTime = time;
                                exitBuffer.insertEvents(event);
                            }
                    );
        }
    }
}
