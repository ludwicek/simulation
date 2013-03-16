package nn;

import java.util.ArrayList;

public class Layer {
	private int number;
	ArrayList<Connectable> connectables = new ArrayList<Connectable>();
	public Layer(){
	}
	public ArrayList<Connectable> getConnectables() {
		return this.connectables;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNumber() {
		return number;
	}
}
