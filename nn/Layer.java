package nn;

import java.io.Serializable;
import java.util.ArrayList;

public class Layer implements Serializable {
	private static final long serialVersionUID = 2994410752699612541L;
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
