package states.packet;

import elements.EntranceBuffer;
import states.State;

public class StateP4 extends State{
	//�	State P4: the packet is located at ENB of switch.
    private EntranceBuffer entranceBuffer;

    public StateP4 (EntranceBuffer entranceBuffer) {
        this.entranceBuffer = entranceBuffer;
    }

    public void act(){
        entranceBuffer.aSwitch.networkLayer.route(entranceBuffer);
    }
}
