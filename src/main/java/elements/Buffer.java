package elements;

import java.util.ArrayList;

import events.Event;
import network.layers.PhysicalLayer;

public abstract class Buffer extends Element {
	public ArrayList<Event> allEvents = new ArrayList<Event>();
	
	public PhysicalLayer phyLayer;
	
	/**
	 * Xay dung phuong thuc insertEvent thuc hien viec
	 * chen mot Event co ten la ev.
	 * @param ev
	 */
	public void insertEvents(Event ev)
	{
		long endTime = ev.endTime;
		int i;
		if(allEvents == null)
		{
			allEvents = new ArrayList<>();
			allEvents.add(ev);
			return;
		}
		if(allEvents.size() == 0)
		{
			allEvents.add(ev);
			updateSoonestEndTime();
			return;
		}
		for(i = 0; i < allEvents.size(); i++ )
		{
			if(allEvents.get(i).endTime > endTime)
			{
				break;
			}
		}
		allEvents.add(i, ev);
		updateSoonestEndTime();
	}
	
	@Override
	
	public void updateSoonestEndTime()
	{
		if(allEvents == null)
		{
			soonestEndTime = Long.MAX_VALUE;
			return;
		}
		if(allEvents.size() == 0)
		{
			soonestEndTime = Long.MAX_VALUE;
			return;
		}
		soonestEndTime = allEvents.get(0).endTime;
		System.out.println("Eveent " + allEvents.get(0).getClass() + " SoonestTime: " + soonestEndTime);
	}
	
	@Override
	public void removeExecutedEvent(Event ev)
	{
		this.allEvents.remove(ev);
	}
}
