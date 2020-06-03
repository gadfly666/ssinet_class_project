package elements;

import network.*;

public class Way extends Element {
    public Node from;
    public Node to;
    public Link link;

    public Way(Node from, Node to) {
        this.from = from;
        this.to = to;
        from.physicalLayer.notConnectedExitBuffer().ifPresent(
                exitBuffer -> exitBuffer.nodeId = to.id
        );
        to.physicalLayer.notConnectedEntranceBuffer().ifPresent(
                entranceBuffer -> entranceBuffer.nodeId = from.id
        );
    }
}
