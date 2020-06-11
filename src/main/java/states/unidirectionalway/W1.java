package states.unidirectionalway;

import elements.Way;
import states.State;
import states.packet.StateP3;

public class W1 extends State {
	//�	State W1: the way has a packet.
    private Way way;

    public W1(Way way) {
        this.way = way;
    }

    public void act(){
        way.packet.state = new StateP3(way);
        way.packet.state.act();
    }
}
