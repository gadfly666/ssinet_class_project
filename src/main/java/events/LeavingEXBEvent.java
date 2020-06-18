package events;

import config.Constant;
import elements.Element;
import elements.ExitBuffer;
import elements.Way;
import network.*;
import network.host.Host;
import network.host.SourceQueue;
import network.layers.PhysicalLayer;
import states.packet.StateP3;
import states.unidirectionalway.W0;

import java.util.Objects;

public class LeavingEXBEvent extends Event {
    //Event dai dien cho su kien loai (C): goi tin roi khoi EXB

    private Link link;
    private ExitBuffer exb;

    public LeavingEXBEvent(Link link, ExitBuffer exb) {
        this.link = link;
        this.exb = exb;
    }

    @Override
    public void execute() {
        exb.removeExecutedEvent(this);//go bo su kien nay ra khoi danh sach cac su kien

        Packet p = exb.allPackets[0];
//        Node currentNode = link.ways.get(exb.nodeId).from;
        Way way =link.ways.get(exb.nodeId);

        if(Objects.nonNull(p)){
            way.insertPacket(p);
            way.getNextState();
            way.state.act();
        }
        //vong lap for thuc hien viec dich chuyen cac goi tin len truoc
        for (int i = 0; i < Constant.QUEUE_SIZE - 1; i++) {
            exb.allPackets[i] = exb.allPackets[i + 1];
        }
        exb.allPackets[Constant.QUEUE_SIZE - 1] = null;
        exb.getNextState();
    }
}
