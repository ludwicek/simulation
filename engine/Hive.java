package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import simulation.Animation;


public class Hive extends Shape implements Infoable {

	private static final long serialVersionUID = 9217899892321703415L;
	private boolean selected = false;
	private final int height = 30;
	private final int width = 30;
	private int bots = 0;
	private int type;
	private int foodGathered;
	public void setSelected(boolean selected) {
		this.selected = selected;
		
	}
	public void drawShape(Graphics2D g) {
		drawShape(g,this.getPosition());
	}
	public void drawShape(Graphics2D g, Point2D p) {
		switch(getType()) {
		case 1: g.setColor(Color.BLUE); break;
		case 2: g.setColor(Color.RED); break;
		case 3: g.setColor(Color.GREEN); break;
		case 4: g.setColor(Color.GRAY); break;
	}
		g.fillRect((int)p.getX(),(int)p.getY(), this.width, this.height);
		g.setColor(Color.BLACK);
	}
	public Hive() {
		this(new Point2D.Double(0,0));
		setFoodGathered(0);
	}
	public Hive (Point2D p) {
		this.setPosition(p);
	}
	public ArrayList<Double> getSensorInfo() {
		ArrayList<Double> ret = new ArrayList<Double>();
		ret.add(getPosition().getX());
		ret.add(getPosition().getY());
		ret.add((double)getType());
		ret.add((double)-2);
		ret.add(0.0);
		return ret;
	}
	
	public boolean show() {
		return true;
	}

	public void setAnim(Animation anim) {
	}

	public ArrayList<String> getInfo() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add("Typ: " + getType());
		ret.add("Prijateho jedla: " + getFoodGathered());
		return ret;
	}

	public void drawShapeInfo(Graphics2D g, Point p) {
		drawShape(g,p);
	}

	public boolean showInfo() {
		return true;
	}

	public int getID() {
		return -2;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setBots(int bots) {
		this.bots = bots;
	}
	public int getBots() {
		return bots;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public void setFoodGathered(int foodGathered) {
		this.foodGathered = foodGathered;
	}
	public int getFoodGathered() {
		return foodGathered;
	}
	public int getWidth() {
		return width;
	}

}
