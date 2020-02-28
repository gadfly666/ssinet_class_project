package network.layers;

import elements.*;
import network.host.*;

public class PhysicalLayer {
	public ExitBuffer[] EXBs;
	public EntranceBuffer[] ENBs;
	public SourceQueue sq;
	
	public PhysicalLayer(Host host)
	{
		ENBs = null;
		EXBs = new ExitBuffer[0];
		sq = new SourceQueue(host.id);
		EXBs[0] = new ExitBuffer();
		EXBs[0].phyLayer = this;
		sq.phyLayer = this;
	}
}
