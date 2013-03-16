package engine;

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
	private Infoable infoable;
	public InfoPanel() {
		this.setPreferredSize(new Dimension(10,190));
	}
	public void paintComponent(Graphics g) {
		int i = 0;
		super.paintComponent(g);
		ArrayList<String> info = infoable.getInfo();
		infoable.drawShapeInfo((Graphics2D) g, new Point(80,20));
		g.drawLine(2,65,196,65);
		for (String tmp : info) {
			g.drawString(tmp, 10, 82 + 20 * (i ++) );			
		}
		//repaint();
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
}
