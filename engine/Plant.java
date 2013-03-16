package engine;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Plant extends Food implements Drawable, Infoable {
	private int units;
	@SuppressWarnings("unused")
	private boolean selected = false;
	public Plant(Point point) {
		try {
			setImage(ImageIO.read(new File("images/plant.gif")));
		}
		catch(IOException e) {
			System.out.println("Vynimka pri nacitavani suboru food.png");
		}
		setPosition(point);
	}
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public Plant() {
		this(new Point(20,20));
	}
	public void drawShape(Graphics2D g) {
		drawShape(g, new Point2D.Double((int)getPosition().getX(),(int)getPosition().getY()));
	}
	public void drawShape(Graphics2D g, Point2D point) {
		g.drawImage(getImage(),(int)point.getX(),(int)point.getY(),null);
	}
	public void Consume() {
		
	}
	public ArrayList<Double> getSensorInfo() {
		ArrayList<Double> ret = new ArrayList<Double>();
		ret.add(getPosition().getX());
		ret.add(getPosition().getY());
		ret.add(255.0);
		ret.add(0.0);
		ret.add(0.0);
		return ret;
	}
	public boolean show() {
		return !isConsumed();
	}
	public boolean isConsumed() {
		return this.units == 0;
	}
	public void setUnits(int units) {
		/*try {
			if (units > 0 && units < 10)
				setImage(ImageIO.read(new File("images/plant20.gif")));
			if (units >= 10 && units < 20)
				setImage(ImageIO.read(new File("images/plant40.gif")));
			if (units >= 20 && units < 30)
				setImage(ImageIO.read(new File("images/plant60.gif")));
			if (units >= 30 && units < 40)
				setImage(ImageIO.read(new File("images/plant80.gif")));
			if (units >= 40)
				setImage(ImageIO.read(new File("images/plant.gif")));
				
		}
		catch(IOException e) {
			System.out.println("Vynimka pri nacitavani suboru plantxx.gif");
		}
		*/
		this.units = units;
	}
	public int getUnits() {
		return units;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public ArrayList<String> getInfo() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add("Jednotiek: " + getUnits());
		return ret;
	}
	public void drawShapeInfo(Graphics2D g, Point p) {
		drawShape(g,p);
		
	}
	public boolean showInfo() {
		return !isConsumed();
	}
	public Point2D getPosition() {
		return super.getPosition();
	}	

}
