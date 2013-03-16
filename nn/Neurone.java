package nn;

import java.io.Serializable;
import java.util.ArrayList;
import utils.Arrayutil;

public class Neurone implements Connectable, Serializable {
	private static final long serialVersionUID = -2870674098937446558L;
	private ArrayList<Connection> connections = new ArrayList<Connection>(); 
	private ArrayList<Connection> inputConnections = new ArrayList<Connection>();
	private double lastOutput;
	public Neurone() {
		
	}
	public void addConnection(Connection connection) {
		connections.add(connection);
	}
	public void addInputConnection(Connection connection) {
		inputConnections.add(connection);
	}
	public ArrayList<Connection> getConnections() {
		return connections;
	}
	public ArrayList<Connection> getInputConnections() {
		return inputConnections;
	}
	public boolean hasInputConnections() {
		return true;
	}
	public double activate(double[] arr) {
		double ret = 1/(1+Math.exp(-Arrayutil.sumArray(arr)));
		setLastOutput(ret);
		return ret;
	}
	public void setLastOutput(double lastOutput) {
		this.lastOutput = lastOutput;
	}
	public double getLastOutput() {
		return lastOutput;
	}
}
