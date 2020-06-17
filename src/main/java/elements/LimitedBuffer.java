package elements;

import config.Constant;
import events.Event;
import network.Packet;
import states.State;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class LimitedBuffer extends Buffer {
    public Packet[] allPackets;

    public int indexOfEmptySlot() {
        //index of emtpty slot == number of used slot cause allPackets is an array
        return Arrays.stream(allPackets)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()).size();
    }

    //init packet size
    public LimitedBuffer() {
        allPackets = new Packet[Constant.QUEUE_SIZE];
    }

    /**
     * Phuong thuc insertPacket se lam nhiem vu chen goi tin p
     * vao trong bo dem cua no
     *
     * @param p la goi tin can chen vao
     * @return true neu nhu chen duoc goi tin
     * false neu nhu KHONG chen duoc goi tin vao (tuc bo dem da day)
     */
    public boolean insertPacket(Packet p) {
        boolean inserted = false;
        int emptySlot = indexOfEmptySlot();
        if (emptySlot < Constant.QUEUE_SIZE) {
            allPackets[emptySlot] = p;
            inserted = true;
        }
        return inserted;
    }

    public boolean hasEventOfPacket(Packet packet)
    {
        if(allEvents == null) return false;
        else if(allEvents.isEmpty()) return false;
        else if(packet == null) return false;
        else{
            for(Event event : allEvents){
                if(event.packet == packet) return true;
            }
            return false;
        }
    }

    public Packet popTopPacket() {
        Packet p = allPackets[0];
        for (int i = 0; i < Constant.QUEUE_SIZE - 1; i++) {
            allPackets[i] = allPackets[i + 1];
        }
        //slot cuoi cung trong bo dem cua EXB phai la null (khong chua goi tin nao)
        allPackets[Constant.QUEUE_SIZE - 1] = null;
        return p;
    }

    public boolean isNotFull() {
        return this.indexOfEmptySlot() < Constant.QUEUE_SIZE;
    }
}
