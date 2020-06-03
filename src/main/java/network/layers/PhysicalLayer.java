package network.layers;

import elements.EntranceBuffer;
import elements.ExitBuffer;
import network.Link;
import network.Switch;
import network.host.Host;
import network.host.SourceQueue;
import simulator.Simulator;

import java.util.*;
import java.util.stream.Collectors;

public class PhysicalLayer {
    public ExitBuffer[] EXBs;
    public EntranceBuffer[] ENBs;
    public SourceQueue sq;
    public Simulator sim;
    public List<Link> links;
//	public Device node;

    public PhysicalLayer(Host host) {
        ENBs = new EntranceBuffer[1];
        EXBs = new ExitBuffer[1];
        sq = new SourceQueue(host.id);
        EXBs[0] = new ExitBuffer();
        links = new ArrayList<>();
//		EXBs[0].phyLayer = this;
		sq.phyLayer = this;
//		this.node = host;
    }

    public PhysicalLayer(Switch sw) {
        ENBs = new EntranceBuffer[4];
        EXBs = new ExitBuffer[4];
        Arrays.fill(ENBs, new EntranceBuffer(sw));
        Arrays.fill(EXBs, new ExitBuffer());
        links = new ArrayList<>();
    }

    public Optional<Link> getByNextNodeId (int nodeId) {
        return links.stream().filter(link -> !Objects.isNull(link.ways.get(nodeId)))
                .findFirst();
    }

    public Optional<EntranceBuffer> notConnectedEntranceBuffer() {
        return Arrays.stream(ENBs).filter(e -> !Objects.isNull(e))
                .filter(e -> e.nodeId != -1).findFirst();
    }

    public Optional<ExitBuffer> notConnectedExitBuffer() {
        return Arrays.stream(EXBs)
                .filter(e -> !Objects.isNull(e))
                .filter(e -> e.nodeId != -1).findFirst();
    }

    public Optional<EntranceBuffer> findByNodeId(long nodeId){
        return Arrays.stream(ENBs).filter(e -> e.nodeId != nodeId).findFirst();
    }

    public Optional<ExitBuffer> findExbByNodeId(long nodeId) {
        return Arrays.stream(EXBs).filter(e -> e.nodeId != nodeId).findFirst();
    }

	/*public void addLocationOfEvents()
	{
		sim.addLocationOfEvents(node);
	}*/
}
