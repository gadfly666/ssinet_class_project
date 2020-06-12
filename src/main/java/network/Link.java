package network;


import config.Constant;
import elements.EntranceBuffer;
import events.Event;
import events.ReachingENBEvent;
import network.host.Host;
import elements.Way;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Dandoh on 6/27/17.
 */
public class Link extends Device {

    public Map<Integer, Way> ways;
    private long bandwidth;
    private double length;

    public Link(Node u, Node v) {
        super(-1);
        ways = new HashMap<>();

        ways.put(v.id, new Way(u, v, this));
        ways.put(u.id, new Way(v, u, this));

        this.bandwidth = Constant.LINK_BANDWIDTH;
    }

    public Link(Node u, Node v, double length) {
        this(u, v);
        this.length = length;
        System.out.println("Link from: " + u.id + " to: " + v.id + " Length = " + length);
    }



    public long serialLatency(int packetSize) {
        if (packetSize != 100000 && this.bandwidth != 1e9)
            System.out.println(packetSize + " " + this.bandwidth);
        return (long) (1e9 * packetSize / this.bandwidth);
    }

    public long propagationLatency() {
        //if(length != Constant.DEFAULT_LINK_LENGTH && length != Constant.HOST_TO_SWITCH_LENGTH)
        //    System.out.println("!!!!!!!!!Length = " + length);
        return (long) (length / Constant.PROPAGATION_VELOCITY);
    }

    public long getTotalLatency(int packetSize) {
        return serialLatency(packetSize) + propagationLatency();
    }


    public double getLength() {
        return this.length;
    }
}