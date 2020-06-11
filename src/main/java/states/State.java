package states;

import elements.Element;
import events.*;

public abstract class State {
    public Event ancestorEvent;
    public Element elem;

    public void act() {
    }

    public void getNextState(Element e) {
    }


}
