package elements;

import common.Queue;
import network.Link;
import states.exb.X01;

public class ExitBuffer extends LimitedBuffer {
    public int nodeId;
    public Link link;
    public Queue<EntranceBuffer> requestEnbQueue;

    public ExitBuffer() {
        state = new X01();
        requestEnbQueue = new Queue<>();
    }
}
