package nn;

import java.util.ArrayList;

public class NeuralNetwork {
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	
	private double[] lastOutputs = null;
	private double fitness;
	private int connectionNum;
	public NeuralNetwork(){
	}
	public void addLayer(Layer layer) {
		this.getLayers().add(layer);
	}
	public double[] feedForward(double[] inputs) {
		ArrayList<Connectable> cAbles;
		ArrayList<Connectable> cAblesBefore = null;
		ArrayList<Connection> conns;
		int i,j,m;
		double ret[] = new double[getLayers().get(getLayers().size()-1).getConnectables().size()];
		for (i = 1; i < getLayers().size(); i++) {
			cAbles = getLayers().get(i).getConnectables();
			if (i > 1) {
				cAblesBefore =  getLayers().get(i-1).getConnectables();
			}
			for (j = 0; j < cAbles.size(); j++) {
				conns = cAbles.get(j).getInputConnections();
				double tmp[] = new double[conns.size()];
				
				// prva vrstva (po inputoch) dava na vstup neuronu vstupy
				if (i == 1) {
					for (m = 0; m < inputs.length; m++) {
						tmp[m] = conns.get(m).getWage()*inputs[m];
					}
				}
				// dalsie vrstvy dostavaju na vstup neuronov vystupy neuronov predoslych vrstiev 
				else {
					for (m = 0; m < cAblesBefore.size(); m++) {
						tmp[m] = conns.get(m).getWage()*cAblesBefore.get(m).getLastOutput();
						//System.out.println(tmp[m]);
					}					
				}
				cAbles.get(j).activate(tmp);
				//System.out.println();
			}
		}
		for (i = 0; i < getLayers().get(getLayers().size()-1).getConnectables().size();i++) {
			ret[i] = getLayers().get(getLayers().size()-1).getConnectables().get(i).getLastOutput();
		}
		lastOutputs = ret;
		return ret;
	}
	
	
	public void setLastOutputs(double[] lastOutputs) {
		this.lastOutputs = lastOutputs;
	}
	public double[] getLastOutputs() {
		return lastOutputs;
	}
	public void setLayers(ArrayList<Layer> layers) {
		this.layers = layers;
	}
	public ArrayList<Layer> getLayers() {
		return layers;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double getFitness() {
		return fitness;
	}
	public void setWages(ArrayList<Double[]> wages) {
		
	}
	public double[] getWages() {
		ArrayList<Connectable> cAbles;
		ArrayList<Connection> conns;
		double ret[] = new double[getConnectionNum()];
		int i,j,k,m = 0;
		for (i = 1; i < getLayers().size(); i++) {
			cAbles = getLayers().get(i).getConnectables();
			for (j = 0; j < cAbles.size(); j++) {
				conns = cAbles.get(j).getInputConnections();
				for (k = 0; k < conns.size(); k++) {
					ret[m++] = conns.get(k).getWage();
				}
			}
		}
		return ret;
	}
	public void setWages(double[] wages) {
		ArrayList<Connectable> cAbles;
		ArrayList<Connection> conns;
		int i,j,k,m = 0;
		for (i = 1; i < getLayers().size(); i++) {
			cAbles = getLayers().get(i).getConnectables();
			for (j = 0; j < cAbles.size(); j++) {
				conns = cAbles.get(j).getInputConnections();
				for (k = 0; k < conns.size(); k++) {
					conns.get(k).setWage(wages[m++]);
				}
			}
		}
	}
	public void setConnectionNum(int connectionNum) {
		this.connectionNum = connectionNum;
	}
	public int getConnectionNum() {
		return connectionNum;
	}
	
}
