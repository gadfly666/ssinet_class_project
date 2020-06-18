package network.layers;

import config.Constant;
import elements.EntranceBuffer;
import elements.ExitBuffer;
import events.Event;
import events.MovingInSwitchEvent;
import network.Node;
import network.Packet;

import java.util.Optional;

public class NetworkLayer {

    public Node node;

    public NetworkLayer(Node node) {
        this.node = node;
    }

    public void controlFlow() {

    }

    public void controlFlow(ExitBuffer exitBuffer) {
		if (!(exitBuffer.isRequestListEmpty())) {
			int selectedId = Integer.MAX_VALUE;
			EntranceBuffer selectedENB  = null;
			Packet p;
			// lay ra cac enb tu request list cua exb hien tai
			for (EntranceBuffer enb : exitBuffer.getRequestList()) {
				p = enb.allPackets[0];
				if (p != null && !(enb.hasEventOfPacket(p))) {
					if (p.id < selectedId) {
						selectedId = p.id;
						selectedENB = enb;
					}
				}
			}
			if(selectedENB != null) {
				long time =this.node.physicalLayer.sim.time();
				Event event = new MovingInSwitchEvent(time, time + Constant.SWITCH_CYCLE,
						selectedENB, exitBuffer, selectedENB.popTopPacket());
				selectedENB.insertEvents(event);
//				exitBuffer.removeFromRequestList(selectedENB);//chen them su kien moi vao
			}
		}
    }

    public void route(EntranceBuffer entranceBuffer) {
		Optional.ofNullable(entranceBuffer.allPackets[0])
				.ifPresent(
						p -> {
							int nextNodeId = this.node.dataLinkLayer.getNextNode(p);
							this.node.physicalLayer.findExbByNodeId(nextNodeId).ifPresent(
									exitBuffer -> {
										exitBuffer.addToRequestList(entranceBuffer);
										controlFlow(exitBuffer);
									}
							);
						}
				);
    }
}
