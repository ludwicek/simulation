package engine;

import graphic.Graphic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BotPanelDrawArea extends JPanel {
	private static final long serialVersionUID = 3067927519638489908L;
	private CopyOnWriteArrayList<Graphic> shapes = new CopyOnWriteArrayList<Graphic>();
	private BotPanel botpanel;
	private BotPanelListener listener = new BotPanelListener(this);
	private BufferedImage botImage;
	public BotPanelDrawArea(BotPanel panel) {
		setBotpanel(panel);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		try {
			botImage = ImageIO.read(new File("images/bot0.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void paint(Graphics g) {
    super.paint(g);
     
    g.drawImage(botImage,380,230,null);
	for (Graphic shape : shapes) {
		shape.drawShape((Graphics2D)g);
	}
    }
	public CopyOnWriteArrayList<Graphic> getShapes() {
		return shapes;
	}
	public void setShapes(CopyOnWriteArrayList<Graphic> shapes) {
		this.shapes = shapes;
	}
	public void setBotpanel(BotPanel botpanel) {
		this.botpanel = botpanel;
	}
	public BotPanel getBotpanel() {
		return botpanel;
	}



}
