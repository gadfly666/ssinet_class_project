package network.layers;

import config.Constant;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import events.Event;
import events.MovingInSwitchEvent;
import network.Node;

import java.util.Optional;

public class NetworkLayer {

    public Node node;

    public NetworkLayer(Node node) {
        this.node = node;
    }

    public void controlFlow() {

    }

    public void controlFlow(ExitBuffer exitBuffer) {
		long time = this.node.physicalLayer.sim.time();
        EntranceBuffer enb = exitBuffer.requestEnbQueue.dequeue();
        if(enb != null){
			Event event = new MovingInSwitchEvent(time, time + Constant.SWITCH_CYCLE,
					enb, exitBuffer);
			enb.insertEvents(event); //chen them su kien moi vao
		}
    }

    public void route(EntranceBuffer entranceBuffer) {
		Optional.ofNullable(entranceBuffer.allPackets[0])
				.ifPresent(
						p -> {
							int nextNodeId = this.node.dataLinkLayer.getNextNode(p);
							this.node.physicalLayer.findExbByNodeId(nextNodeId).ifPresent(
									exitBuffer -> {
										exitBuffer.requestEnbQueue.enqueue(entranceBuffer);
										controlFlow(exitBuffer);
									}
							);
						}
				);
    }
}
