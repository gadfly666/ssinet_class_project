package weightedloadexperiment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import common.Knuth;
import common.StdOut;
import config.Constant;
import custom.fattree.FatTreeGraph;
import custom.fattree.FatTreeRoutingAlgorithm;
import network.Topology;
import network.host.Host;
import simulator.DiscreteEventSimulator;;

public class ThroughputExperiment {
	private Topology network;
	
	public ThroughputExperiment(Topology network) {
        this.network = network;
    }

    public double[][] calThroughput(Map<Integer, Integer> trafficPattern, boolean verbose) {
        DiscreteEventSimulator simulator =
                new DiscreteEventSimulator(true, Constant.MAX_TIME, verbose);
        network.clear(); // clear all the data, queue, ... in switches, hosts
        network.setSimulator(simulator);
        
        int count = 0;
        for (Integer source : trafficPattern.keySet()) {
            Integer destination = trafficPattern.get(source);
            count++;
            network.getHostById(source).generatePacket(destination);
        }
        simulator.start();

        double interval = 1e7;
        int nPoint = (int) (simulator.getTimeLimit() / interval + 1);
        double[][] points = new double[2][nPoint];
        for (int i = 0; i < nPoint; i++) {
            // convert to ms
            points[0][i] = i * interval;
            points[1][i] = simulator.receivedPacketPerUnit[i];
        }

        double throughput = 0;
        List<Double> scores = new ArrayList<Double>();
        for (int i = 0; i < nPoint; i++) {
            points[1][i] = 100 * points[1][i] * Constant.PACKET_SIZE /
                    (trafficPattern.size() * Constant.LINK_BANDWIDTH * interval / 1e9);
        }
        for (int i = 0; i < nPoint; i++) {
            scores.add(points[1][i]);
        }
        throughput = points[1][nPoint - 1];

        StdOut.printf("Throughput : %.2f\n", throughput);

        double rawThroughput = throughput * Constant.LINK_BANDWIDTH / 100 / 1e9;
        //StdOut.printf("RAW Throughput : %.2f GBit/s\n", rawThroughput);

        double alternativeRawThroughput = simulator.numReceived * Constant.PACKET_SIZE / (trafficPattern.size());
        //StdOut.printf("b1: %f\n", alternativeRawThroughput);
        alternativeRawThroughput = alternativeRawThroughput / (nPoint * interval);
        //StdOut.printf("#Flows: %d, Time Limit = %f %f\n", trafficPattern.size(), simulator.getTimeLimit(), nPoint*interval);
        //StdOut.printf("Alternative Raw throughput = %f Gb/s", alternativeRawThroughput);


        //XYLineChart chart = new XYLineChart(null, "Time (ns)", "Throughput (%)", points);
        //chart.setAutoRange00(true, true);      // Axes pass through (0,0)
        //chart.toLatexFile("./src/results/chart.tex", 12, 8);
        //chart.view(800, 500);

        GraphPanel.createAndShowGui(scores);

        // Export to filfp
        try {
            String fileName = "./src/results/throughput.txt";
            File file = new File(fileName);
            // creates the file
            file.createNewFile();

            FileWriter writer = new FileWriter(file);

            // Writes the content to the file
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return points;

    }
    
    
    public static void main(String[] args) {

        //for(int timeOfRun = 0; timeOfRun < 100-3; timeOfRun++)
        {
            FatTreeGraph G = new FatTreeGraph(4);
            FatTreeRoutingAlgorithm ra = new FatTreeRoutingAlgorithm(G, false);
        
        
            Topology network = new Topology(G, ra);
            
            ThroughputExperiment experiment = new ThroughputExperiment(network);
            Integer[] hosts = G.hosts().toArray(new Integer[0]);
            
            Knuth.shuffle(hosts);
            List<Integer> sources = new ArrayList<>();
            List<Integer> destination = new ArrayList<>();
            sources.addAll(Arrays.asList(hosts).subList(0, hosts.length / 2));


            destination.addAll(Arrays.asList(hosts).subList(hosts.length / 2, hosts.length));

            Map<Integer, Integer> traffic = new HashMap<>();
            int sizeOfFlow = //1;
                                sources.size();
            for (int i = 0; i < sizeOfFlow; i++) {
                traffic.put(sources.get(i), destination.get(i));
                //traffic.put(destination.get(i), sources.get(i));
                //StdOut.printf("From source: %d To destination: %d\n", sources.get(i), destination.get(i));
            }

            experiment.calThroughput(traffic, false);
        }
    }

}
