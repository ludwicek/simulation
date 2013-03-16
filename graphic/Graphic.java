package graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public abstract class Graphic {
	private Color color;
	protected Point2D position;
	protected int radius;
	protected boolean selected;
	protected boolean hovered;
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	public void drawShape(Graphics2D g) {
		
	}
	public Point2D getPosition() {
		return position;
	}
	public void setPosition(Point2D position) {
		this.position = position;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public boolean isHovered() {
		return hovered;
	}
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public void setLineSelected(boolean selected) {
		
	}
	public ArrayList<ConnectionLine> getLines() {
		return null;
	}
	public ArrayList<ConnectionLine> getLinesBefore() {
		return null;
	}
	public double getWageShown() {
		return 0;
	}
	public void setLast(boolean last) {
	}
	public boolean isLast() {
		return false;
	}
}
