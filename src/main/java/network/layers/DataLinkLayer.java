package network.layers;

import network.Node;
import network.Packet;
import routing.RoutingAlgorithm;

public class DataLinkLayer {

    public RoutingAlgorithm ra;
    public Node node;

    public DataLinkLayer(Node node, RoutingAlgorithm ra) {
        this.ra = ra;
        this.node = node;
    }

    public void update(Packet p) {
    }

    public int getNextNode(Packet p) {
        return ra.next(p.getSource(), node.id, p.getDestination());
    }
}
