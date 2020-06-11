package states.packet;

import elements.Element;
import elements.Way;
import states.State;

public class StateP3 extends State {
    //�	State P3: the packet is moved in a unidirectional way.
    public Element element;

    public StateP3(Element element) {
        this.element = element;
    }

    public void act() {
        Way way = (Way) element;
        way.transferPacket();
    }
}
