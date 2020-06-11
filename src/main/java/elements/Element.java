package elements;

import events.Event;
import network.layers.PhysicalLayer;
import simulator.DiscreteEventSimulator;
import states.State;

import java.util.ArrayList;

public abstract class Element {
	public int id;
	public State state;
	public long soonestEndTime = Long.MAX_VALUE;

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
		System.out.println("added Eveent " + allEvents.get(0).getClass() + " SoonestTime: " + soonestEndTime);
	}

	public void removeExecutedEvent(Event ev)
	{
		this.allEvents.remove(ev);
//		int index = allEvents.indexOf(ev);
//		for(int i = index; i < allEvents.size() -1 ; i++)
//		{
//			allEvents.set(i, allEvents.get(i+1));
//		}
//		allEvents.remove(allEvents.size() -1);
		if (allEvents.isEmpty())
			this.soonestEndTime = Long.MAX_VALUE;
//		else this.soonestEndTime = allEvents.get(0).endTime;
	}
	
	public void getNextState()
	{
		
	}

}
