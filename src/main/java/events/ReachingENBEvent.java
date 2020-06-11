package events;

import elements.EntranceBuffer;
import network.Packet;
import states.packet.StateP4;

enum TypeD {
    D, D1, D2
}

public class ReachingENBEvent extends Event {
    public TypeD type = TypeD.D;
    //Event dai dien cho su kien loai (D): goi tin den duoc ENB cua nut tiep theo

    private Packet p;
    private EntranceBuffer entranceBuffer;

    public ReachingENBEvent(Packet p, EntranceBuffer entranceBuffer) {
        this.p = p;
        this.entranceBuffer = entranceBuffer;
    }

    @Override
    public void execute() {
        entranceBuffer.way.removeExecutedEvent(this);
        System.out.println("REACH ENB" + p.id);
        p.state = new StateP4(entranceBuffer);
        p.state.act();
    }
}
