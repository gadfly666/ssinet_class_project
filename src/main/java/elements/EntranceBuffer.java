package elements;

import config.Constant;
import network.Switch;
import states.enb.N0;
import states.enb.N1;

public class EntranceBuffer extends LimitedBuffer {

    public long nodeId  = -1;
    public Switch aSwitch;
    public Way way;

    public EntranceBuffer(Switch aSwitch) {
        this.state = new N0(this);
        this.aSwitch = aSwitch;
        this.phyLayer = aSwitch.physicalLayer;
    }

    public void getNextState() {
        int emptySlot = indexOfEmptySlot();
        if (emptySlot < Constant.QUEUE_SIZE) {
            state = new N0(this);
        } else {
            state = new N1();
        }
    }
}
