package nn;

import java.io.Serializable;
import java.util.ArrayList;

public class Input implements Connectable, Serializable {
	private static final long serialVersionUID = -3977364445464939468L;
	private ArrayList<Connection> connections = new ArrayList<Connection>(); 
	public Input() {
		
	}
	public void addConnection(Connection connection) {
		connections.add(connection);
	}
	public ArrayList<Connection> getConnections() {
		return connections;
	}
	public void addInputConnection(Connection connection) {
		
	}
	public ArrayList<Connection> getInputConnections() {
		return null;
	}
	public boolean hasInputConnections() {
		return false;
	}
	public double activate(double[] arr) {
		return 0;
	}
	public double getLastOutput() {
		return 0;
	}
}
