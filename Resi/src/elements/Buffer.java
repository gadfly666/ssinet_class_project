package elements;

import java.util.ArrayList;

import events.Event;

public class Buffer extends Element {
	public ArrayList<Event> allEvents = new ArrayList<Event>();
	
	public void insertEvents(Event ev)
	{
		long endTime = ev.endTime;
		int i;
		if(allEvents == null)
		{
			allEvents = new ArrayList<Event>();
			allEvents.add(ev);
			return;
		}
		boolean found = false;
		for(i = 0; i < allEvents.size() && !found; i++ )
		{
			if(allEvents.get(i).endTime > endTime)
			{
				found = true;
				break;
			}
		}
		allEvents.add(i, ev);
		
	}
}
