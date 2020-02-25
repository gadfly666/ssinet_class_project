package factories;

import elements.Element;
import network.host.*;
import network.Packet;

public class FactoryPacket extends Factory {
	public void updateState(Packet p, Element e)
	{
		if(p.state == null && (e instanceof SourceQueue))
		{
			
		}
	}
}
