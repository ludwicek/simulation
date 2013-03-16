package engine;

import init.Init;

import javax.swing.*;
import java.awt.*;
//import java.awt.geom.Point2D;
//import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
public class DrawArea extends JPanel {
	private static final long serialVersionUID = 1L;
	private Point mousePosition = new Point(0,0);
	private MainWindow frame;
	private BotPanel botpanel;
	private Init init;
	private DrawAreaListener listener = new DrawAreaListener(this);
	private CopyOnWriteArrayList<Drawable> shapes = new CopyOnWriteArrayList<Drawable>();
	private boolean adding;
	private int addType;
	private double scale = 1;
	private boolean showingBotPanel;
    public DrawArea(MainWindow frame) {
		setFrame(frame);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		this.addMouseWheelListener(listener);
		this.setFocusable(true);
		this.addKeyListener(listener);
	}
	public void paintComponent(Graphics g) {
		//System.out.println(getInit());
		//System.out.println(shapes.size());
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D)g;
		graphics.scale(getScale(),getScale());
		//graphics.translate((-xMin + xMax / scale), (-yMax + yMin / scale));


		//graphics.drawRect(1.7, 5.8, 54.7, 90.1);
		if (shapes.isEmpty()) return;
        for (Drawable shape : shapes) {
        	if (shape.show())
        		shape.drawShape(graphics);
        }
       // this.update(graphics);
	}
	public void drawCircle(Graphics g, int xCenter, int yCenter, int r) {
		g.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
	}
	
	public Point getMousePosition() {
		return mousePosition;
	}
	public void setMousePosition(Point mousePosition) {
		this.mousePosition = mousePosition;
	}
	public MainWindow getFrame() {
		return frame;
	}
	public void setFrame(MainWindow frame) {
		this.frame = frame;
	}
	public CopyOnWriteArrayList<Drawable> getShapes() {
		return shapes;
	}
	public void setShapes(CopyOnWriteArrayList<Drawable> shapes) {
		this.shapes = shapes;
	}
	public void setListener(DrawAreaListener listener) {
		this.listener = listener;
	}
	public DrawAreaListener getListener() {
		return listener;
	}
	public void showBotInfo(int id) {
		botpanel = new BotPanel(frame.getInit().getBot(id));
		frame.setEnabled(false);
		frame.setRunning(false);
		//pictureBox.pict = null;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		botpanel.setClicked(id);
		botpanel.setInit(getInit());
		botpanel.setMain(frame);
		botpanel.setResizable(false);
		botpanel.setLocation((screen.width - botpanel.getWidth()) / 2, (screen.height - botpanel.getHeight()) / 2);
		botpanel.init();
		botpanel.setVisible(true); 
	
	}
	public void setInit(Init init) {
		this.init = init;
	}
	public Init getInit() {
		return init;
	}
	public void setAdding(boolean adding) {
		this.adding = adding;
	}
	public boolean isAdding() {
		return adding;
	}
	public void setAddType(int addType) {
		this.addType = addType;
	}
	public int getAddType() {
		return addType;
	}
	public void setScale(double scale) {
		this.scale = scale;
	}
	public double getScale() {
		return scale;
	}
	public void zoomIn() {
		setScale(getScale() + 0.05);
	}
	public void zoomOut() {
		if (getScale() > 0.40)
			setScale(getScale() - 0.05);
	}
	public void setShowingBotPanel(boolean showingBotPanel) {
		this.showingBotPanel = showingBotPanel;
	}
	public boolean isShowingBotPanel() {
		return showingBotPanel;
	}

}
