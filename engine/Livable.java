package engine;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface Livable extends Drawable {
	public void testHunger();
	public int eat(Consumable food);
	public void recieveFood(int amount);
	public void testMeet(Livable livable);
	public Point2D getPosition();
	public int getRace();
	public int getDmg();
	public double getFood();
	public boolean lives();
	public ArrayList<Bot> reproduce(Livable liv);
	public boolean canReproduce(Bot bot);
	//public void setRace(int race);
}
