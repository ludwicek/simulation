package engine;
import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*; 
import javax.swing.border.Border;

public class MainWindow extends JFrame { 

	private static final long serialVersionUID = 1L;
	private DrawArea drawArea;
	private InfoPanel infoPanel = new InfoPanel();
	public MainWindow() { 
		setTitle("Simul·cia umelÈho ûivota - Diplomov· pr·ca, ºudovÌt KovaËoviË (2012)"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setJMenuBar(createMenuBar());
        
        Container pane = getContentPane(); 
		pane.setLayout(new BorderLayout()); 
		pane.add(createRightMenu(), BorderLayout.EAST); 
		setDrawArea(createDrawArea());
		pane.add(getDrawArea(), BorderLayout.CENTER); 
		//pane.add(createButtonPanel(), BorderLayout.SOUTH); 
		pack();
	}
	public void showInfo(Infoable infoable) throws CloneNotSupportedException {
		this.infoPanel.setVisible(true);
		this.infoPanel.drawInfo(infoable);
		
	}
	public void updateInfo() {
		this.infoPanel.repaint();
	}
	public void hideInfo() {
		this.infoPanel.setVisible(false);
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
		
		menu_2.add(getSaveButton(true));
		menu_2.add(getLoadButton(true));
		menu_1.setBorder(border_1);
		menu_2.setBorder(border_2);
		infoPanel.setBorder(border_3);
		infoPanel.setVisible(false);
		
		JPanel rightPanel = new JPanel();
		menu.add(menu_1, BorderLayout.NORTH);
		//menu.add(menu_2, BorderLayout.SOUTH);
		menu.add(infoPanel, BorderLayout.SOUTH);
		rightPanel.setLayout(new BorderLayout()); 
		rightPanel.add(menu, BorderLayout.NORTH);
		rightPanel.setPreferredSize( new Dimension( 200, 15 ) );
		return rightPanel; 
	} 
	private DrawArea createDrawArea() { 
		DrawArea area = new DrawArea(this); 
		//area.setBorder(BorderFactory.createLineBorder(Color.black));

		area.setBackground(new Color(255,255,255));
		return area;
	} 
	private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("S˙bor");
        JMenu aboutMenu = new JMenu("O programe");
        fileMenu.add(getSaveButton(false));
        fileMenu.add(getLoadButton(false));
        fileMenu.add(getExitButton());
        aboutMenu.add(new JMenuItem("O programe"));
        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);
        return menuBar;
	}

	public JComponent getLoadButton(boolean button) {
		JButton tmp = new JButton("NaËÌtaù");
		JMenuItem tmp1 = new JMenuItem("NaËÌtaù");
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	            JFileChooser fileopen = new JFileChooser();
	            //FileFilter filter = new FileNameExtensionFilter("c files", "c");
	            //fileopen.addChoosableFileFilter(filter);

	            int ret = fileopen.showDialog(null, "NaËÌtaù s˙bor");

	            if (ret == JFileChooser.APPROVE_OPTION) {
	              File file = fileopen.getSelectedFile();
                  System.out.println(file.getPath());
                  //getDrawArea().getIO().load(file.getPath());
	              	
	            }
	        }
	      };
	     tmp.addActionListener(actionListener);
	     tmp1.addActionListener(actionListener);
	     if (button) return tmp;
	     return tmp1;
	}
	public JComponent getSaveButton(boolean button) {
		JButton tmp = new JButton("Uloûiù");
		JMenuItem tmp1 = new JMenuItem("Uloûiù");
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	JFileChooser jfc = new JFileChooser();
	        		int choice = jfc.showSaveDialog(jfc);
	        		 
	        		if (choice == JFileChooser.APPROVE_OPTION) {
                        //File f = jfc.getSelectedFile();
                        //getDrawArea().getIO().save(f.getPath());
                	}
            }
        };
        tmp.addActionListener(actionListener);
        tmp1.addActionListener(actionListener);
        if (button) return tmp;
        return tmp1;
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
}