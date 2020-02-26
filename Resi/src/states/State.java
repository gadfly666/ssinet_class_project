package states;

import elements.Element;

public abstract class State {
	public Element elem;
	public void act() {}
	public void getNextState(Element e) {}
}
