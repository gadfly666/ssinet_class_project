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

        link.transferPacket(exb.nodeId, p);

        //vong lap for thuc hien viec dich chuyen cac goi tin len truoc
        for (int i = 0; i < Constant.QUEUE_SIZE - 1; i++) {
            exb.allPackets[i] = exb.allPackets[i + 1];
        }
        //slot cuoi cung trong bo dem cua EXB phai la null (khong chua goi tin nao)
        exb.allPackets[Constant.QUEUE_SIZE - 1] = null;

//        Node from = link.ways.get(exb.nodeId).from;
//        if(from instanceof Host) {
//            ((Host)from).generatePacket(p.getDestination());
//        }
    }
}
