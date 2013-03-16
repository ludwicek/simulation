package genetics;

import java.util.Comparator;

public class ChromosomeComparator implements Comparator<Chromosome>{
	public int compare(Chromosome ch1, Chromosome ch2) {
		if (ch1.getFitness() > ch2.getFitness()) { 
			return -1;
		}
		else if (ch1.getFitness() == ch2.getFitness()) {
			return 0;
		}
		else {
			return 1;
		}
		
	}

}
