package genetics;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome {
	private double[] cells;
	private double fitness;
	private double probability;
	private int id;
	public void setCells(double[] cells) {
		this.cells = cells;
	}
	public String toString() {
		String ret = id + ": [";
		for (int i = 0; i < getCells().length; i++) {
			ret += getCells()[i]; 
				if (i < getCells().length - 1) 
			ret += ",";
		}
		ret += "], fittness: " + getFitness();
		return ret;
	}

	public double[] getCells() {
		return cells;
	}
	public ArrayList<Chromosome> cross(Chromosome chromosome, int type) {
		ArrayList<Chromosome> ret = new ArrayList<Chromosome>();
		Chromosome child1 = new Chromosome();
		Chromosome child2 = new Chromosome();
		ret.add(child1);
		ret.add(child2);
		boolean mask[] = new boolean[cells.length];
		double retCells1[] = new double[cells.length];
		double retCells2[] = new double[cells.length];
		Random generator = new Random();
		
		String tmp = "";
		
		//ystem.out.println(this.toString());
		//System.out.println(chromosome.toString());
		
		switch (type) {
			case GeneticAlgorithm.MASKED_CROSSING: {
				for (int i = 0; i < mask.length; i++) {
					mask[i] = generator.nextBoolean();
					if (mask[i]) tmp += 1;
					else tmp += 0;
				}
				//System.out.println(tmp);
				for (int i = 0; i < mask.length; i++) {
					if (mask[i]) {
						retCells2[i] = getCells()[i];
						retCells1[i] = chromosome.getCells()[i];						
					}
					else {
						retCells2[i] = chromosome.getCells()[i];
						retCells1[i] = getCells()[i]; 
					}
				}
				ret.get(0).setCells(retCells1);
				ret.get(1).setCells(retCells2);
				break;
			} 
			// TODO
			case GeneticAlgorithm.POINT_CROSSING: {
				break;
			}
		}
		//System.out.println(ret.get(0).toString());
		//System.out.println(ret.get(1).toString());
		//System.out.println("-----------------------------------------------------------------------------------------");
		return ret;
	}
	public Chromosome mutate(double rate, int type) {
		Random generator = new Random();
		double additiveParam = (generator.nextDouble() * (2 * GeneticAlgorithm.ADDITIVE_MAX)) - GeneticAlgorithm.ADDITIVE_MAX;
		double multiplicativeParam = (generator.nextDouble() * ( 2 * GeneticAlgorithm.MULTIPLICATIVE_MAX)) - GeneticAlgorithm.MULTIPLICATIVE_MAX;
		double min = Double.MAX_VALUE,max = Double.MIN_VALUE;
		Chromosome ret = new Chromosome();
		//System.out.println(this.toString());
		//System.out.println("aha");

		double [] cells = getCells();
		for (int i = 0; i < cells.length; i++) {
			if (cells[i] < min) {
				min = cells[i];
			}
			if (getCells()[i] > max) {
				max = cells[i];
			}
		}
		for (int i = 0; i < getCells().length; i++) {
			if (generator.nextDouble() < rate) {
				//System.out.println("mutujeeeeeeem");
				switch(type) {
					case GeneticAlgorithm.ORDINARY_MUTATION : {
						cells[i] =  (generator.nextDouble() * (max - min)) - min;
						break;
					}
					case GeneticAlgorithm.ADDITIVE_MUTATION : {
						cells[i] += generator.nextDouble() * additiveParam;
						break;
					}
					case GeneticAlgorithm.MULTIPLICATIVE_MUTATION: {
						cells[i] *= generator.nextDouble() * multiplicativeParam;
						break;					
					}
				}
			}
		}
		ret.setCells(cells);
		//System.out.println(ret.toString());
		return ret;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double getFitness() {
		return fitness;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public double getProbability() {
		return probability;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
