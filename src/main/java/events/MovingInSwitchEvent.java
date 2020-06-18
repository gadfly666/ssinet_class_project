package events;

import elements.EntranceBuffer;
import elements.ExitBuffer;
import network.Packet;
import states.packet.StateP5;

import java.util.Objects;
import java.util.Optional;

enum TypeE {
    E, E1, E2
}

public class MovingInSwitchEvent extends Event {
    public TypeE type = TypeE.E;
    //Event dai dien cho su kien loai (E): goi tin roi khoi ENB cua Switch de sang EXB

    public Packet p;
    public EntranceBuffer entranceBuffer;
    public ExitBuffer exitBuffer;

    public MovingInSwitchEvent(long startTime, long endTime, EntranceBuffer entranceBuffer, ExitBuffer exitBuffer, Packet p) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.entranceBuffer = entranceBuffer;
        this.exitBuffer = exitBuffer;
        this.p = p;
    }

    @Override
    public void execute() {
        entranceBuffer.removeExecutedEvent(this);
        if(Objects.nonNull(p)){
            p.state = new StateP5(entranceBuffer, exitBuffer, p);
            p.state.act();
        }
        entranceBuffer.getNextState();
    }
}
