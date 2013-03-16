package engine;

import init.Init;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	private static final long serialVersionUID = WIDTH;
	private JFrame frame;
	private Infoable infoable = null;
	private Init init;
	public InfoPanel(Init init) {
		this.init = init;
		this.setPreferredSize(new Dimension(10,270));
	}
	public void paintComponent(Graphics g) {
		int i = 0;
		super.paintComponent(g);
		if (infoable == null) {
			System.out.println("ale no");
			this.infoable = null;
			this.setVisible(false);
			return;
		}
		
		ArrayList<String> info = infoable.getInfo();
		infoable.drawShapeInfo((Graphics2D) g, new Point(80,35));
		g.drawLine(5,73,195,73);
		for (String tmp : info) {
			g.drawString(tmp, 10, 92 + 20 * (i ++) );			
		}
		//repaint();
	}
	
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}
	
	public void drawInfo(Infoable infoable) throws CloneNotSupportedException {
		this.infoable = infoable;
		
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setInit(Init init) {
		this.init = init;
	}
	public Init getInit() {
		return init;
	}
}
