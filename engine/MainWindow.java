package engine;
import init.Init;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*; 
import javax.swing.border.Border;

public class MainWindow extends JFrame { 
	private double simSpeed;
	private static final long serialVersionUID = 1L;
	//public static final int APPLICATION_WIDTH = 1400;
	//public static final int APPLICATION_HEIGHT = 800;
	private DrawArea drawArea;
	private NewFrame newFrame;
	private JScrollPane scrollpanel;
	private JScrollPane editorScroll;
	private InfoPanel infoPanel;
	private String load = "";
	private String save = "";
	private boolean running = true;
	private int botid;
	private Init init;
	private JMenuBar menuBar;
	private JButton pauseMenu;
	private JMenuItem saveButton;
	private JMenuItem newSimMenu;
	
	public MainWindow(Init init) { 
		setInit(init);
		infoPanel = new InfoPanel(init);
		setTitle("Simul·cia umelÈho ûivota"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setJMenuBar(createMenuBar());
        
        Container pane = getContentPane(); 
		pane.setLayout(new BorderLayout()); 
		pane.add(createRightMenu(), BorderLayout.EAST); 
		setDrawArea(createDrawArea());
		editorScroll = new JScrollPane(getDrawArea());
		//editorScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//editorScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		editorScroll.getVerticalScrollBar().setPreferredSize (new Dimension(0,0)); 
		editorScroll.getHorizontalScrollBar().setPreferredSize (new Dimension(0,0));
		pane.add(editorScroll, BorderLayout.CENTER); 
		
		this.setPreferredSize(new Dimension(init.appWidth,init.appHeight));
		//pane.add(createButtonPanel(), BorderLayout.SOUTH); 
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage("images/bot0.png");
		setIconImage(img);
		pack();
	}
	public void updateInfo() {
		this.infoPanel.repaint();
	}
	private JComponent createRightMenu() { 
		JPanel menu = new JPanel();
		JPanel menu_1 = new JPanel();
		JPanel menu_2 = new JPanel();
		
		
		Border border_1 = BorderFactory.createTitledBorder("Nastavenia simul·cie");
		Border border_2 = BorderFactory.createTitledBorder("IO");
		Border border_3 = BorderFactory.createTitledBorder("Info bar");
		menu.setLayout(new GridLayout(0,1));
		menu_1.setLayout(new GridLayout(0,1));
		menu_2.setLayout(new GridLayout(0,1));
		infoPanel.setLayout(new GridLayout(0,1));
		
		menu_1.setBorder(border_1);
		menu_1.add(createSimSlider());
		menu_1.add(createSimLabel());
		menu_2.setBorder(border_2);
		infoPanel.setBorder(border_3);
		infoPanel.setVisible(false);
		
		JPanel rightPanel = new JPanel();
		//menu.add(menu_1, BorderLayout.NORTH);
		//menu.add(menu_2, BorderLayout.SOUTH);
		menu.add(infoPanel, BorderLayout.SOUTH);
		rightPanel.setLayout(new BorderLayout()); 
		rightPanel.add(menu, BorderLayout.NORTH);
		rightPanel.setPreferredSize( new Dimension( getInit().rightPanelWidth, 15 ) );
		return rightPanel; 
	} 
	private JSlider createSimSlider() {
		JSlider ret = new JSlider(JSlider.HORIZONTAL,1,30,1);
        ret.setPaintLabels(true);
		ret.setPaintTicks(true);
		ret.setAlignmentY(TOP_ALIGNMENT);
		SlideListener listener = new SlideListener(this);
        ret.addChangeListener(listener);
		return ret;
	}
	private JLabel createSimLabel() {
		JLabel ret = new JLabel("R˝chlosù anim·cie", JLabel.CENTER);
		ret.setAlignmentY(TOP_ALIGNMENT);
		return ret;
		
	}
	private DrawArea createDrawArea() { 
		DrawArea area = new DrawArea(this); 
		//area.setBorder(BorderFactory.createLineBorder(Color.black));

		area.setInit(getInit());
		area.setBackground(new Color(255,255,255));
		area.setPreferredSize(new Dimension(getInit().drawareaWidth, getInit().drawareaHeight));
		area.setLocation(new Point(30,30));
		//area.setLocation(1500,2000);
		area.setAutoscrolls(true);

		return area;
	} 
	private JMenuBar createMenuBar() {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("S˙bor");
        JMenu aboutMenu = new JMenu("O programe");
        pauseMenu = new JButton("ätart/Pauza");
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	setRunning(!isRunning());
            }
        }; 
        pauseMenu.addActionListener(actionListener);
        
        fileMenu.add(getNewButton());
        fileMenu.add(getSaveButton());
        fileMenu.add(getLoadButton());
        fileMenu.add(getExitButton());
        aboutMenu.add(new JMenuItem("O programe"));
        menuBar.add(fileMenu);
        menuBar.add(getAddMenu());
        menuBar.add(getStatsMenu());
        menuBar.add(aboutMenu);
        menuBar.add(pauseMenu);
        
        return menuBar;
	}
	
	private JMenuItem getNewButton() {
		newSimMenu = new JMenuItem("Nov· simul·cia");
		ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	        	newFrame = new NewFrame(init);
	        	newFrame.setVisible(true);
	        	mySetEnabled(false);
	        	
	        	newFrame.setLocation((screen.width - newFrame.getWidth()) / 2, (screen.height - newFrame.getHeight()) / 2);
	        }
	      };
	      newSimMenu.addActionListener(actionListener);
		
		return newSimMenu;
	}
	public void mySetEnabled(boolean set) {
		this.setEnabled(set);
	}
	public JComponent getStatsMenu() {
        JMenu statsMenu = new JMenu("ätatistiky");
		statsMenu.add(getStatsMenuItem1());
		statsMenu.add(getStatsMenuItem3());
		return statsMenu;
	}
	
	public JComponent getAddMenu() {
        JMenu addMenu = new JMenu("Pridaù");
		//addMenu.add(getAddMenuItem1());
		addMenu.add(getAddMenuItem2());
		return addMenu;
	}
	
	public JComponent getAddMenuItem1() {
		JMenuItem item = new JMenuItem("Bota");
		final Image cursorImage = new ImageIcon("images/bot0.png").getImage();
		final Point hotspot = new Point(0, 0);
		final String name = "My Cursor";
		ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        		System.out.println("Pridavam bota");
	        		setCursor(getToolkit().createCustomCursor(cursorImage, hotspot, name));
	        		drawArea.setAddType(1);
	        		drawArea.setAdding(true);
	        }
	      };
	      item.addActionListener(actionListener);
	      return item;
	}
	
	public JComponent getAddMenuItem2() {
		JMenuItem item = new JMenuItem("Rastlinu");
		final Image cursorImage = new ImageIcon("images/plant.gif").getImage();
		final Point hotspot = new Point(0, 0);
		final String name = "My Cursor";
		ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        		System.out.println("Pridavam rastlinu");
	        		setCursor(getToolkit().createCustomCursor(cursorImage, hotspot, name));
	        		drawArea.setAddType(2);
	        		drawArea.setAdding(true);
	        }
	      };
	      item.addActionListener(actionListener);
	      return item;
	}
	
	public JComponent getStatsMenuItem1() {
		JMenuItem item = new JMenuItem("Max fitness");
		ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	getInit().getGraph().setVisible(true);
	        		//System.out.println("ätatistiky vöeobecnÈ");
	        }
	      };
	      item.addActionListener(actionListener);
	      return item;
	}
	public JComponent getStatsMenuItem2() {
		JMenuItem item = new JMenuItem("PoËet botov");
		ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	getInit().getGraph2().setVisible(true);
	        		//System.out.println("ätatistiky vöeobecnÈ");
	        }
	      };
	      item.addActionListener(actionListener);
	      return item;
	}
	public JComponent getStatsMenuItem3() {
		JMenuItem item = new JMenuItem("Zoznam botov");
		ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	getInit().getInfos().updateTable();
	        	getInit().getInfos().setVisible(true);
	        	setRunning(false);
	        	setEnabled(false);
	        }
	      };
	      item.addActionListener(actionListener);
	      return item;
	}	
	public JComponent getLoadButton() {
		JMenuItem tmp1 = new JMenuItem("NaËÌtaù");
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	            JFileChooser fileopen = new JFileChooser();
	            //FileFilter filter = new FileNameExtensionFilter("c files", "c");
	            //fileopen.addChoosableFileFilter(filter);

	            int ret = fileopen.showDialog(null, "NaËÌtaù s˙bor");

	            if (ret == JFileChooser.APPROVE_OPTION) {
	              File file = fileopen.getSelectedFile();
                 // System.out.println(file.getPath());
                  setLoad(file.getPath());
	              	
	            }
	        }
	      };
	     tmp1.addActionListener(actionListener);
	     return tmp1;
	}
	public JComponent getSaveButton() {
		saveButton = new JMenuItem("Uloûiù");
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	JFileChooser jfc = new JFileChooser();
	        		int choice = jfc.showSaveDialog(jfc);
	        		 
	        		if (choice == JFileChooser.APPROVE_OPTION) {
                        File file = jfc.getSelectedFile();
                        //getDrawArea().getIO().save(f.getPath());
	        			setSave(file.getPath());
                	}
            }
        };
        saveButton.addActionListener(actionListener);
        return saveButton;
	}
	public JComponent getExitButton() {
		JMenuItem tmp = new JMenuItem("Exit");
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	System.exit(0);
            }
        };
        tmp.addActionListener(actionListener);
        return tmp;
	}
	
	
	public void setMenusEnabled(boolean set) {
		getMyMenuBar().getMenu(1).setEnabled(set);
		getMyMenuBar().getMenu(2).setEnabled(set);
		getPauseMenu().setEnabled(set);
		saveButton.setEnabled(set);
		if (set) {
			newSimMenu.setEnabled(false);
		}
	}
	
	public DrawArea getDrawArea() {
		return drawArea;
	}
	public void setDrawArea(DrawArea drawArea) {
		this.drawArea = drawArea;
	}
	public void setInfoPanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}
	public InfoPanel getInfoPanel() {
		return infoPanel;
	}
	public void setSimSpeed(double simSpeed) {
		this.simSpeed = simSpeed;
	}
	public double getSimSpeed() {
		return simSpeed;
	}
	//public 
	public void setLoad(String load) {
		this.load = load;
	}
	public String getLoad() {
		return load;
	}
	public void setSave(String save) {
		this.save = save;
	}
	public String getSave() {
		return save;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	public boolean isRunning() {
		return running;
	}
	public void setBotid(int botid) {
		this.botid = botid;
	}
	public int getBotid() {
		return botid;
	}
	public void setInit(Init init) {
		this.init = init;
	}
	public Init getInit() {
		return init;
	}
	public void setScrollpanel(JScrollPane scrollpanel) {
		this.scrollpanel = scrollpanel;
	}
	public JScrollPane getScrollpanel() {
		return scrollpanel;
	}
	public JScrollPane getEditorScroll() {
		return editorScroll;
	}
	public void setEditorScroll(JScrollPane editorScroll) {
		this.editorScroll = editorScroll;
	}
	public NewFrame getNewFrame() {
		return newFrame;
	}
	public void setNewFrame(NewFrame newFrame) {
		this.newFrame = newFrame;
	}
	public JMenuBar getMyMenuBar() {
		return menuBar;
	}
	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}
	public JButton getPauseMenu() {
		return pauseMenu;
	}
	public void setPauseMenu(JButton pauseMenu) {
		this.pauseMenu = pauseMenu;
	}
	
}