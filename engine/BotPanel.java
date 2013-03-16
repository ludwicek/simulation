package engine;

import graphic.Circle;
import graphic.ConnectionLine;

import init.Init;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

//import nn.Connectable;
import nn.Layer;
import nn.NeuralNetwork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
*
* @author ludwik
*/
public class BotPanel extends javax.swing.JFrame {


	private static final long serialVersionUID = 1L;
	private MainWindow main;
	private Init init;
	private int clicked;
	private double wageTmp;
	private BotPanelDrawArea jPanel1;
	private NeuralNetwork nn;
	private NeuralNetwork nntmp;
	private Bot bot;
/**
    * Creates new form NewJFrame
    */
   public BotPanel(Bot bot) {
       initComponents();
       setBot(bot);
       checkbox1.setSelected(bot.isDying());
       
       setTitle("NeurÛnov· sieù Bota");
   }
   public void init() {
       nn = bot.getNeuralNetwork();
       Layer layer, nextLayer = null;
	   int i = 0,j = 0,k = 0,m = 0,actual = 0,neuroneRadius,offsetY = 0,height = jPanel1.getHeight() + 30;
	   // kruhy
       for (i = 0; i < nn.getLayers().size(); i++) {
    	   layer = nn.getLayers().get(i);
    	   if (i < nn.getLayers().size() - 1) {
    		   nextLayer = nn.getLayers().get(i+1);
    	   } 
    	   for (j = 0; j < layer.getConnectables().size(); j++) {
    		   neuroneRadius = 11;
    		   if (i == 0) {
	    		   neuroneRadius = 5;
	    	   }
    		   offsetY = (height - (layer.getConnectables().size()*((neuroneRadius*2)+4))) / 2;
    		   //System.out.println((layer.getConnectables().size()*((neuroneRadius*2)+4)));
    		   Circle circle = new Circle(new Point2D.Double(70 * (i + 1),5 + offsetY + (j*((neuroneRadius*2)+4))), neuroneRadius,Color.BLACK,this);
	    	   
	    	   if (i < nn.getLayers().size() - 1) {
		    	   for (k = 0; k < nextLayer.getConnectables().size(); k++) {
		    		   offsetY = (height - (nextLayer.getConnectables().size()*((neuroneRadius*2)+4))) / 2;
		    	   }
	    	   }
	    	   jPanel1.getShapes().add(circle);
    	   }
       }
       // spojenia
       k = 0;
       for (i = 0; i < nn.getLayers().size(); i++) {
		   neuroneRadius = 11;
		   if (i == 0) {
    		   neuroneRadius = 5;
    	   }
    	   actual += nn.getLayers().get(i).getConnectables().size();
    	   layer = nn.getLayers().get(i);
    	   if (i < nn.getLayers().size() - 1) {
    		   nextLayer = nn.getLayers().get(i+1);
    	   } 
    	   for (j = 0; j < layer.getConnectables().size(); j++, k++) {
  		   
    		     
    		   if (i < nn.getLayers().size() - 1) {
	    		   for (m = 0; m < nextLayer.getConnectables().size(); m++ ) {
	    			   Point2D tmp = jPanel1.getShapes().get(actual+m).getPosition();
		    		   ConnectionLine conn = new ConnectionLine(new Point2D.Double(tmp.getX() - neuroneRadius , tmp.getY()));
		    		   double wage = nextLayer.getConnectables().get(m).getInputConnections().get(j).getWage();
		    		   conn.setWage(wage);
		    		   jPanel1.getShapes().get(k).getLines().add(conn);
		    		   jPanel1.getShapes().get(actual+m).getLinesBefore().add(conn);
	    		   }
    		   }
    	   }
       }
              
       // info o botovi
       String text = "";
       for (String info : bot.getInfo()) {
    	   text += info + "\n";
       }

       textField2.setText(text);
       
        //rows and columns

       text = "";
       int count = 0;
       for (Double num : bot.getNeuralNetwork().getWages()) {
    	   text += num;
    	   if (++ count  % 4 == 0 && count > 0) {
    		   text += "\n";
    	   }
    	   else {
    		   text += ",";
    	   }
       }
       area.setText(text);
       area.setCaretPosition(0);
   }

   public void paint(Graphics g) {
	   super.paint(g);

	   //System.out.println("h˙");
   }
   @SuppressWarnings("deprecation")
private void initComponents() {




       jSeparator2 = new javax.swing.JSeparator();
       jButton5 = getLoadButton();
       jButton2 = new javax.swing.JButton();
       jButton1 = new javax.swing.JButton();
       checkbox1 = new javax.swing.JCheckBox();
       jButton6 = getSaveButton();
       jPanel1 = new BotPanelDrawArea(this);
       label3 = new java.awt.Label();
       textField2 = new JTextPane();
       label1 = new java.awt.Label();
       textField3 = new java.awt.TextField();
       area = new JTextArea(10,40);
       textField1 = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
       
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       this.addWindowListener(new WindowAdapter() {
    	      public void windowClosing(WindowEvent e) {
    	    	  cancelEnd();
    	        }
       });
       

       jButton2.setText("Cancel");
       jButton2.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent evt) {
               jButton2ActionPerformed(evt);
           }
       });

       jButton1.setText("OK");
       jButton1.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent evt) {
               jButton1ActionPerformed(evt);
           }
       });
  
       
       checkbox1.setLabel("dying");


       //textField1.setEditable(false);
       //textField1.setEnabled(true);
       


       textField2.setBackground(new java.awt.Color(240, 240, 240));
       textField2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
       textField2.setEditable(false);
       textField2.setEnabled(false);
       textField2.setBounds(20,20,20,20);

       label1.setFont(new java.awt.Font("Dialog", 0, 24));
       label1.setText("V·ha spojenia");

       textField3.setBackground(new java.awt.Color(240, 240, 240));
       textField3.setEditable(false);
       textField3.setEnabled(false);
       textField3.setFont(new java.awt.Font("Dialog", 0, 12));
       textField3.setText("0.0");

       javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
       jPanel1.setLayout(jPanel1Layout);
       jPanel1Layout.setHorizontalGroup(
           jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(jPanel1Layout.createSequentialGroup()
               .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                   .addComponent(textField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                   .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addGap(19, 19, 19)
               .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addGap(20, 20, 20))
       );
       jPanel1Layout.setVerticalGroup(
           jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(jPanel1Layout.createSequentialGroup()
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addGroup(jPanel1Layout.createSequentialGroup()
                       .addGap(55, 55, 55)
                       .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                       .addGap(13, 13, 13)
                       .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                           .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                           .addComponent(textField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                   .addGroup(jPanel1Layout.createSequentialGroup()
                       .addContainerGap()
                       .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
               .addContainerGap(15, Short.MAX_VALUE))
       );

       javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
       getContentPane().setLayout(layout);
       layout.setHorizontalGroup(
           layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addComponent(jSeparator2)
           .addGroup(layout.createSequentialGroup()
               .addContainerGap()
               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addGroup(layout.createSequentialGroup()
                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                           .addComponent(jButton6)
                           .addComponent(jButton5))
                       .addGap(18, 18, 18)
                       .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                           .addGroup(layout.createSequentialGroup()
                               .addComponent(jButton1)
                               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                               .addComponent(jButton2))
                           .addComponent(checkbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                       .addGap(8, 8, 8))
                   .addGroup(layout.createSequentialGroup()
                       .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                       .addContainerGap())))
       );
       layout.setVerticalGroup(
           layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(layout.createSequentialGroup()
               .addContainerGap()
               .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addGap(18, 18, 18)
               .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addGap(19, 19, 19)
               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addGroup(layout.createSequentialGroup()
                       .addComponent(checkbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                       .addGap(151, 151, 151)
                       .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                           .addComponent(jButton1)
                           .addComponent(jButton2)))
                   .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                   .addGroup(layout.createSequentialGroup()
                       .addComponent(jButton5)
                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                       .addComponent(jButton6)))
               .addContainerGap())
       );

       pack();
       repaint();
   }// </editor-fold>

   /**
    * @param args the command line arguments
    */
   public void test() {
	   
   }
   public void drawNS() {
	   
   } 
   
   public void setMain(MainWindow main) {
	this.main = main;
   }

	public MainWindow getMain() {
		return main;
	}
public void setInit(Init init) {
		this.init = init;
	}

	public Init getInit() {
		return init;
	}
	public void setClicked(int clicked) {
		this.clicked = clicked;
	}

	public int getClicked() {
		return clicked;
	}
	public void setWage(double wage) {
		textField3.setText(Double.toString(wage));
	}
	public void setWageTmp(double wageTmp) {
		this.wageTmp = wageTmp;
	}
	public double getWageTmp() {
		return wageTmp;
	}
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	public Bot getBot() {
		return bot;
	}
	
    public void save(String filename) {
    	ObjectOutputStream outputStream = null;
    	try {
            outputStream = new ObjectOutputStream(new FileOutputStream(filename));
            outputStream.writeObject(bot.getNeuralNetwork());
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                    System.out.println("Ulozenie NS prebehlo v poriadku");
                }
            } catch (IOException ex) {
                System.out.println("Chyba pri ukladani");
            }
        }
    }
    public void load(String filename) {
        ObjectInputStream inputStream = null;

        try {
            inputStream = new ObjectInputStream(new FileInputStream(filename));
            Object o = inputStream.readObject();
            if (o instanceof nn.NeuralNetwork) {
            	setNntmp(bot.getNeuralNetwork());
            	bot.setNeuralNetwork((nn.NeuralNetwork)o);           	
            }
        } catch (EOFException ex) {
            System.out.println("End of file reached.");
        } catch (ClassNotFoundException ex) {
            System.out.println("1");
        } catch (FileNotFoundException ex) {
            System.out.println("2");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            //Close the ObjectInputStream
            try {
                if (inputStream != null) {
                    inputStream.close();
                    System.out.println("Nacitanie prebehlo v poriadku");
                    
                    //this.initComponents();
                    jPanel1.getShapes().clear();
                    init();
                    this.repaint();
                    jPanel1.repaint();
                    
                } 
            }  catch (IOException ex) {
            
            	}
    
           }	
    }
	public JButton getLoadButton() {
		JButton tmp = new JButton("NaËÌtaù");
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	            JFileChooser fileopen = new JFileChooser();
	            //FileFilter filter = new FileNameExtensionFilter("c files", "c");
	            //fileopen.addChoosableFileFilter(filter);

	            int ret = fileopen.showDialog(null, "NaËÌtaù s˙bor");

	            if (ret == JFileChooser.APPROVE_OPTION) {
	              File file = fileopen.getSelectedFile();
                  load(file.getPath());
	              	
	            }
	        }
	      };
	     tmp.addActionListener(actionListener);
	     return tmp;
     
	}
	public JButton getSaveButton() {
		JButton tmp = new JButton("Uloûiù");
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	JFileChooser jfc = new JFileChooser();
	        		int choice = jfc.showSaveDialog(jfc);
	        		 
	        		if (choice == JFileChooser.APPROVE_OPTION) {
                        File file = jfc.getSelectedFile();
                        //getDrawArea().getIO().save(f.getPath());
	        			save(file.getPath());
                	}
            }
        };
        tmp.addActionListener(actionListener);
        return tmp;
	}
	
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // OK
    	this.dispose();
    	bot.setDying(checkbox1.isSelected());
	    main.setVisible(true);
	    main.setEnabled(true);
	    main.setRunning(true);
	    main.getDrawArea().setShowingBotPanel(false);
    }  
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    	// Cancel
    	cancelEnd();
    }  
   	
	public void setNntmp(NeuralNetwork nntmp) {
		this.nntmp = nntmp;
	}
	public NeuralNetwork getNntmp() {
		return nntmp;
	}
	
	private void cancelEnd() {
    	if (getNntmp() != null) bot.setNeuralNetwork(getNntmp());
    	this.dispose();
	    main.setVisible(true);
	    main.setEnabled(true);
	    main.setRunning(true);	
	    main.getDrawArea().setShowingBotPanel(false);
	}

	// Variables declaration - do not modify
    private javax.swing.JCheckBox checkbox1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JSeparator jSeparator2;
    private java.awt.Label label1;
    private java.awt.Label label3;
    private JScrollPane textField1;
    private JTextPane textField2;
    private JTextArea area;
    private java.awt.TextField textField3;

}