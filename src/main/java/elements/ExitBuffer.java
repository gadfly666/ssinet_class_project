package elements;

import common.Queue;
import network.Link;
import network.Node;
import states.exb.X00;
import states.exb.X01;
import states.exb.X10;
import states.exb.X11;
import states.unidirectionalway.W0;

import java.util.ArrayList;

public class ExitBuffer extends LimitedBuffer {
    public int nodeId;
    public Link link;
    protected ArrayList<EntranceBuffer> requestList;

    public ExitBuffer() {
        state = new X01();
        this.requestList = new ArrayList<>();
    }

    public void addToRequestList(EntranceBuffer entranceBuffer){
        requestList.add(entranceBuffer);
    }

    public ArrayList<EntranceBuffer> getRequestList() {
        return requestList;
    }

    public void removeFromRequestList(EntranceBuffer entranceBuffer){
        if(requestList.contains(entranceBuffer)){
            requestList.remove(entranceBuffer);
        }else
            System.out.println("ERROR: ExitBuffer: " + this.toString() + " does not contain request id: " + id);
    }
    public boolean isRequestListEmpty(){
        return requestList.isEmpty();
    }

    public void getNextState() {
        Way way = link.ways.get(nodeId);
        if(this.isNotFull() && way.state instanceof W0) {
            state = new X00();
        } else if (this.isNotFull() && !(way.state instanceof W0)){
            state = new X01();
        } else if (!this.isNotFull() && way.state instanceof W0) {
            state = new X10();
        } else if(!this.isNotFull() && !(way.state instanceof W0)) {
            state = new X11();
        }
    }
}
