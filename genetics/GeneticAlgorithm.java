package genetics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import init.Init;

public class GeneticAlgorithm {
	private int ELITE = 2;
	private int CROSSING = 7;
	private int RANDOM = 5;
	
	public static final int MASKED_CROSSING = 1;
	public static final int POINT_CROSSING = 2;
	public static final int ORDINARY_MUTATION = 1;
	public static final int ADDITIVE_MUTATION = 2;
	public static final int MULTIPLICATIVE_MUTATION = 3;
	public static final int COMMA_STRATEGY = 1;
	public static final int ADDITIVE_STRATEGY = 2;

	public static final double ADDITIVE_MAX = 3;
	public static final double MULTIPLICATIVE_MAX = 1.2;
	private static final double MUTATE_RATE = 0.05;
	
	private int size;
	
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
	public Chromosome randomChromosome() {
		Random generator = new Random();
		int size = population.getChromosomes().get(0).getCells().length;
		Chromosome ret = new Chromosome();
		double[] cells = new double[size];
		for(int i = 0; i < size; i++) {
			cells[i] = (generator.nextDouble() * (Init.MAX_WAGE - Init.MIN_WAGE)) + Init.MIN_WAGE;
		}
		ret.setCells(cells);
		return ret;
	}
	public void ga() {
		//System.out.println("POPULACIA");
		//showPopulation();
		//System.out.println("RODICIA");
		setSize(population.getChromosomes().size());
		if (population.getChromosomes().size() <= ELITE + CROSSING) {
			System.out.println("vymreta populacia");
			return;
		}

		int test = selection();
		if (test == 0) {
			parents.clear();
			for (int i = 0; i < CROSSING + ELITE; i++) {
				parents.add(population.getChromosomes().get(i));
			}
		}
		//showParents();

		if (test != -1) operators();
		System.out.println("DETI");
		//showChildren();

		substitute(COMMA_STRATEGY);
		//System.out.println("-----");
		//System.out.println("Konecne:");
		//showChromosomes(population.getChromosomes());
		
		//operators_new();
	}
	public void showParents() {
		for (Chromosome chromosome : parents) {
			System.out.println(chromosome.toString());
		}
	}
	public void showChildren() {
		for (Chromosome chromosome : children) {
			System.out.println(chromosome.toString());
		}
	}
	public void showPopulation() {
		for (Chromosome chromosome : population.getChromosomes()) {
			System.out.println(chromosome.toString());
		}
	}	
	public double[] evaluateProbabilities(double fitnesses[]) {
		double[] ret = new double[fitnesses.length];
		double min = fitnesses[0];
		double max = fitnesses[0];
		double same = fitnesses[0];
		double sum = 0;
		double sum2 = 0;
		boolean allsame = true;
		int i;
		
		for (i = 0; i < fitnesses.length; i++) {
			// osetrenie ci su vsetky hodnoty rovnake
			
			if (same != fitnesses[i]) {
				allsame = false;
			}
			same = fitnesses[i];
			
			// nastavenie minima a maxima
			if (fitnesses[i] < min) {
				min = fitnesses[i];
			}
			if (fitnesses[i] > max) {
				max = fitnesses[i];
			}
			// spocitanie sumy
			sum += Math.abs(fitnesses[i]);
		}
		//System.out.println("MIN: " + min);
		//System.out.println("MAX: " + max);
				
		for (i = 0; i < fitnesses.length; i++) {
			sum2 += ((fitnesses[i] - min)/ sum) ;
		}	
		
		for (i = 0; i < fitnesses.length; i++) {
			if(allsame) {
				ret[i] = (double)1/fitnesses.length;
			}
			else {
				ret[i] = (((fitnesses[i] - min)/sum) / sum2) ;
			}
			//System.out.println(fitnesses[i] +":" + ret[i]);
		}	
		
		return ret;
	}
	public ArrayList<Chromosome> rouletteSelection(ArrayList<Chromosome> chromosomes) {
		//System.out.println("RULETA");
		ArrayList<Chromosome> ret = new ArrayList<Chromosome>();
		Random generator = new Random();
		double[] fitnesses;
		double[] probabilities;
		int i,j = 0;
		double remaining = 1;
		
		if (chromosomes.size() <= CROSSING) {
			ret.addAll(chromosomes);
		}
		else {
			
			while (ret.size() != CROSSING) {
				i = 0;
				fitnesses = new double[chromosomes.size()];
				for (Chromosome chromosome : chromosomes) {
					fitnesses[i++] = chromosome.getFitness();
				}
				probabilities = evaluateProbabilities(fitnesses);
				//System.out.println("----");
				for (j = 0; j < probabilities.length; j++) {
					if (generator.nextDouble()*remaining <= probabilities[j]) {
						ret.add(chromosomes.get(j));
						remaining -= probabilities[j];
						//System.out.println("chosen: " + chromosomes.get(j).getFitness());
						chromosomes.remove(j);
						if (ret.size() == CROSSING) {
							showChromosomes(ret);
							return ret;
						}
						break;
					}
					// ak by ani jeden nebol vybraty, vyberie posledneho (nepresnost atd...)
					else if (j == probabilities.length - 1) {
							ret.add(chromosomes.get(0));
							//System.out.println("chosen: " + chromosomes.get(0).getFitness());
							chromosomes.remove(0);
							if (ret.size() == CROSSING) {
								showChromosomes(ret);
								return ret;
							}
							break;
					}
					
				}				
				
			}
		}
		//showChromosomes(ret);
		return ret;
	}
	// selekcia rodicov
	public int selection() {
		//Random generator = new Random();
		ArrayList<Chromosome> roulette = new ArrayList<Chromosome>();
		int i = 0;
		
		if (population.getChromosomes() == null || population.getChromosomes().size() < CROSSING + ELITE) {
			System.out.println("Nedostatok jedincov v populacii na parenie (menej ako potrebnych na krizenie a mutaciu");
			return -1;
		}
		
		Collections.sort(population.getChromosomes(), new ChromosomeComparator());
		// prvych 5 (elitizmus)
		for (Chromosome chromosome : population.getChromosomes()) {
			//size = population.getChromosomes().size();
			if (i++ < ELITE) {
				parents.add(chromosome);
			}
			else {
				roulette.add(chromosome);
			}
		}

		parents.addAll(rouletteSelection(roulette));
		//System.out.println("hm");
		if (parents == null || parents.size() < CROSSING + ELITE) {
			System.out.println("Nedostatok rodicov na parenie (menej ako potrebnych na krizenie a mutaciu");
			return 0;
		}
		
		return 1;
		
		
		
	}
	//operatory (krizenie, mutacia)
	public void operators() {	
		//elitizmus
		for (int i = 0; i < ELITE; i++) {
			children.add(parents.get(i));
		}		
		// krizenie
		for (int i = ELITE; i < ELITE + CROSSING - 1; i++) {
			for (int j = i + 1; j < ELITE + CROSSING; j++) {
				children.addAll(parents.get(i).cross(parents.get(j), MASKED_CROSSING));
				if (children.size() >= getSize()) break;
			}
			if (children.size() >= getSize()) break;
		}
		
		// musime zachovat velkost populacie deti
		if (children.size() > getSize()) children.remove(children.size()-1);
		
		//showPopulation();
		
		// ak sme nagenerovali malo deti, pridame do novej generacie aj ostatnych z povodnej populacie ( okrem rodicov )

		// odstranime rodicov z povodnej
		population.getChromosomes().removeAll(parents);
		
		//showPopulation();
		
		// pridame deti
		if (children.size() < getSize()) {
			for (int i = ELITE + CROSSING; ; i++) {
				children.add(population.getChromosomes().get(i));
				if (children.size() == getSize() - RANDOM) break;
			}
			
		}
		
		//nahodne chromozomy
		for(int i = 0; i < RANDOM; i++) {
			children.add(randomChromosome());
		}
		
		
		// mutacie
		for (int i = ELITE; i < getSize(); i++) {
			children.add(children.get(i).mutate(MUTATE_RATE, ADDITIVE_MUTATION));
		}
		for (int i = ELITE; i < getSize(); i++) {
			children.remove(ELITE);
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
	public void setSize(int size) {
		this.size = size;
	}
	public int getSize() {
		return size;
	}
	
}
