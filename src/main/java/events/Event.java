package events;

import elements.Element;
import network.Packet;

public abstract class Event {
	public Packet packet; //packet ID
	public long startTime;
	public long endTime;
	
	public Element elem;
	
	public void execute()
	{}
}
