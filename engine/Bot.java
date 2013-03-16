package engine;

import init.Init;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.*;


import simulation.Animation;
import utils.Stringutil;

import nn.NeuralNetwork;

public class Bot extends Shape implements Movable, Cloneable, Infoable, Dragable {
	private static final long serialVersionUID = -8131242969514869349L;
	private static final int TIME_TO_EAT_ONE_UNIT_OF_FOOD = 1;
	private static final int TIME_TO_GATHER_ONE_UNIT_OF_FOOD = 1;
	private static final int TIME_TO_TAKE_ONE_DMG = 3;
	private static final int TIME_TO_AGE = 40;
	//private static final int TIME_TO_EXCHANGE_FOOD_FOR_HP = 3;
	//private static final int TIME_TO_CHANGE_DIRECTION = 2;
	//private static final int TIME_TO_MEASURE_FITNESS = 5;
	private static final int BOT_DMG = 4;
	private static final double MIN_SPEED = 0.2;
	private static final double MAX_SPEED = 2;
	private static final double BOT_ENERGY_COEFF = 0.01;
	private static final double BOT_ENERGY_COEFF_MIN = 0.001;
	private final int CIRCLE_RADIUS = 35;
	
	private Point2D birthPoint;

	// ci nesie jedlo
	private boolean carryingFood = false;
	// kolko jedla nesie
	private int foodCarrying = 0;
	
	
	// kolko dmg dostal
	private double dmgTaken;
	// kolko presiel
	private double distanceMoved;
	// kolko jedal zozral
	private double foodGathered;
	// kolko jedla preniesol do hniezda
	private int foodCarried = 0;

	// kolko nasiel instancii jedla
	private int foodInstancesFound;
	// kolko preniesol instancii jedla do hniezda
	private int foodInstancesCarried;
	
	private NeuralNetwork neuralNetwork;
	private BotSensorSystem sensorSystem;

	
	private final int PLANT_HEIGHT = 32;
	private final int PLANT_WIDTH = 47;
	
	private boolean newPop;
	
	private final int signRadius = 5; 
	private final int foodRadius = 6;
	
	private double age;
	private boolean dragged;
	private boolean dying;
	private double hp;
	private boolean selected;
	private boolean exchanging;
	private double speed;
	private int id;
	private double food;
	private int parentID1;
	private int parentID2;
	private int tmp_time = 0;
	private int tmp_time1 = 0;
	private int tmp_time2 = 0;
	//private int tmp_time3 = 0;
	private int tmp_time4 = 0;
	private int width;
	private int height;
	//private int tmp_time5 = 0;
	private int tmp_time7 = 0;
	
	// smer pohybu (uhol)
	private double direction;
	// rasa (farba bota - odlisenie od ostatnych)
	private int race;
	// sila utoku
	private int dmg;
	
	private transient Animation anim;
	private double bestFitness = Double.NEGATIVE_INFINITY;

	
	// DEBUG
	
	public Point2D getPosition() {
		return super.getPosition();
	}
	
	public Bot(Point2D p, int race, int sensors) {
		this.sensorSystem = new BotSensorSystem(this);
		this.setPosition(p);
		this.setRace(race);
		this.setDmg(BOT_DMG); 
		this.setSpeed(MIN_SPEED);
		this.setHeight(32);
		this.setWidth(32);
		setDying(false);

	}

	public Bot() {
		//this(new Point(0,0), 0, );
	}
	public void DetermineMotion() {
		if (hp <= 0) {
			setSpeed(0);
		}
		else {
			if (this.getNeuralNetwork().getLastOutputs() == null) {
				setSpeed(MIN_SPEED);
				//System.out.println(id + ": random");
			}
			else {
				setDirection(this.getNeuralNetwork().getLastOutputs()[0] * 360);
				setSpeed((this.getNeuralNetwork().getLastOutputs()[1] * MAX_SPEED) + MIN_SPEED);
				//if (Double.isNaN(this.getNeuralNetwork().getLastOutputs()[0]))
					//System.out.println("vaha:" + this.getNeuralNetwork().getWages()[0]);
				//System.out.println(id + ": neuronka: " + this.getNeuralNetwork().getLastOutputs()[0]);
				//System.out.println(id + ": neuronka: " + this.getNeuralNetwork().getLastOutputs()[1]);
			}
			
			// nastavenie obrazka otocenia			
		}
	}
	public void testHunger() {
		if (hp < 0) {
			hp = 0;
		}
		if (hp > 100) {
			hp = 100;
		}
		if (hp == 0) 
			return;
		if (food < 0) {
			food = 0;
		}
		
		tmp_time++;
		if (tmp_time > TIME_TO_EAT_ONE_UNIT_OF_FOOD && hp > 0) {
			tmp_time = 0;
			if (food <= 0) { 
				if (dying) hp -= getSpeed()*BOT_ENERGY_COEFF + BOT_ENERGY_COEFF_MIN;	
				//System.out.println("AU (" + hp + ")");
			}
			else { 
				if (dying) food -= getSpeed()*BOT_ENERGY_COEFF + BOT_ENERGY_COEFF_MIN;
				//System.out.println("Spotreboval som unit foodu (" + food + "), ( id : "+ id  +")");
			}
		}
		/*
		if (food > 100 && hp < 100) {
			tmp_time5++;
			if (tmp_time5 > TIME_TO_EXCHANGE_FOOD_FOR_HP) {
				tmp_time5 = 0;
				
				// !!! DECISION !!!
				
				//System.out.println("Menim si jedlo za hp (" + id + ")");
				hp++;
				food--;
			}
		}
		*/
		if (hp <= 0) {
			//System.out.println(id + " dead");
		}
		
		
	}
	public boolean meetHive(Hive hive) {
		int hive_x = (int)hive.getPosition().getX();
		int hive_y = (int)hive.getPosition().getY();
		int hive_width = hive.getWidth();
		int x = (int)getPosition().getX();
		int y = (int)getPosition().getY();
		if (x >= hive_x - getWidth() && x <= hive_x + hive_width && y >= hive_y - getHeight() && y < hive_y + hive_width) {
			return true;
		}
		return false;
	}	
	public int eat(Consumable food) {
		//System.out.println("zerem");
		if (this.food > 100) this.food = 100;
		int food_x = (int)food.getPosition().getX();
		int food_y = (int)food.getPosition().getY();
		int x = (int)getPosition().getX();
		int y = (int)getPosition().getY();
		if (x >= food_x - getWidth() && x <= food_x + PLANT_WIDTH && y >= food_y - getHeight() && y < food_y + PLANT_HEIGHT) {
		if (!isCarryingFood()) {
			setCarryingFood(true);
			setFoodCarrying(food.getUnits());
			food.setUnits(0);
			return 1;
		}
			tmp_time1++;
			if (tmp_time1 > TIME_TO_GATHER_ONE_UNIT_OF_FOOD && hp > 0) {
				tmp_time1 = 0;
					if (this.food < 100) { 
						this.food++;
						food.setUnits(food.getUnits() - 1);
					}
					if (this.food > 0 && hp < 100) hp ++;
					this.setFoodGathered(this.getFoodGathered()+1);
					if (food.getUnits() == 0) return 1;
			}
		}
		return 0;
	}
	public void move() {
		if (!lives()) return;
		DetermineMotion();
		
		tmp_time7++;
		if (tmp_time7 > TIME_TO_AGE) {
			age+=0.1;
			tmp_time7 = 0;
		}
				
		Point2D p = this.getPosition();
		Random generator = new Random();

		// !!! DECISION !!!
		if (this.getNeuralNetwork().getLastOutputs() == null) {
			setDirection(generator.nextDouble() * 360);
			//System.out.println(id + ": random");
		}
		else {
			if (this.getNeuralNetwork().getLastOutputs()[0] < 0 || this.getNeuralNetwork().getLastOutputs()[0] > 10000)

			setDirection(360 * (1 - (this.getNeuralNetwork().getLastOutputs()[0] )));
			//System.out.println(id + ": neuronka");
		}
		
		if (isDragged()) return;
		double speed_x = speed * Math.cos((getDirection()-90)/(180/Math.PI));
		double speed_y = speed * Math.sin((getDirection()-90)/(180/Math.PI));


		/*
		if (p.getY() + speed_y  > getMax().getY()) {
			p.setLocation(p.getX(), 0);
		}
		if (p.getX() + speed_x  > getMax().getX()) {
			p.setLocation(0, p.getY());
		}		
		if (p.getY() + speed_y + getHeight() + 7 < 0) {
			p.setLocation(p.getX(), getMax().getY() - 7);
		}		
		if (p.getX() + speed_x < 0) {
			p.setLocation(getMax().getX() - 7,  p.getY());
		}
			*/	
		
		boolean distanceMove = true;
		if (p.getY() + speed_y + getHeight() + 7 < getMax().getY() && p.getY() + speed_y - getHeight() - 7 > 0) {
			p.setLocation(p.getX(), p.getY() + speed_y);
		}
		else {
			distanceMove = false;
		}
		if (p.getX() + speed_x + getWidth() + 1 < getMax().getX() &&  p.getX() + speed_x - getWidth() - 1 > 0) {
			p.setLocation(p.getX() + speed_x, p.getY());
		}
		else {
			distanceMove = false;
		}
		if (distanceMove)
			this.setDistanceMoved(getDistanceMoved() + 1);
		//System.out.println(getDistanceMoved());
				
	}
	public void drawShape(Graphics2D g) {
		drawShape(g,getPosition());
		// selected circle
		
		if (isSelected()) {
			g.setColor(Color.RED);
			int xCenter = (int)this.getPosition().getX() + getWidth()/2 - 1;
			int yCenter = (int)this.getPosition().getY() + getHeight()/2 - 2;
			int r = CIRCLE_RADIUS;
			int x = xCenter-r;
			int y = yCenter-r;
			
			g.drawOval(x,y, r*2, r*2);
			g.drawOval(x - 1,y - 1, (r+1)*2, (r+1)*2);
			//g.drawOval((int)this.getPosition().getX() + 10, (int)this.getPosition().getY()+10, 31, 31);
		}
	}
	public void drawShape(Graphics2D g, Point2D p) {
		
		float greenHPBar, redHPbar, foodBar;
		//int alignID = 0;
		//g.drawRect((int)p.getX(), (int)p.getY(), (int)getWidth(), (int)getHeight());	
		// body
		
		
		int animNumber = (int)((getDirection())/10);
		if (animNumber == 36) animNumber = 0;
		g.drawImage(anim.getImages()[animNumber],(int)p.getX(),(int)p.getY(),null);
		
		
		//g.rotate(0);
		
		switch(getRace()) {
			case 0: g.setColor(Color.BLACK); break;
			case 1: g.setColor(Color.BLUE); break;
			case 2: g.setColor(Color.RED); break;
			case 3: g.setColor(Color.GREEN); break;
			case 4: g.setColor(Color.GRAY); break;
		}
		
		g.fillOval((int)p.getX() + getWidth()/2 - signRadius, (int)p.getY() + getHeight()/2 - signRadius , signRadius*2, signRadius*2);
		
		/*int xCenter = (int)this.getPosition().getX() + 20/2;
		int yCenter = (int)this.getPosition().getY() + 20/2 - 2;
		int r = 15;
		int x = xCenter-r;
		int y = yCenter-r;		
		
		g.drawOval(x,y, r*2, r*2);
		*/
		
		/*
		g.drawRect((int)p.getX(), (int)p.getY(), 20, 20);
		g.drawRect((int)p.getX() + 1, (int)p.getY() + 1, 20 - 2, 20 - 2);
		g.drawRect((int)p.getX() + 2, (int)p.getY() + 2, 20 - 4, 20 - 4);
		*/
		
		// id
		g.setColor(Color.BLACK);
		int alignID = 0;
		if (id > 0 && id < 10)
			alignID = 7; 
		if (id >= 10 && id < 100)
			alignID = 3;
			 
		g.drawString(Integer.toString(id), (float)(int)p.getX() + alignID - 20, (float)(int)p.getY() + 15);		
		
		// health bar
		
		greenHPBar = (int)(hp * 20) / 100;
		redHPbar = 20 - greenHPBar;
		 
		g.setColor(Color.BLUE);
		if ((int)greenHPBar > 0)  {
			g.drawRect((int)p.getX() + 7, (int)p.getY() - 4, (int)greenHPBar, 2);
			g.fillRect((int)p.getX() + 7, (int)p.getY() - 4, (int)greenHPBar, 2);
		}
		else {
			g.setColor(Color.RED);
			g.drawRect((int)p.getX() + 7, (int)p.getY() - 4, 1, 2);
		}
		g.setColor(Color.RED);
		if ((int)redHPbar > 0) {
			g.drawRect((int)p.getX() + (int)greenHPBar + 8, (int)p.getY() - 4, (int)redHPbar - 1, 2);
			g.fillRect((int)p.getX() + (int)greenHPBar + 8, (int)p.getY() - 4, (int)redHPbar - 1, 2);
		}
		
		// food bar
		
		foodBar = (int)(food * 20) / 100;
		if (foodBar > 20)  {
			g.setColor(new Color(44,148,70));
			foodBar = 20;
		}
		else {
			g.setColor(new Color(44,148,70));
		}
			
		if (foodBar > 0) {
			g.drawRect((int)p.getX() + 7, (int)p.getY() - 8, (int)foodBar, 2);
			g.fillRect((int)p.getX() + 7, (int)p.getY() - 8, (int)foodBar, 2);
		}
		
		// carrying food
		
		// x - sin(angle)
		// y - 2* sin(angle/2)
		
		if (isCarryingFood()) {
			g.setColor(new Color(50,156,83));
			int x_null = (int)p.getX() + getWidth()/2 - 4;
			int y_null = (int)p.getY() - 6;
			g.fillOval( (int) (x_null + Math.sin(getDirection()/(180/Math.PI)) * getWidth()/2), (int) (y_null + 2*Math.sin((getDirection()/2)/(180/Math.PI)) * getHeight()/2), foodRadius * 2, foodRadius * 2);
		}
	
		
		g.setColor(Color.BLACK);
		
		
		
	}
	public void setHp(double hp) {
		this.hp = hp;
		if (this.hp > 100) this.hp = 100;
		if (this.hp < 0) this.hp = 0;
	}
	public double getHp() {
		return hp;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setDirection(double direction) {
		this.direction = direction;
	}
	public double getDirection() {
		return direction;
	}
	
	public int getRace() {
		return this.race;
	}
	
	public void setRace(int race) {
		this.race = race;
	}
	public void testMeet(Livable livable) {
		// ak bot nezije, nerandi :)
		if (hp <= 0) return;
		// nas bot sa zasadne nestretava s mrtvymi botmi
		if (!livable.lives()) return;
		
		int x = (int)getPosition().getX();
		int y = (int)getPosition().getY();
		int livable_x = (int)livable.getPosition().getX();
		int livable_y = (int)livable.getPosition().getY();
		
		if (x >= livable_x - getWidth() && x <= livable_x + getHeight() && y >= livable_y - getWidth() && y < livable_y + 2*getHeight()) {
			if (livable.getRace() != this.getRace()) {
				tmp_time2++;
				if (tmp_time2 > TIME_TO_TAKE_ONE_DMG) {
					tmp_time2 = 0;			
					if ( dying ) this.hp -= livable.getDmg();
					this.setDmgTaken(this.getDmgTaken()+livable.getDmg());
					//System.out.println("bijem sa - AU (" + id + ")") ;
				}
			}
			else {
				
				tmp_time4++;

				// !!! DECISION !!!

				if (tmp_time4  > TIME_TO_GATHER_ONE_UNIT_OF_FOOD && livable.getFood() < 20 && getFood() > 65) {
					tmp_time4 = 0;	
					livable.recieveFood(1);
					this.setFood(getFood()-1);
					//System.out.println("Davam jedlo (" + id + ")");
				}
			}			
		}
		double tmp = getNeuralNetwork().getFitness();
		if (tmp > bestFitness) {
			bestFitness = tmp; 
		}
	}
	public void setDmg(int dmg) {
		this.dmg = dmg;
	}
	public int getDmg() {
		return dmg;
	}
	public boolean lives() {
		return getHp() > 0;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getSpeed() {
		return speed;
	}
	public double getFood() {
		return this.food;
	}
	public void setFood(double food) {
		this.food = food;
	}
	public void recieveFood(int amount) {
		this.setFood(getFood() + amount);
		this.setHp(getHp() + amount);
		if (getHp() > 100) setHp(100);
		//System.out.println("Prijimam jedlo (" + id + ")");
	}
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public void drawShapeInfo(Graphics2D g, Point p) {
		drawShape(g,p);
	}
	public ArrayList<String> getInfo() {
		
		ArrayList<String> ret = new ArrayList<String>();
		ret.add("HP: " + Stringutil.formatDouble(getHp(), 2) + " %");
		ret.add("Jedlo: " + Stringutil.formatDouble(getFood(), 2));
		ret.add("Typ: " + getRace());
		ret.add("Uhol vektora pohybu : " + Stringutil.formatDouble(getDirection(), 1) + "º");
		//if (this.getParentID1() != 0)  ret.add("Rodicia: " + this.getParentID1() + "," + this.getParentID2());
		ret.add("Rychlost: " + Stringutil.formatDouble(getSpeed(), 2));
		ret.add("ID: " + getID());
		ret.add("Nesie jedlo: " + (isCarryingFood() ? ("áno" +  "(" + getFoodCarrying() + ")" ): "nie"));
		ret.add("Aktuálna fitnees: " + Stringutil.formatDouble(Init.getFitness(this), 2));
		ret.add("Prenesené jedlo do hniezda: " + getFoodInstancesCarried());
		return ret;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setExchanging(boolean exchanging) {
		this.exchanging = exchanging;
	}
	public boolean isExchanging() {
		return exchanging;
	}
	public boolean isNear(Drawable drawable) {
		//System.out.println(id + ": " + this.getPosition().distance(drawable.getPosition()));
		if (this.getPosition().distance(drawable.getPosition()) < 50)
			return true;
		return false;
	}
	public ArrayList<Double> getSensorInfo() {
		ArrayList<Double> ret = new ArrayList<Double>();
		ret.add(getPosition().getX());
		ret.add(getPosition().getY());
		ret.add((double)getRace());
		ret.add((double)getId());
		ret.add((double)getHp());
		return ret;
	}

	public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
	}

	public NeuralNetwork getNeuralNetwork() {
		return neuralNetwork;
	}

	public void setDmgTaken(double dmgTaken) {
		this.dmgTaken = dmgTaken;
	}

	public double getDmgTaken() {
		return dmgTaken;
	}

	public void setDistanceMoved(double distanceMoved) {
		this.distanceMoved = distanceMoved;
	}

	public double getDistanceMoved() {
		return distanceMoved;
	}

	public void setFoodGathered(double foodGathered) {
		this.foodGathered = foodGathered;
	}

	public double getFoodGathered() {
		return foodGathered;
	}

	public boolean show() {
		return lives();
	}

	@Override
	public boolean showInfo() {
		return lives();
	}

	@Override
	public boolean canReproduce(Bot bot) {
		
		int x = (int)getPosition().getX();
		int y = (int)getPosition().getY();
		int bot_x = (int)bot.getPosition().getX();
		int bot_y = (int)bot.getPosition().getY();
		
		if (x >= bot_x - getWidth() - 5 && x <= bot_x + getHeight() + 5 && y >= bot_y - getWidth() - 5 && y < bot_y + getHeight() + 5 && bot.getHp() > 99 && bot.getFood() > 80 && getHp() > 99 && getFood() > 80) {
			return true;
		}
		return false;
	}
	public ArrayList<Bot> reproduce(Livable liv) {
		
		return null;
	}

	public void setParentID1(int parentID1) {
		this.parentID1 = parentID1;
	}

	public int getParentID1() {
		return parentID1;
	}

	public void setParentID2(int parentID2) {
		this.parentID2 = parentID2;
	}

	public int getParentID2() {
		return parentID2;
	}

	public Animation getAnim() {
		return anim;
	}

	public void setAnim(Animation anim) {
		this.anim = anim;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}
	public int getID() {
		return this.id;
	}

	public void setDying(boolean dying) {
		this.dying = dying;
	}

	public boolean isDying() {
		return dying;
	}

	public boolean isDragged() {
		return dragged;
	}

	public void setDragged(boolean dragged) {
		this.dragged = dragged;
	}
	public boolean canBeDragged() {
		return lives();
	}

	public void setAge(double age) {
		this.age = age;
	}

	public double getAge() {
		return age;
	}

	public void setBirthPoint(Point2D birthPoint) {
		this.birthPoint = birthPoint;
	}

	public Point2D getBirthPoint() {
		return birthPoint;
	}

	public void setCarryingFood(boolean carryingFood) {
		this.carryingFood = carryingFood;
		foodInstancesFound++;
	}

	public boolean isCarryingFood() {
		return carryingFood;
	}
	public double getBestFitness() {
		return bestFitness;
	}

	public void setFoodCarrying(int foodCarrying) {
		this.foodCarrying = foodCarrying;
	}

	public int getFoodCarrying() {
		return foodCarrying;
	}

	public void setFoodCarried(int foodCarried) {
		this.foodCarried = foodCarried;
		this.foodInstancesCarried++;
	}

	public int getFoodCarried() {
		return foodCarried;
	}

	public void setNewPop(boolean newPop) {
		this.newPop = newPop;
	}

	public boolean isNewPop() {
		return newPop;
	}

	public int getFoodInstancesFound() {
		return foodInstancesFound;
	}

	public void setFoodInstancesFound(int foodInstancesFound) {
		this.foodInstancesFound = foodInstancesFound;
	}

	public int getFoodInstancesCarried() {
		return foodInstancesCarried;
	}

	public void setFoodInstancesCarried(int foodInstancesCarried) {
		this.foodInstancesCarried = foodInstancesCarried;
	}

	public void setSensorSystem(BotSensorSystem sensorSystem) {
		this.sensorSystem = sensorSystem;
	}

	public BotSensorSystem getSensorSystem() {
		return sensorSystem;
	}


}
