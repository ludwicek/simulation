package genetics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm {
	private static final int ELITE = 1;
	private static final int CROSSING = 2;
	private static final int MUTATE = 1;
	private static final int ROULETTE = 20;
	public static final int MASKED_CROSSING = 1;
	public static final int POINT_CROSSING = 2;
	public static final int ORDINARY_MUTATION = 1;
	public static final int ADDITIVE_MUTATION = 2;
	public static final int MULTIPLICATIVE_MUTATION = 3;
	public static final int COMMA_STRATEGY = 1;
	public static final int ADDITIVE_STRATEGY = 2;

	public static final double ADDITIVE_MAX = 15;
	public static final double MULTIPLICATIVE_MAX = 3.6;
	private static final double MUTATE_RATE = 0.02;
	
	private Population population;
	private ArrayList<Chromosome> parents = new ArrayList<Chromosome>();
	private ArrayList<Chromosome> children = new ArrayList<Chromosome>();
	public GeneticAlgorithm(Population population) {
		setPopulation(population);
	}
	public GeneticAlgorithm() {
	}
	public void setPopulation(Population population) {
		this.population = population;
	}
	public Population getPopulation() {
		return population;
	}
	public void showChromosomes(ArrayList<Chromosome> chromosomes) {
		for (Chromosome chromosome : chromosomes) {
			System.out.println(chromosome.toString());
		}		
	}
	public void ga() {
		//selection();
		//operators();
		operators_new();
		//substitute(COMMA_STRATEGY);
	}
	public void showParents() {
		for (Chromosome chromosome : parents) {
			System.out.println(chromosome.toString());
		}
	}
	public ArrayList<Chromosome> rouletteSelection(ArrayList<Chromosome> chromosomes) {
		//showChromosomes(parents);
		int num = 0, cycles = 0; 
		
		double sum = 0;
		double probability;
		double min = chromosomes.get(0).getFitness();
		double max = chromosomes.get(0).getFitness();
		double range = 0;
		boolean nulls = false;
		Random generator = new Random();
		ArrayList<Chromosome> ret = new ArrayList<Chromosome>();
		for (Chromosome chromosome : chromosomes) {
			if (chromosome.getFitness() < min) {
				min = chromosome.getFitness();
			}
			if (chromosome.getFitness() > max) {
				max = chromosome.getFitness();
			}
			sum += chromosome.getFitness();
		}
		System.out.println(min);
		System.out.println(max);
		range = max - min;
		
		if (max == min && sum == 0) {
			nulls = true;
		}
		
		if (max == min && Math.abs(max) != Math.abs(min) && sum == 0) {
			nulls = false;
		}
		
		//System.out.println(nulls);
		
		for (Chromosome chromosome : chromosomes) {
			if (nulls) {
				probability = 1/(double)chromosomes.size();
				//System.out.println(probability);
			}
			else {
				probability = ((chromosome.getFitness() - min)/range)/chromosomes.size();
				System.out.println(probability);
			}
			
			chromosome.setProbability(probability);
		}
		if (CROSSING + MUTATE > chromosomes.size()) {
			return null;
		}
		while (num != CROSSING + MUTATE) {
			for (Chromosome chromosome : chromosomes) {
				if (num == CROSSING + MUTATE) break;
				if (generator.nextDouble() < chromosome.getProbability()) {
					//System.out.println(chromosome.getProbability());
					num++;
					ret.add(chromosome);
					chromosomes.remove(chromosome);
					break;
				}
			}
			// pravdepodobne su velmi nizke pravdepodobnosti (alebo vela nulovych)
			if (chromosomes.size() == 0 || cycles ++ > 100) {
				if (num++ == CROSSING + MUTATE) break;
				ret.add(ret.get(ret.size()-1));
			}
		}
		return ret;
	}
	// selekcia rodicov
	public void selection() {
		//Random generator = new Random();
		ArrayList<Chromosome> roulette = new ArrayList<Chromosome>();
		int i = 0, j = 0;
		if (population.getChromosomes().size() < ELITE + CROSSING + MUTATE) {
			System.out.println("Nedostatok rodicov na parenie (0)");
		}
		
		Collections.sort(population.getChromosomes(), new ChromosomeComparator());
		// prvych 5 (elitizmus)
		for (Chromosome chromosome : population.getChromosomes()) {
			//size = population.getChromosomes().size();
			if (i++ < ELITE) {
				parents.add(chromosome);
			}
			else if (j++ < ROULETTE) {
				roulette.add(chromosome);
			}
		}

		parents.addAll(rouletteSelection(roulette));
		//System.out.println("hm");
		if (parents == null) {
			System.out.println("Nedostatok rodicov na parenie (menej ako potrebnych na krizenie a mutaciu");
		}
		
		//showParents();
		
		
	}
	//operatory (krizenie, mutacia)
	public void operators() {	
		//elitizmus
		for (int i = 0; i < ELITE; i++) {
			children.add(parents.get(i));
		}		
		// krizenie
		for (int i = ELITE; i < ELITE + CROSSING; i+=2) {
			children.addAll(parents.get(i).cross(parents.get(i+1), MASKED_CROSSING));
		}
		// mutacie
		for (int i = ELITE + CROSSING; i < parents.size(); i++) {
			children.add(parents.get(i).mutate(MUTATE_RATE, MULTIPLICATIVE_MUTATION));
		}
	}
	
	public void operators_new() {
		int i;
		// dvaja rodicia
		for (i = 0; i < population.getChromosomes().size();i++) {
			parents.add(population.getChromosomes().get(i));
		}
		// krizenie
		children.addAll(parents.get(0).cross(parents.get(1), MASKED_CROSSING));
		//showChromosomes(children);
		// mutacie
		for ( i = 0; i < parents.size(); i++) {
			children.add(children.get(i).mutate(MUTATE_RATE, MULTIPLICATIVE_MUTATION));
		}
		
		children.remove(0);
		children.remove(0);
		
		ArrayList<Chromosome> substitute = new ArrayList<Chromosome>();
		for (i = 0; i < children.size(); i++) {
			substitute.add(children.get(i));
		} 
		population.setChromosomes(substitute);
		
	}
	
	// nahrada novej populacie
	public void substitute(int strategy) {
		int i; 
		//showChromosomes(population.getChromosomes());
		ArrayList<Chromosome> substitute = new ArrayList<Chromosome>();
		for (i = 0; i < children.size(); i++) {
			substitute.add(children.get(i));
		} 
		/*
		for (i = ELITE; i < population.getChromosomes().size(); i++) {
			 substitute.add(children.get(j));
			 if (++j == children.size()) {
				 j = ELITE;
			 }
		 }
		 */
		Collections.sort(substitute, new ChromosomeComparator());
		population.setChromosomes(substitute);
		 //System.out.println("-----------------------------------------------------------------------------------------");
		 //showChromosomes(population.getChromosomes());
	}
	
}
