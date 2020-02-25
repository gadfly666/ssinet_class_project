package elements;

import events.Event;
import states.State;

public abstract class Element {
	public int id;
	public Event event;
	public State state;
	
	public void getNextState()
	{
		
	}
}
