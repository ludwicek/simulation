package init;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
//import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import nn.Connectable;
import nn.Connection;
import nn.Input;
import nn.Layer;
import nn.NeuralNetwork;
import nn.Neurone;

import simulation.BotAnimation;
import simulation.PlantAnimation;
import engine.Bot;
import engine.Consumable;
import engine.Dragable;
import engine.Drawable;
import engine.Hive;
import engine.InfoFrame;
import engine.Infoable;
import engine.Livable;
import engine.MainWindow;
import engine.Movable;
import engine.Plant;
import genetics.Chromosome;
import genetics.GeneticAlgorithm;
import genetics.Population;
import graph.LineGraph;


public class Init {
	private long lastCheck;
	private long lastCheck2;
	private int period = 10;
	private final int neuroneperiod = 15;
	public static final int EQUAL_BOT = 0;
	public static final int FOREIGN_BOT = 1;
	public static final int PLANT = 2;
	public static final int EQUAL_HIVE = 3;
	public static final int FOREIGN_HIVE = 4;

	private long periodGraph = 60000;
	
	private final int TIME_TO_ADD_FOOD = 5;
	private final int gaperiod = 6000;
	private int botsNum = 100;
	private final int plants = 100;
	
	public static final double MAX_WAGE = 2.5; 
	public static final double MIN_WAGE = -2.5;
	
	
	private final int startfood = 0;
	private int raceCount = 1;
	private final int maxRaces = 4;
	private final int maxLabels = 61000;
	private int actualBotID = 1;
	
	private double[][] updateValues = new double[raceCount][maxLabels];
	private String[] updateLabels = new String[maxLabels];
	
	//private double[][] updateValues2 = new double[raceCount][maxLabels];
	
	public final int drawareaWidth = 1900;
	public final int drawareaHeight = 1300;
	
	public final int appWidth = 1000;
	public final int appHeight = 600;
	
	public final int rightPanelWidth = 200;
	
	public final int boundaries = 0;
	
	private double neuronetest = 0;
	private double foodtest = 0;
 	private double gatest;
 	private boolean running = true;

	private int generation = 0;
	
	private int commonInputs = 4;
	private int[] sensorsOn = new int[] {EQUAL_BOT,EQUAL_HIVE,PLANT};
	private final int[] layersNum = {commonInputs + 2*sensorsOn.length,commonInputs + 2*sensorsOn.length,commonInputs + 2*sensorsOn.length + 1,2};
	

	private MainWindow main;
	private CopyOnWriteArrayList<Bot> bots = new CopyOnWriteArrayList<Bot>();
	private CopyOnWriteArrayList<Hive> hives = new CopyOnWriteArrayList<Hive>();
	private CopyOnWriteArrayList<Movable> movables = new CopyOnWriteArrayList<Movable>();
	private CopyOnWriteArrayList<Livable> livables = new CopyOnWriteArrayList<Livable>();
	private CopyOnWriteArrayList<Consumable> consumables = new CopyOnWriteArrayList<Consumable>();
	private CopyOnWriteArrayList<Infoable> infoables = new CopyOnWriteArrayList<Infoable>();
	private ArrayList<Dragable> dragables = new ArrayList<Dragable>();
	
	private BotAnimation botanim = new BotAnimation();
	private PlantAnimation plantanim = new PlantAnimation();
	
	private LineGraph graph;
	private LineGraph graph2;
	private InfoFrame infos;
	private boolean newSim = false;
	
	private NeuralNetwork loadNS[] = new NeuralNetwork[maxRaces];
	
	
	// DEBUG
	
	 // private FileWriter fstream;
	 // private BufferedWriter out;

	
    public void createBot(int race, boolean visible) {
    	double x = 0,y = 0; 
    	switch(race) {
			case 1:  x = drawareaWidth / 2; y = drawareaHeight / 2; break;
			case 2:  x = drawareaWidth - 150; y =  drawareaHeight - 150; break;
			case 3:  x = drawareaWidth - 150; y =  150; break;
			case 4:  x = 150; y =  drawareaHeight - 150; break;
    	}
		createBot(race,new Point2D.Double(x,y), visible);
    }
    public void createBot(int race, Point2D point,  boolean visible) {
    	Random generator = new Random();
		
		Bot bot = new Bot(point, race, sensorsOn.length);
		bot.setAnim(botanim);
		bot.setFood(startfood);
		bot.setDirection(generator.nextInt(360));
		bot.setId(actualBotID);
		bot.setMax(new Point(drawareaWidth,drawareaHeight));
		if (visible)
			bot.setHp(100);
		
		bot.setFood(100);
	
		bot.setNeuralNetwork(createBotNN(loadNS[race-1]));
		bot.setBirthPoint(new Point2D.Double(point.getX(), point.getY()));
		movables.add(bot);
		livables.add(bot);
		bots.add(bot);
		dragables.add(bot);
		main.getDrawArea().getShapes().add(bot);
		infoables.add(bot);
		actualBotID++;
		
	}
    
	public void createPlant(boolean visible) {
		Random generator = new Random();
		int x = (int) ((generator.nextDouble() * 1 * drawareaWidth) + (0 * drawareaWidth)); 
		int y = (int) ((generator.nextDouble() * 0.2 * drawareaHeight) + (0.03 * drawareaHeight));
		createPlant(new Point2D.Double(x,y), visible);
		createPlant(new Point2D.Double(x,drawareaHeight - y), visible);
	}
	public void createPlant(Point2D point, boolean visible) {
		Plant plant = new Plant(point);
		if (visible)
			plant.setUnits(50);
		
		plant.setAnim(plantanim);
		main.getDrawArea().getShapes().add(0,plant);
		consumables.add(plant);
		infoables.add(0,plant);
	}


    public void createHive(int type) {
    	Hive hive = new Hive();
    	Point2D point;
    	double x = 0,y = 0;
    	switch(type) {
    		case 0:  x = drawareaWidth / 2; y = drawareaHeight / 2; break;
    		case 1:  x = drawareaWidth - 150; y =  drawareaHeight - 150; break;
    		case 2:  x = drawareaWidth - 150; y =  150; break;
    		case 3:  x = 150; y =  drawareaHeight - 150; break;
    	}
    	point = new Point2D.Double(x,y);
    	hive.setPosition(point);
    	hive.setType(type+1);
    	main.getDrawArea().getShapes().add(hive);
    	infoables.add(hive);
    	hives.add(hive);
    }
	public NeuralNetwork createBotNN(NeuralNetwork copy) {
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
					double wage;
					if (copy != null) {
						wage = copy.getWages()[num];
					}
					else {
						wage = (generator.nextDouble() * (MAX_WAGE - MIN_WAGE)) + MIN_WAGE;
					}
					connLeft.getConnections().get(connLeft.getConnections().size() - 1).setWage(wage);
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
	
	public static double getFitness(Bot bot) {
		return(10000 * bot.getFoodInstancesCarried()) + (1000 * bot.getFoodInstancesFound()) - (35 * bot.getDmgTaken()) + bot.getDistanceMoved() * 0.1 ;
	}
	
    public void save(String filename) {
    	ObjectOutputStream outputStream = null;
    	try {
            outputStream = new ObjectOutputStream(new FileOutputStream(filename));
            for (int i = 0; i < main.getDrawArea().getShapes().size(); i++) {
                outputStream.writeObject(main.getDrawArea().getShapes().get(i));
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                    System.out.println("Ulozenie prebehlo v poriadku");
                }
            } catch (IOException ex) {
                System.out.println("Chyba pri ukladani");
            }
        }
    }
    public void load(String filename) {
        ObjectInputStream inputStream = null;
        int objCount = 0;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(filename));
            
            main.getDrawArea().getShapes().clear();
            infoables.clear();
            bots.clear();
            movables.clear();
            livables.clear();
            consumables.clear();
            dragables.clear();
            
            
            
            while (true) {
                Object o = inputStream.readObject();
                if (o instanceof Drawable) {
                	System.out.println("Pridavam do drawable");
                	if (o instanceof Bot)
                		((Drawable) o).setAnim(botanim);
                	if (o instanceof Plant)
                		((Drawable) o).setAnim(plantanim);
                	if (o instanceof Hive) {
                		hives.add((Hive) o);
                	}
                	main.getDrawArea().getShapes().add((Drawable)o);
                }
                if (o instanceof Consumable) {
                	consumables.add((Consumable)o);
                	objCount++;
                }
                if (o instanceof Infoable) {
                	infoables.add((Infoable)o);
                }
                if (o instanceof Bot) {
                	bots.add((Bot)o);
                }
                if (o instanceof Movable) {
                	movables.add((Movable)o);
                }
                if (o instanceof Livable) {
                	livables.add((Livable)o);
                }
                if (o instanceof Dragable) {
                	dragables.add((Dragable)o);
                }
            }

        } catch (EOFException ex) {
            System.out.println("End of file reached.");
        } catch (ClassNotFoundException ex) {
            System.out.println("1");
        } catch (FileNotFoundException ex) {
            System.out.println("2");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            //Close the ObjectInputStream
            try {
                if (inputStream != null) {
                    inputStream.close();
                    System.out.println("Nacitanie prebehlo v poriadku (" + objCount + " objektov)");
                    main.getDrawArea().repaint();
                }
            } catch (IOException ex) {
            	System.out.println("hú");            }
    }
    }
	public void run() {
		
		
// ------------------------------------------- Testovacie data ---------------------------------------------------------------------	

		int j,m,l;
		final Random generator = new Random();

		/*double number = 360/(180/Math.PI);
		System.out.println("cos "+ number +": " + Math.cos(number));
		System.out.println("sin "+ number +": " + Math.sin(number));
		if (true == true) return;
		*/
		main = new MainWindow(this);
		//main.setSize(MainWindow.APPLICATION_WIDTH, MainWindow.APPLICATION_HEIGHT);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		main.setVisible(true); 
		main.setResizable(false);
		main.setLocation((screen.width - appWidth) / 2, (screen.height - appHeight) / 2);
		//main.setPreferredSize(new Dimension(300,800));
		main.getDrawArea().setInit(this);
		main.setMenusEnabled(false);
		
		while(newSim  != true) {
			;			
		}
		for (int i = 0; i < loadNS.length; i++) {
			System.out.println(loadNS[i]);
		}
		lastCheck = new Date().getTime();
		
		//System.out.println(Stringutil.formatDouble(105.4681861, 2));
		// vytvorenie objektov
		/*
		for (j = 0; j < this.getPlants() - 200; j++) {
			createPlant(false);
		}
		for (j = this.getPlants() - 200; j < this.getPlants(); j++) {
			createPlant(true);
		}
		for (m = 0; m < botsNum - 200; m ++) {
			createBot(m+1, generator.nextInt(raceCount)+1, false);
		}
		for (m = botsNum - 200; m < botsNum; m ++) {
			createBot(m+1, generator.nextInt(raceCount)+1, true);
		}
		*/
		for (j = 0; j < this.getPlants(); j++) {
			createPlant(true);
		}
		for (m = 0; m < botsNum; m ++) {
			createBot(generator.nextInt(raceCount)+1, true);
		}
		
		for (l = 0; l < raceCount; l++) {
			createHive(l);
		}
		
		graph = new LineGraph(new String[] {"Modrý bot", "Èervený bot", "Zelený bot", "Šedý bot"}, "Maximálna fitness", "generácia", "max fitness", new Color[] {Color.BLUE, Color.RED, Color.GREEN, Color.GRAY}, raceCount);
		//graph = new LineGraph(new String[] {"Modrý bot"}, "Maximálna fitness", "generácia", "fitness", new Color[] {Color.BLUE}, raceCount);
		graph.setVisible(false);
		/*graph2 = new LineGraph(new String[] {"Èervený bot", "Modrý bot", "Zelený bot", "Šedý bot"}, "Poèet botov", "èas (min.)", "poèet", new Color[] {Color.RED, Color.BLUE, Color.GREEN, Color.GRAY}, raceCount);
		graph2.setVisible(false);*/
		infos = new InfoFrame(this);
		infos.setVisible(false);
		infos.setResizable(false);
		infos.setLocation((screen.width - infos.getWidth()) / 2, (screen.height - infos.getHeight()) / 2);
		
		
		//graph.updateGraph(new double[][]{new double[]{5.4,3.5},new double[]{7.8,3.9}}, new String[]{"kkt", "dpc"});
		
		
		/*
		GeneticAlgorithm ga = new GeneticAlgorithm();
		ArrayList<Chromosome> chrom = new ArrayList<Chromosome>();
		for (int i = 0; i < 4; i++) {
			chrom.add(new Chromosome());
			chrom.get(i).setCells(new double[]{i+1,i+1,i+1});
		}
		chrom.get(0).setFitness(-1);
		chrom.get(1).setFitness(-2);
		chrom.get(2).setFitness(-3);
		chrom.get(3).setFitness(-4);
		Population pop = new Population();
		pop.setChromosomes(chrom);
		ga.setPopulation(pop);
		ga.showChromosomes(ga.getPopulation().getChromosomes());
		
		ga.ga();
		if (true) return;
		*/

// ---------------------------------------------------------------------------------------------------------------------------------

		
		
// ------------------------------------------- Eventy vykonavane v case ------------------------------------------------------------ 
		javax.swing.Timer t = new javax.swing.Timer(0, new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	          	 boolean action = false;
	          	 long time = new Date().getTime();
	          	 //period = 29 - (int)main.getSimSpeed();
	          	 if (main.getLoad() != "") {
	          		 System.out.println("Loading file: " + main.getLoad());
	          		 load(main.getLoad());
	          		 main.setLoad("");
	          	 }
	          	 if (main.getSave() != "") {
	          		 System.out.println("Saving to file: " + main.getSave());
	          		 save(main.getSave());
	          		 main.setSave("");
	          	 }

	        	 if (lastCheck + period <= time) {
	        		 running = main.isRunning();
	        		 if (running) action = true;
	        		 lastCheck = time;
	        	 }
	        	 if (lastCheck2 + periodGraph <= time) {
	        		 running = main.isRunning();
	        		 lastCheck2 = time;
	        	 }

	        	 if (action) {
	        		 for(Bot bot : bots) {
	        			 if (bot.lives() && bot.isCarryingFood()) {
	        				 for (Hive hive : hives) {
	        					 if (hive.getType() == bot.getRace() && bot.meetHive(hive) ) {
	        						 bot.setCarryingFood(false);
	        						 bot.setFoodCarried(bot.getFoodCarried() + bot.getFoodCarrying());
	        						 hive.setFoodGathered(hive.getFoodGathered() + bot.getFoodCarrying());
	        						 bot.setFoodCarrying(0);
	        					 }
	        				 }
	        			 }
	        		 }
	        	 }

	             // pridanie rastliny ked je zjedena
            	 /*
            	 for (int n = 0; n < consumed; n++) {
	            	 createPlant();
	             }
	             */
	             
	             // reprodukcia
	             /*
				if (action) {
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
				*/
            	 if (action) {
            		 //System.out.println("Pocet vykreslovanych objektov: " + main.getDrawArea().getShapes().size());
            		 //
            		 
            		 for(Movable shape : movables) {
	            		if (shape.lives())shape.move();
	            	 }
	             }   
            	 // GA
          		 if (gatest > gaperiod) {
          			 double max = Double.NEGATIVE_INFINITY; 
          			 double min = Double.POSITIVE_INFINITY;
          			 double maxAge = 0, minAge = 0;
	        		 double maxFit[] = new double[raceCount];
	        		 for (int race = 0; race < raceCount; race++) {
	        			 maxFit[race] = Double.NEGATIVE_INFINITY;
	        		 }
          			 for (int a = 0; a < raceCount; a++) {
                  		 GeneticAlgorithm ga = new GeneticAlgorithm();
                 		 Population pop = new Population();

	          			 gatest = 0;
	          			 generation++;
	          			 
		          		 for (Bot bot : bots ) {
		          			 if (bot.lives() && bot.getRace() == a + 1) {
		          				 bot.setNewPop(false);
		          				 Chromosome chromosome = new Chromosome();
		          				 chromosome.setCells(bot.getNeuralNetwork().getWages());
		          				 double fitness = getFitness(bot);
		          				 //System.out.println(bot.getDistanceMoved());
		          				 bot.getNeuralNetwork().setFitness(fitness);
		          				 chromosome.setFitness(fitness);
		          				 if (fitness > max) {
		          					 max = fitness;
		          					 maxAge = bot.getAge();
		          				 }
		          				 if (fitness < min) {
		          					 min = fitness;
		          					 minAge = bot.getAge();
		          				 }				          				 
		          				 pop.addChromosome(chromosome);
		          				 //System.out.println(bot.getId() + ": " + bot.getNeuralNetwork().getFitness());
		          				 
		          				 if (fitness > maxFit[a]) {
		          					 maxFit[a] = fitness;
		          				 }          				 
		          				 
		          				 bot.setDistanceMoved(0);
		          				 bot.setDmgTaken(0);
		          				 bot.setFoodGathered(0);
		          				 bot.setCarryingFood(false);
		          				 bot.setFoodCarrying(0);
		          				 bot.setFoodInstancesCarried(0);
		          				 bot.setFoodInstancesFound(0);
		          				 bot.setPosition(new Point2D.Double(bot.getBirthPoint().getX(), bot.getBirthPoint().getY()));
		          			 }	
		          			 
		          		 }
      				 	 updateValues[a][generation] = maxFit[a]; 
    	        		 updateLabels[generation-1] = Integer.toString(generation);		        	        		 	        		 
    	        		 graph.updateGraph(updateValues, updateLabels, generation);
		          		 
		          		 ga.setPopulation(pop);
		          		//ga.showChromosomes(ga.getPopulation().getChromosomes());
		          		 ga.ga();
		          		//ga.showChromosomes(ga.getPopulation().getChromosomes());
		          		 for (int f = 0; f < ga.getPopulation().getChromosomes().size(); f++) {
			          		bots.get(f).getNeuralNetwork().setWages(ga.getPopulation().getChromosomes().get(f).getCells());
		          		 }
          			 }
          			 System.out.println("MIN fitness: " + min + "(" + minAge + ")");
          			 System.out.println("MAX fitness: " + max + "(" + maxAge + ")");
          			
          		 }     
            	 if (action) {
            		 for(Livable shape : livables) {
	            		 if (shape.lives()) {
		            			 shape.testHunger();
		            		 for (Consumable food : consumables) {
		            			 if (!food.isConsumed() && food.getUnits() > 0) {
		            				shape.eat(food);
		            			 }
		            			 if (food.getUnits() <= 0) {
		            				 consumables.remove(food);
			          				 main.getDrawArea().getShapes().remove(food);
			          				 infoables.remove(food);
			          				 break;
		            			 }
		            		 }
		            		 for (Livable shape2 : livables) {
		            			 if (!shape.equals(shape2) && shape.lives()) {
		            				shape.testMeet(shape2);	            				 
		            			 }
		            		 }
	            		 }
	            	 }
	             }          		 
	          	 if (action) {
	          		// main.getDrawArea().repaint();
	          		 main.updateInfo();
	          		 // ucenie NS
	         		
	          		 neuronetest++;
	          		 gatest++;
	          		 if (neuronetest > neuroneperiod) {
	          			 neuronetest = 0;
	
		          		 for (Bot bot:bots) {
		          			 if (bot.lives()) {
			          			 int carriesFood = bot.isCarryingFood() ? 100 : 0;
			          			 double distLow[] = new double[sensorsOn.length];
			          			 Drawable drawNear[] = new Drawable[sensorsOn.length];
			          			 for (int i = 0; i < sensorsOn.length; i++) {
			          				 distLow[i] = Double.POSITIVE_INFINITY;
			          			 }
			          			 
			          			for (Drawable draw : main.getDrawArea().getShapes()) {
			          				if (draw.show() && !draw.equals(bot) && bot.isNear(draw)) {			          					
				          				for (int i = 0; i < sensorsOn.length; i++) {
				          					if (bot.getSensorSystem().isActive(draw, sensorsOn[i])) {
				          						distLow[i] = bot.getPosition().distance(draw.getPosition());
				          						drawNear[i] = draw;
				          					}
				          					
				          				}
			          					
			          				}
			          			}
			          				
			          			double[] neuroneInputs = new double[commonInputs + (sensorsOn.length*2)];

			          			double distance;
			          			double angle;
		      					double bot_x =  bot.getPosition().getX();
		      					double bot_y =  bot.getPosition().getY();
			          			
		      					int tmp = 0;
			          			for (int i = 0; i < sensorsOn.length; i++) {
			          				if (drawNear[i] == null) continue; 
		      						ArrayList<Double> info = drawNear[i].getSensorInfo();
			      					double object_x = info.get(0);
			      					double object_y = info.get(1);
			      					distance = bot.getPosition().distance(new Point2D.Double(object_x,object_y));
			      					if (bot_x != object_x) {
			      						angle = Math.atan(Math.abs(bot_y - object_y)/Math.abs(bot_x - object_x))/Math.PI * 360;
			      					}
			      					else {
			      						angle = 0;
			      					}
			      					neuroneInputs[tmp++] = angle;
			      					neuroneInputs[tmp++] = distance;
			          				
			          			}

			          			int next = (sensorsOn.length*2);
			          			neuroneInputs[next++] = bot.getPosition().getX();
			          			neuroneInputs[next++] = bot.getPosition().getY();
			          			neuroneInputs[next++] = carriesFood;
			          			neuroneInputs[next++] = bot.getDirection();
			          			
		      					bot.getNeuralNetwork().feedForward(neuroneInputs);	          					
		
			          			 
		          			 //double fitness = bot.getNeuralNetwork().getFitness();
		          			 }
		          			 else {
		          				 bots.remove(bot);
		          				// main.getInfoPanel().setVisible(false);
		          				 movables.remove(bot);
		          				 livables.remove(bot);
		          				 dragables.remove(bot);
		          				 main.getDrawArea().getShapes().remove(bot);
		          				 infoables.remove(bot);
		          			 }
		          			 
		          		 }
		          	 }  
	          	 }
	          	 if (action) {
	          		 foodtest++;
	          		 if (foodtest > TIME_TO_ADD_FOOD) {
	          			 foodtest = 0;
	          			 if (consumables.size() < plants*2)createPlant(true);
	          		 }
	          	 }
	        	 main.getDrawArea().repaint();
	         }
	       });
		t.start();	
// ---------------------------------------------------------------------------------------------------------------------------------
		
	}
	public LineGraph getGraph2() {
		return graph2;
	}
	public void setGraph2(LineGraph graph2) {
		this.graph2 = graph2;
	}
	public CopyOnWriteArrayList<Movable> getMovable() {
		return movables;
	}
	public void setMoveable(CopyOnWriteArrayList<Movable> movables) {
		this.movables = movables;
	}
	public CopyOnWriteArrayList<Bot> getBots() {
		return bots;
	}
	public int getPlants() {
		return plants;
	}
	public Bot getBot(int botID) {
		for (Bot bot : bots) {
			if (bot.getId() == botID) return bot;
		}
		return null;
	}
	public void setDragables(ArrayList<Dragable> dragables) {
		this.dragables = dragables;
	}
	public ArrayList<Dragable> getDragables() {
		return dragables;
	}
	public LineGraph getGraph() {
		return graph;
	}
	public void setGraph(LineGraph graph) {
		this.graph = graph;
	}
	public int getRaceCount() {
		return raceCount;
	}
	public InfoFrame getInfos() {
		return infos;
	}
	public void setInfos(InfoFrame infos) {
		this.infos = infos;
	}
	public MainWindow getMain() {
		return main;
	}
	public void setMain(MainWindow main) {
		this.main = main;
	}
	public CopyOnWriteArrayList<Infoable> getInfoables() {
		return infoables;
	}
	public void setInfoables(CopyOnWriteArrayList<Infoable> infoables) {
		this.infoables = infoables;
	}
	/*
	public BufferedWriter getOut() {
		return out;
	}
	public void setOut(BufferedWriter out) {
		this.out = out;
	}
	*/
	public boolean isNewSim() {
		return newSim;
	}
	public void setNewSim(boolean newSim) {
		this.newSim = newSim;
	}
	public int getBotsNum() {
		return botsNum;
	}
	public void setBotsNum(int botsNum) {
		this.botsNum = botsNum;
	}
	public void setRaceCount(int raceCount) {
		this.raceCount = raceCount;
	}
	public void setLoadNS(NeuralNetwork loadNS[]) {
		this.loadNS = loadNS;
	}
	public NeuralNetwork[] getLoadNS() {
		return loadNS;
	}


}


