package simulator;

import java.util.ArrayList;
import java.util.List;

import common.StdOut;
import config.Constant;
import events.Event;
import network.Switch;
import network.Topology;
import network.host.Host;

public class DiscreteEventSimulator extends Simulator {
	public int numReceived = 0;
    public long receivedPacket[];
    public int numSent = 0;
    public int numLoss = 0;
    public long totalPacketTime = 0;
    public int numEvent = 0;
    private boolean isLimit;
    private double timeLimit;
    private boolean verbose;
    public long totalHop = 0;
    
    
    
    public DiscreteEventSimulator(boolean isLimit, double timeLimit, boolean verbose) {
        super();
        this.isLimit = isLimit;
        this.verbose = verbose;
        this.timeLimit = timeLimit;
        this.receivedPacket = new long[(int)(timeLimit/ Constant.EXPERIMENT_INTERVAL +1)];
    }
    
    public double getTime() {
        return currentTime;
    }

    public double getTimeLimit() {
        return timeLimit;
    }

    public void start () {
    	//while(currentTime <= timeLimit)
    	{
    		currentEvents = new ArrayList<Event>();
    		addCurrentEventsFromHost();
    		for(Event e : currentEvents)
    		{
    			e.execute();
    		}
    		
    	}
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void log(String message) {
        if (this.verbose) {
            StdOut.printf("At %d: %s\n", (long) this.getTime(), message);
        }
    }
    
    
    public void selectCurrentEvents()
    {
    	//if()
    }
    
    public void addCurrentEventsFromHost()
    {
    	//ArrayList<Event> allEvents = new ArrayList<Event>();
    	List<Host> allHosts = this.network.getHosts(); 
		for(Host host : allHosts)
		{
			//allEvents.addAll(host.physicalLayer.sq.allEvents);
			addCurrentEvetsFromList(host.physicalLayer.sq.allEvents);
			addCurrentEvetsFromList(host.physicalLayer.EXBs[0].allEvents);//add events of EXB of hosts
		}
		List<Switch> allSwitches = this.network.getSwitches();
		for(Switch aSwitch : allSwitches)
		{
			for(int i = 0; i < aSwitch.numPorts; i++)
			{
				addCurrentEvetsFromList(aSwitch.physicalLayer.ENBs[i].allEvents);
				addCurrentEvetsFromList(aSwitch.physicalLayer.EXBs[i].allEvents);//add events of EXB of hosts
			}
		}
		
		
    }
    
    public void addCurrentEvetsFromList(ArrayList<Event> allEvents)
    {
    	if(allEvents != null)
		{
			if(allEvents.size() > 0)
			{
				for(Event e: allEvents)
				{
					if(e.endTime == this.currentTime)
					{
						this.currentEvents.add(e);
					}
				}
			}
		}
    }
    
    
}
