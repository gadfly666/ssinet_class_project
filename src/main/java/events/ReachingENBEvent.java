package events;

import elements.EntranceBuffer;
import network.Packet;

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
        entranceBuffer.removeExecutedEvent(this);
        System.out.println("REACH ENB" + p.id);

        if (entranceBuffer.insertPacket(p)) {
            entranceBuffer.aSwitch.networkLayer.route(entranceBuffer);
        }
    }
}
