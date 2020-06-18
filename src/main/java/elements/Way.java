package elements;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import config.Constant;
import events.Event;
import events.ReachingENBEvent;
import network.Link;
import network.Node;
import network.Packet;
import network.Switch;
import network.host.Host;
import states.enb.N0;
import states.packet.StateP6;
import states.unidirectionalway.W0;
import states.unidirectionalway.W1;
import states.unidirectionalway.W2;

import java.util.Objects;

public class Way extends Element {
    public Node from;
    public Node to;
    public Link link;
    public Packet packet;

    public Way(Node from, Node to, Link link) {
        this.from = from;
        this.to = to;
        this.link = link;
        this.state = new W0();
        from.physicalLayer.notConnectedExitBuffer().ifPresent(
                exitBuffer -> {
                    exitBuffer.nodeId = to.id;
                    exitBuffer.link = link;
                }
        );
        to.physicalLayer.notConnectedEntranceBuffer().ifPresent(
                entranceBuffer -> {
                    entranceBuffer.nodeId = from.id;
                }
        );
    }

    public void transferPacket() {
        if (Objects.nonNull(packet)) {
            if (to instanceof Switch) {
                to.physicalLayer.findByNodeId(from.id).ifPresent(
                        (entranceBuffer) -> {
                            entranceBuffer.way = this;
                            entranceBuffer.state.act();
                        }
                );
            }
            if (to instanceof Host) {
                packet.state = new StateP6();
                packet.state.act();
                Host host = (Host) to;
                host.receive(packet);
                this.packet = null;
                this.getNextState();
            }
        }
    }

    public void getNextState() {
        if (!Objects.isNull(packet)) {
            state = new W1(this);
        }
        if (Objects.isNull(packet)) {
            to.physicalLayer.findByNodeId(from.id).ifPresent(
                    entranceBuffer -> {
                        if (entranceBuffer.indexOfEmptySlot() < Constant.QUEUE_SIZE) {
                            state = new W0();
                        } else {
                            state = new W2();
                        }
                    }
            );
        }
    }

    public void insertPacket(Packet p) {
        if(Objects.isNull(packet)) {
            packet = p;
        }
    }

}
