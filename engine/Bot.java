package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.*;

import nn.NeuralNetwork;

public class Bot extends Shape implements Movable, Livable, Cloneable, Infoable {
	private static final int TIME_TO_EAT_ONE_UNIT_OF_FOOD = 50;
	private static final int TIME_TO_GATHER_ONE_UNIT_OF_FOOD = 2;
	private static final int TIME_TO_TAKE_ONE_DMG = 3;
	//private static final int TIME_TO_EXCHANGE_FOOD_FOR_HP = 3;
	private static final int TIME_TO_CHANGE_DIRECTION = 20;
	private static final int TIME_TO_MEASURE_FITNESS = 5;
	private static final int BOT_DMG = 4;
	private static final double BOT_SPEED = 0.5;
	private static final double MAX_SPEED = 1.5;

	private NeuralNetwork neuralNetwork;
	private double dmgTaken;
	private double distanceMoved;
	private double foodGathered;
	
	
	private int hp = 100;
	private boolean selected;
	private boolean exchanging;
	private double speed;
	private int id;
	private int food;
	private int parentID1;
	private int parentID2;
	private int tmp_time = 0;
	private int tmp_time1 = 0;
	private int tmp_time2 = 0;
	private int tmp_time3 = 0;
	private int tmp_time4 = 0;
	//private int tmp_time5 = 0;
	private int tmp_time6 = 0;
	// smer pohybu (uhol)
	private double direction;
	// rasa (farba bota - odlisenie od ostatnych)
	private int race;
	// sila utoku
	private int dmg;
	
	public Point2D getPosition() {
		return super.getPosition();
	}
	
	public Bot(Point2D p, int race) {
		this.setPosition(p);
		this.setRace(race);
		this.setDmg(BOT_DMG); 
	}
	public Bot() {
		this(new Point(0,0), 0);
	}
	public void DetermineMotion() {
		if (hp <= 0) {
			setSpeed(0);
		}
		else {
			if (this.getNeuralNetwork().getLastOutputs() == null) {
				setSpeed(BOT_SPEED);
				//System.out.println(id + ": random");
			}
			else {
				setSpeed((this.getNeuralNetwork().getLastOutputs()[1] + 1) * MAX_SPEED/2);
				//System.out.println(id + ": neuronka");
			}			
			
		}
	}
	public void testHunger() {
		if (hp < 0) {
			hp = 0;
		}
		if (hp == 0) 
			return;
		if (food < 0) {
			food = 0;
		}
		
		tmp_time++;
		if (tmp_time > TIME_TO_EAT_ONE_UNIT_OF_FOOD && hp > 0) {
			tmp_time = 0;
			if (food == 0) { 
				hp -= Math.round(getSpeed()*2);	
				//System.out.println("AU (" + hp + ")");
			}
			else { 
				food -= Math.round(getSpeed()*2);;
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
			System.out.println(id + " dead");
		}
		
		
	}
	public int eat(Consumable food) {
		
		int food_x = (int)food.getPosition().getX();
		int food_y = (int)food.getPosition().getY();
		int x = (int)getPosition().getX();
		int y = (int)getPosition().getY();
		if (x >= food_x - 20 && x <= food_x + 47 && y >= food_y - 27 && y < food_y + 32) {
			tmp_time1++;
			if (tmp_time1 > TIME_TO_GATHER_ONE_UNIT_OF_FOOD && hp > 0 && this.food < 100) {
				tmp_time1 = 0;
					this.food++;
					food.setUnits(food.getUnits() - 1);
					if (this.food > 0 && hp < 100) hp ++;
					this.setFoodGathered(this.getFoodGathered()+1);
					if (food.getUnits() == 0) return 1;
			}
		//	System.out.println("zeriem (" + id + ")");
		//	System.out.println("mam vela jedla (" + this.food + ")");
		}
		return 0;
		
	}
	public void move() {
		if (!lives()) return;
		DetermineMotion();
				
		tmp_time6++;
		if (tmp_time6 > TIME_TO_MEASURE_FITNESS) {
			tmp_time6 = 0;
			//System.out.println("TERAZ");
			this.getNeuralNetwork().setFitness(100 * this.getFoodGathered() - 150 * this.getDmgTaken() - this.getDistanceMoved());
		}
		
		Point2D p = this.getPosition();
		Random generator = new Random();
		tmp_time3++;
		if (tmp_time3 > TIME_TO_CHANGE_DIRECTION) {

			// !!! DECISION !!!
			if (this.getNeuralNetwork().getLastOutputs() == null) {
				setDirection(generator.nextDouble() * 360);
				//System.out.println(id + ": random");
			}
			else {
				setDirection(360 * (1 - this.getNeuralNetwork().getLastOutputs()[0]));
				//System.out.println(id + ": neuronka");
			}
			tmp_time3 = 0;
		}
		
		double speed_x = speed * Math.cos(getDirection());
		double speed_y = speed * Math.sin(getDirection());

		if (p.getY() + speed_y + 27 > getMax().getY() || p.getX() + speed_x + 21 > getMax().getX() || p.getY() + speed_y - 27 < 0 || p.getX() + speed_x - 21 < 0) {
			/*
			int change = 0;
			if (p.getY() + 27 > getMax().getY()) {
				change = 180;
			}
			if (p.getX() + 21 > getMax().getX()) {
				change = 90;
			}
			if (p.getY() - 27 < 0) {
				change = 90;
			}
			if (p.getX() - 21 < 0) {
				change = 270;
			}
			if (p.getY() - 27 < 0 && p.getX() - 21 < 0) {
				change = 45;
			}
			*/
			
			//this.setDirection(change);
			//this.setDirection(generator.nextDouble());
			p.setLocation(200,200);
			//this.setDistanceMoved(getDistanceMoved() + speed );
		}
		else {
			p.setLocation(p.getX() + speed_x, p.getY() + speed_y);
			this.setDistanceMoved(getDistanceMoved() + speed );
		}
		//System.out.println("after: " + p);
	}
	public void drawShape(Graphics2D g) {
		drawShape(g,getPosition());
		// selected circle
		
		if (isSelected()) {
			g.setColor(Color.RED);
			int xCenter = (int)this.getPosition().getX() + 20/2;
			int yCenter = (int)this.getPosition().getY() + 20/2 - 2;
			int r = 25;
			int x = xCenter-r;
			int y = yCenter-r;
			
			g.drawOval(x,y, r*2, r*2);
			g.drawOval(x - 1,y - 1, (r+1)*2, (r+1)*2);
			//g.drawOval((int)this.getPosition().getX() + 10, (int)this.getPosition().getY()+10, 31, 31);
		}
	}
	public void drawShape(Graphics2D g, Point2D p) {
		
		float greenHPBar, redHPbar, foodBar;
		int alignID = 0;
			
		// body
		switch(getRace()) {
			case 0: g.setColor(Color.BLACK); break;
			case 1: g.setColor(Color.BLUE); break;
			case 2: g.setColor(Color.RED); break;
		
		}
		
		g.drawRect((int)p.getX(), (int)p.getY(), 20, 20);
		g.drawRect((int)p.getX() + 1, (int)p.getY() + 1, 20 - 2, 20 - 2);
		g.drawRect((int)p.getX() + 2, (int)p.getY() + 2, 20 - 4, 20 - 4);
		
		// id
		g.setColor(Color.BLACK);

		if (id > 0 && id < 10)
			alignID = 7; 
		if (id >= 10 && id < 100)
			alignID = 3; 
		g.drawString(Integer.toString(id), (float)(int)p.getX() + alignID, (float)(int)p.getY() + 15);		
		
		// health bar
		
		greenHPBar = (hp * 20) / 100;
		redHPbar = 20 - greenHPBar;
		 
		g.setColor(Color.BLUE);
		if ((int)greenHPBar > 0)  {
			g.drawRect((int)p.getX(), (int)p.getY() - 4, (int)greenHPBar, 2);
			g.fillRect((int)p.getX(), (int)p.getY() - 4, (int)greenHPBar, 2);
		}
		else {
			g.setColor(Color.RED);
			g.drawRect((int)p.getX(), (int)p.getY() - 4, 1, 2);
		}
		g.setColor(Color.RED);
		if ((int)redHPbar > 0) {
			g.drawRect((int)p.getX() + (int)greenHPBar + 1, (int)p.getY() - 4, (int)redHPbar - 1, 2);
			g.fillRect((int)p.getX() + (int)greenHPBar + 1, (int)p.getY() - 4, (int)redHPbar - 1, 2);
		}
		
		// food bar
		
		foodBar = (food * 20) / 100;
		if (foodBar > 20)  {
			g.setColor(new Color(44,148,70));
			foodBar = 20;
		}
		else {
			g.setColor(new Color(44,148,70));
		}
			
		if (foodBar > 0) {
			g.drawRect((int)p.getX(), (int)p.getY() - 8, (int)foodBar, 2);
			g.fillRect((int)p.getX(), (int)p.getY() - 8, (int)foodBar, 2);
		}
	
		
		g.setColor(Color.BLACK);
		
		
		
	}
	public void setHp(int hp) {
		this.hp = hp;
		if (this.hp > 100) this.hp = 100;
		if (this.hp < 0) this.hp = 0;
	}
	public int getHp() {
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
		
		if (x >= livable_x - 20 && x <= livable_x + 20 && y >= livable_y -20 && y < livable_y + 42) {
			if (livable.getRace() != this.getRace()) {
				tmp_time2++;
				if (tmp_time2 > TIME_TO_TAKE_ONE_DMG) {
					tmp_time2 = 0;			
					this.hp -= livable.getDmg();
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
				//if (livable.canReproduce())
			}			
		}
		if (hp <= 0) {
			System.out.println(id + " dead");
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
	public int getFood() {
		return this.food;
	}
	public void setFood(int food) {
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
		ret.add("HP: " + getHp() + " %");
		ret.add("Jedlo: " + getFood() + " jednotiek");
		ret.add("Typ: " + getRace());
		ret.add("Uhol vektora pohybu : " + Math.round(getDirection()) + "º");
		if (this.getParentID1() != 0)  ret.add("Rodicia: " + this.getParentID1() + "," + this.getParentID2());
		ret.add("Rychlost: " + this.getSpeed());
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
		if (this.getPosition().distance(drawable.getPosition()) < 78.2)
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
		
		if (x >= bot_x - 15 && x <= bot_x + 15 && y >= bot_y - 15 && y < bot_y + 35 && bot.getHp() > 99 && bot.getFood() > 80 && getHp() > 99 && getFood() > 80) {
			System.out.println("Reprodukujem sa");
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

}
