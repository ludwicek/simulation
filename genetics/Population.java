package genetics;

import java.util.ArrayList;

public class Population {
	private ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();

	public void addChromosome(Chromosome chromosomes) {
		this.chromosomes.add(chromosomes);
	}

	public ArrayList<Chromosome> getChromosomes() {
		return chromosomes;
	}
	public void setChromosomes(ArrayList<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
	}
}
