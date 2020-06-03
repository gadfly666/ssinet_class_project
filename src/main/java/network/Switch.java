package network;

import config.Constant;
import elements.EntranceBuffer;
import network.host.Host;
import network.layers.DataLinkLayer;
import network.layers.NetworkLayer;
import network.layers.PhysicalLayer;
import routing.RoutingAlgorithm;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dandoh on 6/27/17.
 */
public class Switch extends Node {
	public List<Node> connectedNodes;
	public RoutingAlgorithm ra;
	public int numPorts = 0;

	public Switch(int id, RoutingAlgorithm ra)
	{
		super(id);
		this.physicalLayer = new PhysicalLayer(this);
		this.networkLayer = new NetworkLayer(this);
		this.dataLinkLayer = new DataLinkLayer(this, ra);
		connectedNodes = new ArrayList<>();
	}

}