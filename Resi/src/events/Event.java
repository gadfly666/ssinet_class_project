package events;

import elements.*;

public abstract class Event {
	public int pid; //packet ID
	public int startTime;
	public int endTime;
	
	public void execute(Element elem)
	{}
}
