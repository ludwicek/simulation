package graphic;

import java.awt.Graphics;
import java.awt.geom.Point2D;

public class ConnectionLine {
	private Point2D to;
	private boolean okLeft;
	private boolean okRight;
	private boolean visible;
	private double wage;
	public  ConnectionLine(Point2D to) {
		setTo(to);
		setVisible(false);
	}
	public void paint(Graphics g, Point2D from) {
		g.drawLine((int)from.getX(), (int)from.getY(), (int)to.getX(), (int)to.getY());
	}
	public Point2D getTo() {
		return to;
	}
	public void setTo(Point2D to) {
		this.to = to;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setOkLeft(boolean okLeft) {
		this.okLeft = okLeft;
	}
	public boolean isOkLeft() {
		return okLeft;
	}
	public void setOkRight(boolean okRight) {
		this.okRight = okRight;
	}
	public boolean isOkRight() {
		return okRight;
	}
	public void setWage(double wage) {
		this.wage = wage;
	}
	public double getWage() {
		return wage;
	}
}
