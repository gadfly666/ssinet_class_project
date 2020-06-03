package states.packet;

import elements.Element;
import elements.ExitBuffer;
import events.LeavingEXBEvent;
import network.Link;
import network.Node;
import network.Packet;
import states.State;

public class StateP3 extends State {
    //�	State P3: the packet is moved in a unidirectional way.
    public Element element;

    public StateP3(Element element) {
        this.element = element;
    }

    @Override
    public void act(LeavingEXBEvent ev) {
        //get connected Link
        Link link = ((ExitBuffer)element).link;
        Packet p = ((ExitBuffer)element).allPackets[0];

//        Node nextNode = (Node)link.connectedNode.get(p.getDestination());
//        nextNode.physicalLayer.
    }
}
