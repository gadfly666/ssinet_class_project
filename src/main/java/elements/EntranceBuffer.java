package elements;

import network.Switch;
import states.enb.N0;

public class EntranceBuffer extends LimitedBuffer {

    public long nodeId  = -1;
    public Switch aSwitch;

    public EntranceBuffer(Switch aSwitch) {
        this.state = new N0();
        this.aSwitch = aSwitch;
    }

}
