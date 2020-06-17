package simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import common.StdOut;
import config.Constant;
import elements.Way;
import events.Event;
import network.Link;
import network.Switch;
import network.Topology;
import network.host.Host;

public class DiscreteEventSimulator extends Simulator {
    public int numReceived = 0;
    public long receivedPacketPerUnit[];
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
        this.receivedPacketPerUnit = new long[(int) (timeLimit / Constant.EXPERIMENT_INTERVAL + 1)];
    }

    public double getTime() {
        return currentTime;
    }

    public double getTimeLimit() {
        return timeLimit;
    }

    public void start() {
        while (currentTime <= timeLimit) {
            long startTime = System.currentTimeMillis();
            int lastPercentage = 0;
            currentEvents = new ArrayList<Event>();
            //Loc ra tat ca cac event sap ket thuc o cac thiet bi
            addCurrentEventsFromDevices(currentTime);
            //
            for (Event e : currentEvents) {
                e.execute();
            }
            currentTime = selectNextCurrentTime(currentTime);
            int percentage = (int) currentTime / (int) Constant.EXPERIMENT_INTERVAL;
            if (percentage > lastPercentage) {
                lastPercentage = percentage;
                StdOut.printProgress("Progress", startTime, (long) timeLimit, currentTime);
            }
        }
        StdOut.print("\r");
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void log(String message) {
        if (this.verbose) {
            StdOut.printf("At %d: %s\n", (long) this.getTime(), message);
        }
    }


    public void selectCurrentEvents() {
        //if()
    }

    public void addCurrentEventsFromDevices(long currentTime) {
        //ArrayList<Event> allEvents = new ArrayList<Event>();
        List<Host> allHosts = this.network.getHosts();
        for (Host host : allHosts) {
            //soonestEndTime will be updated later as events are executed
            if (host.physicalLayer.sq.soonestEndTime == currentTime) {
                addCurrentEvetsFromList(host.physicalLayer.sq.allEvents);
            }
            //soonestEndTime will be updated later as events are executed
            if (host.physicalLayer.EXBs[0].soonestEndTime == currentTime) {
                addCurrentEvetsFromList(host.physicalLayer.EXBs[0].allEvents);//add events of EXB of hosts
            }

            Link link = host.physicalLayer.links.get(host.id);
            if (link.ways.get(link.ways.get(host.id).from.id).soonestEndTime == currentTime) {
                addCurrentEvetsFromList(link.ways.get(link.ways.get(host.id).from.id).allEvents);//add events of EXB of hosts
            }
        }

        //Temporarily set comment, will be set to normal later
        List<Switch> allSwitches = this.network.getSwitches();
        for (Switch aSwitch : allSwitches) {
            for (int i = 0; i < aSwitch.physicalLayer.EXBs.length; i++) {
                for (Link link : aSwitch.physicalLayer.links.values()) {
                    Way way = link.ways.get(link.ways.get(aSwitch.id).from.id);
                    if (way.soonestEndTime == currentTime) {
                        addCurrentEvetsFromList(link.ways.get(link.ways.get(aSwitch.id).from.id).allEvents);
                    }
                }
                //soonestEndTime will be updated later as events are executed
                if (aSwitch.physicalLayer.ENBs[i].soonestEndTime == currentTime) {
                    addCurrentEvetsFromList(aSwitch.physicalLayer.ENBs[i].allEvents);
                }
                //soonestEndTime will be updated later as events are executed
                if (aSwitch.physicalLayer.EXBs[i].soonestEndTime == currentTime) {
                    addCurrentEvetsFromList(aSwitch.physicalLayer.EXBs[i].allEvents);//add events of EXB of hosts
                }

            }
        }

    }

    public void addCurrentEvetsFromList(ArrayList<Event> allEvents) {
        allEvents.stream()
                .filter(e -> e.endTime == this.currentTime)
                .map(e -> this.currentEvents.add(e))
                .collect(Collectors.toList());
    }


    public long selectNextCurrentTime(long currentTime) {
        long result = Long.MAX_VALUE;
        List<Host> allHosts = this.network.getHosts();
        for (Host host : allHosts) {
            if (host.physicalLayer.sq.soonestEndTime >= currentTime && host.physicalLayer.sq.soonestEndTime < result) {
                result = host.physicalLayer.sq.soonestEndTime;
            }

            if (host.physicalLayer.EXBs[0].soonestEndTime >= currentTime && host.physicalLayer.EXBs[0].soonestEndTime < result) {
                result = host.physicalLayer.EXBs[0].soonestEndTime;
            }

            Link link = host.physicalLayer.links.get(host.id);
            if (link.ways.get(link.ways.get(host.id).from.id).soonestEndTime >= currentTime
                    && link.ways.get(link.ways.get(host.id).from.id).soonestEndTime < result) {
                result = link.ways.get(link.ways.get(host.id).from.id).soonestEndTime;
            }
        }

        List<Switch> allSwitches = this.network.getSwitches();
        for (Switch aSwitch : allSwitches) {
            for (int i = 0; i < aSwitch.physicalLayer.EXBs.length; i++) {
                //soonestEndTime will be updated later as events are executed
                if (aSwitch.physicalLayer.EXBs[i].soonestEndTime >= currentTime && aSwitch.physicalLayer.EXBs[i].soonestEndTime < result) {
                    result = aSwitch.physicalLayer.EXBs[i].soonestEndTime;
                }

                //soonestEndTime will be updated later as events are executed
                if (aSwitch.physicalLayer.ENBs[i].soonestEndTime >= currentTime && aSwitch.physicalLayer.ENBs[i].soonestEndTime < result) {
                    result = aSwitch.physicalLayer.ENBs[i].soonestEndTime;
                }

                for (Link link : aSwitch.physicalLayer.links.values()) {

                    if (link.ways.get(link.ways.get(aSwitch.id).from.id).soonestEndTime >= currentTime && link.ways.get(link.ways.get(aSwitch.id).from.id).soonestEndTime < result) {
                        result = link.ways.get(aSwitch.id).soonestEndTime;
                    }
                }
            }
        }
        return result;
    }

}
