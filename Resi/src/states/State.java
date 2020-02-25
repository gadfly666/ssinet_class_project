package states;

import elements.Element;

public abstract class State {
	public void act() {}
	public void getNextState(Element e) {}
}
