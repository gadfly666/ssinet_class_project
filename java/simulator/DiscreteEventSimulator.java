package simulator;

import common.StdOut;
import common.Tuple;
import config.Constant;
import umontreal.ssj.simevents.Simulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represent for simulator, inherit from Simulator of umontreal ssj library
 */
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

    public DiscreteEventSimulator() {
        super();
    }

    public DiscreteEventSimulator(boolean isLimit, double timeLimit, boolean verbose) {
        super();
        this.isLimit = isLimit;
        this.verbose = verbose;
        this.timeLimit = timeLimit;
        this.receivedPacket = new long[(int)(timeLimit/ Constant.EXPERIMENT_INTERVAL +1)];
    }

    @Override
    public void start () {
        if (eventList.isEmpty())
            throw new IllegalStateException ("start() called with an empty event list");
        stopped = false;
        simulating = true;
        umontreal.ssj.simevents.Event ev;
        try {
            long startTime = System.currentTimeMillis();
            int lastPercentage = 0;
            while ((ev = removeFirstEvent()) != null && !stopped
                    && (!isLimit || currentTime < timeLimit)) {
                ev.actions();
                int percentage = (int)currentTime/(int)Constant.EXPERIMENT_INTERVAL;
                if (percentage > lastPercentage) {
                    lastPercentage = percentage;
                    StdOut.printProgress("Progress", startTime, (long) timeLimit, (long) currentTime);
                }
            }
            StdOut.print("\r");
        }
        finally {
            stopped = true; simulating = false;
        }
    }
    public double getTime() {
        return currentTime;
    }

    public double getTimeLimit() {
        return timeLimit;
    }

    public void addEvent(Event e) {
        this.getEventList().add(e);
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void log(String message) {
        if (this.verbose) {
            StdOut.printf("At %d: %s\n", (long) this.getTime(), message);
        }
    }
}
