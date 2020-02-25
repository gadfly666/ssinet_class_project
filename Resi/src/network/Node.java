package network;


import network.layers.*;
/**
 * Created by Dandoh on 6/27/17.
 */
public abstract class Node extends Device {
	
	public NetworkLayer networkLayer;
	public PhysicalLayer physicalLayer;
	public DataLinkLayer dataLinkLayer;
	
    public Node(int id) {
        super(id);
    }
}
