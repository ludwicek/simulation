import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
//import java.awt.geom.Point2D;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.*;

import engine.*;
import nn.*;
import genetics.*;
public class Main {
	private long lastCheck;
	private final int period = 16;
	private final int neuroneperiod = 5;
	private final int gaperiod = 800;
	private final int botsNum = 100;
	private final int plants = 120;
	private final int[] layersNum = {5,6,6,2};
	private static final int APPLICATION_WIDTH = 1400;
	private static final int APPLICATION_HEIGHT = 800;

	private double neuronetest;
 	private double gatest;

	private MainWindow main;
	private ArrayList<Bot> bots = new ArrayList<Bot>();
	private ArrayList<Movable> movable = new ArrayList<Movable>();
	private ArrayList<Livable> livable = new ArrayList<Livable>();
	private ArrayList<Consumable> consumable = new ArrayList<Consumable>();
	public static void main(String[] args) {
		Main simulation = new Main();
		simulation.run();
	}
	public void createPlant() {
		Random generator = new Random();
		int x = generator.nextInt(main.getDrawArea().getWidth()); 
		int y = generator.nextInt(main.getDrawArea().getHeight());
		Plant plant = new Plant(new Point(x,y));
		plant.setUnits(50);
		main.getDrawArea().getShapes().add(0,plant);
		consumable.add(plant);
		main.getDrawArea().getListener().getInfoables().add(0,plant);
	}
	public void createBot(int id, int race) {
		Random generator = new Random();
		double x = generator.nextDouble()*main.getDrawArea().getWidth(); 
		double y = generator.nextDouble()*main.getDrawArea().getHeight();
		//int hp = generator.nextInt(100);
		//int direction = generator.nextInt(8) + 1;
		Bot bot = new Bot(new Point2D.Double(x,y), race);
		bot.setFood(100);
		bot.setDirection(id);
		bot.setId(id);
		bot.setMax(new Point(main.getDrawArea().getWidth(),main.getDrawArea().getHeight()));
	
		bot.setNeuralNetwork(createBotNN());
		movable.add(bot);
		livable.add(bot);
		bots.add(bot);
		main.getDrawArea().getShapes().add(bot);
		main.getDrawArea().getListener().getInfoables().add(bot);
		
	}
	public NeuralNetwork createBotNN() {
		Random generator = new Random();
		Layer[] layers = new Layer[layersNum.length];
		int connectablesNum = 0, i, j , k;
		ArrayList<Connectable> connectables = new ArrayList<Connectable>();
		NeuralNetwork nn = new NeuralNetwork();
		connectablesNum = 0;
		// vytvorenie vrstiev
		for (i = 0; i < layersNum.length; i++) {
			connectablesNum += layersNum[i];
			layers[i] = new Layer();
		}
		
		// vytvorenie vstupov a neuronov
		for (i = 0; i < connectablesNum; i++) {
			if (i < layersNum[0]) {
				connectables.add(new Input());
			}
			else { 
				connectables.add(new Neurone());
			}
		}
	
		// pridanie vstupov a neuronov do vrstiev
		for (i = 0, j = 0, k = 0; i < connectablesNum; i++, j++) {
			if (layersNum[k] == j) {
				j = 0; k++;
			}
			layers[k].getConnectables().add(connectables.get(i));
		}
		
		
		int num = 0;
		// vytvorenie spojeni pre vrstvy
		for (i = 0; i < layersNum.length - 1; i++) {
			for (j = 0; j < layersNum[i]; j++) {
				for (k = 0; k < layersNum[i+1]; k++) {
					Connectable connLeft = layers[i].getConnectables().get(j);
					Connectable connRight = layers[i+1].getConnectables().get(k);
					Connection conn = new Connection(connLeft,connRight);
					connLeft.addConnection(conn);
					connLeft.getConnections().get(connLeft.getConnections().size() - 1).setWage((generator.nextDouble() * 2) - 1);
					connRight.addInputConnection(conn);
					num++;
				}
			}
		}
		nn.setConnectionNum(num);
		
		// pridanie vrstiev do NS
		
		for (i = 0; i < layersNum.length; i++) {
			nn.addLayer(layers[i]);
		}	
		return nn;
	}
	public void run() {
		
		
// ------------------------------------------- Testovacie data ---------------------------------------------------------------------	

		int j,m;
		Random generator = new Random();
		main = new MainWindow();
		main.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		main.setVisible(true); 
		main.setResizable(false);
		main.setLocation((screen.width - APPLICATION_WIDTH) / 2, (screen.height - APPLICATION_HEIGHT) / 2);
		
		lastCheck = new Date().getTime();
		

		// vytvorenie objektov
		
		for (j = 0; j < this.getPlants(); j++) {
			createPlant();
		}
		for (m = 0; m < this.getBots(); m ++) {
			createBot(m+1, generator.nextInt(2)+1);
		}
// ---------------------------------------------------------------------------------------------------------------------------------

		
		
// ------------------------------------------- Eventy vykonavane v case ------------------------------------------------------------ 
		javax.swing.Timer t = new javax.swing.Timer(0, new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	          	 boolean action = false;
          		 GeneticAlgorithm ga = new GeneticAlgorithm();
         		 Population pop = new Population();
	          	 long time = new Date().getTime();
	        	 if (lastCheck + period <= time) {
	        		 action = true;
	        		 lastCheck = time;
	        	 }
	             for(Movable shape : movable) {
	            	 if (action) {
	            		 shape.move();
	            	 }
	             } 
	             int consumed = 0;
	             for(Livable shape : livable) {
	            	 
	            	 if (action) {
	            		 shape.testHunger();
	            		 for (Consumable food : consumable) {
	            			 if (!food.isConsumed()) {
	            				 consumed += shape.eat(food);
	            			 }
	            		 }
	            		 for (Livable shape2 : livable) {
	            			 if (!shape.equals(shape2)) {
	            				 shape.testMeet(shape2);	            				 
	            			 }
	            		 }
	            	 }
	             }
	             if (consumed > 0) System.out.println("Pridavam " + consumed +" rastlin");
	             for (int n = 0; n < consumed; n++) {
	            	 createPlant();
	             }
	             
	             // reprodukcia
				if (true == false) {
					boolean added = false;
					for (Bot bot : bots) {
						for (Bot bot1 : bots) {
							if (!bot.equals(bot1) && bot.getRace() == bot1.getRace() && bot.canReproduce(bot1)) {
								
								Chromosome chromosome = new Chromosome();
								Chromosome chromosome1 = new Chromosome();
		          				chromosome.setCells(bot.getNeuralNetwork().getWages());
		          				pop.addChromosome(chromosome);
		          				chromosome1.setCells(bot1.getNeuralNetwork().getWages());
		          				pop.addChromosome(chromosome1);
		          				
		          				ga.setPopulation(pop);
		          				//ga.showChromosomes(ga.getPopulation().getChromosomes());
		          				ga.ga();
		          				System.out.println("--");
		          				//ga.showChromosomes(ga.getPopulation().getChromosomes());
								createBot(bots.size(), bot.getRace());
								createBot(bots.size(), bot.getRace());
								bot.setFood(bot.getFood()-60);
								bot1.setFood(bot1.getFood()-60);
								Bot child1 = bots.get(bots.size()-2);
								Bot child2 = bots.get(bots.size()-1);
								child1.setPosition(new Point2D.Double(bot.getPosition().getX() + 20, bot.getPosition().getY() + 20));
								child2.setPosition(new Point2D.Double(bot.getPosition().getX() + -0, bot.getPosition().getY() - 20));
								child1.getNeuralNetwork().setWages(ga.getPopulation().getChromosomes().get(0).getCells());
								child2.getNeuralNetwork().setWages(ga.getPopulation().getChromosomes().get(1).getCells());
								child1.setParentID1(bot.getId());
								child1.setParentID2(bot1.getId());
								child2.setParentID1(bot.getId());
								child2.setParentID2(bot1.getId());

								
								added = true;
								break;
							}
						}
						if (added) break;
					}
				}
    			 
	          	 if (action) {
	          		 main.getDrawArea().repaint();
	          		 main.updateInfo();
	          		 // ucenie NS
	         		
	          		 neuronetest++;
	          		 gatest++;
	          		 if (neuronetest > neuroneperiod) {
	          			 neuronetest = 0;
	
		          		 for (Bot bot:bots) {
		          			 if (bot.lives()) {
			          			 Drawable nearest = null;
			          			 double dist = Double.MAX_VALUE;
			          			 for (Drawable draw : main.getDrawArea().getShapes()) {
			          				if (bot.isNear(draw) && !draw.equals(bot) && draw.show()) {
			          					if (bot.getPosition().distance(draw.getPosition()) < dist ) {
			          						dist = bot.getPosition().distance(draw.getPosition());
			          						nearest = draw;
			          					}
			          					
			          				}
			          			 }
		
		      					double distance;
		      					double angle;
		      					double type;
		      					double hp;
		      					double blood;
			      					
		          				if (nearest != null) {
		          					//System.out.println(bot.getId() + " najblizsi: " + nearest.getSensorInfo().get(3));
			      					ArrayList<Double> info = nearest.getSensorInfo();
			      					double bot_x =  bot.getPosition().getX();
			      					double bot_y =  bot.getPosition().getY();
			      					double object_x = info.get(0);
			      					double object_y = info.get(1);
			      					//System.out.println("Bot (" + bot.getId() +") vidi " + draw + ") " + info.get(3));
			      					//System.out.println("Uhol (" + bot.getId() +" a " + info.get(3) +"): " + Math.atan(Math.abs(bot_y - object_y)/Math.abs(bot_x - object_x))/Math.PI * 360  );
			      					//System.out.println("Typ: " + info.get(2));
			      					
			      					// senzory 
			      					distance = bot.getPosition().distance(new Point2D.Double(object_x,object_y));
			      					angle = Math.atan(Math.abs(bot_y - object_y)/Math.abs(bot_x - object_x))/Math.PI * 360;
			      					type = info.get(2);
			      					hp = bot.getHp();
			      					blood = info.get(4);
	          					
	          					//System.out.println(distance + "," + angle + "," + type + "," + hp + "," + blood);
	          					
		          				}
		          				else {
		          					Random generator = new Random();
		          					distance = generator.nextDouble()*75;
		          					angle = generator.nextDouble()*360;
		          					type = generator.nextInt(2);
		          					hp = generator.nextInt(100);
		          					blood = generator.nextInt(100);
		          				}
	          					bot.getNeuralNetwork().feedForward(new double[]{distance, angle, type, hp, blood});
		
			          			 
		          			 //double fitness = bot.getNeuralNetwork().getFitness();
		          			 }
		          			 
		          		 }
		          		 // pridanie novych botov do populacie
		          		 if (gatest > gaperiod) {
		          			 /*
		          			 gatest = 0;
			          		 for (Bot bot : bots ) {
			          			 if (bot.lives()) {
			          				 Chromosome chromosome = new Chromosome();
			          				 chromosome.setCells(bot.getNeuralNetwork().getWages());
			          				 chromosome.setFitness(bot.getNeuralNetwork().getFitness());
			          				 pop.addChromosome(chromosome);
			          				 System.out.println(bot.getId() + ": " + bot.getNeuralNetwork().getFitness());
			          				 bot.setDistanceMoved(0);
			          				 bot.setDmgTaken(0);
			          				 bot.setFoodGathered(0);
			          			 }	
			          			 
			          			 
			          		 }
			          		 ga.setPopulation(pop);
			          		//ga.showChromosomes(ga.getPopulation().getChromosomes());
			          		 ga.ga();
			          		//ga.showChromosomes(ga.getPopulation().getChromosomes());
			          		 for (int f = 0; f < ga.getPopulation().getChromosomes().size(); f++) {
				          		createBot(bots.size());
				          		bots.get(bots.size() - 1).getNeuralNetwork().setWages(ga.getPopulation().getChromosomes().get(f).getCells());
			          		 }
		          			*/
		          		 }
		          		 
		          		 
		          		 
		          	 }  
	          	 }
	         }
	       });
		t.start();	
// ---------------------------------------------------------------------------------------------------------------------------------
		
	}
	public ArrayList<Movable> getMovable() {
		return movable;
	}
	public void setMoveable(ArrayList<Movable> movable) {
		this.movable = movable;
	}
	public int getBots() {
		return botsNum;
	}
	public int getPlants() {
		return plants;
	}

}
