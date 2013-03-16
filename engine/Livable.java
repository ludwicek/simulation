package engine;

import java.util.ArrayList;

public interface Livable extends Movable {
	public void testHunger();
	public int eat(Consumable food);
	public void recieveFood(int amount);
	public void testMeet(Livable livable);
	public int getRace();
	public int getDmg();
	public int getFood();
	public boolean lives();
	public ArrayList<Bot> reproduce(Livable liv);
	public boolean canReproduce(Bot bot);
	//public void setRace(int race);
}
