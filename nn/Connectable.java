package nn;

import java.util.ArrayList;

public interface Connectable {
	public void addConnection(Connection connection);
	public ArrayList<Connection> getConnections();
	public void addInputConnection(Connection connection);
	public ArrayList<Connection> getInputConnections();
	public boolean hasInputConnections();
	public double activate(double[] arr);
	public double getLastOutput();
}
