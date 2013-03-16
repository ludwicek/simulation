package engine;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import nn.NeuralNetwork;

import init.Init;

public class NewFrame extends javax.swing.JFrame {

	private Init init;
	private static final long serialVersionUID = -7992025750753638611L;
	public NewFrame(Init init) {
        initComponents();
        hideLoads();
        this.setSize(430,250);
        this.setTitle("Nov· simul·cia");
        this.setResizable(false);
        setInit(init);

    }


    
    public void hideLoads() {
        // rasa 2
        jButton2.setVisible(false);
        jLabel4.setVisible(false);
        jTextField4.setVisible(false);
        // rasa 3
        jButton5.setVisible(false);
        jLabel5.setVisible(false);
        jTextField5.setVisible(false);
        // rasa 4
        jButton6.setVisible(false);
        jLabel6.setVisible(false);
        jTextField6.setVisible(false);
    	
    }
    
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jSlider1 = new javax.swing.JSlider();
        jTextField2 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jSlider2 = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
  	      public void windowClosing(WindowEvent e) {
  	    	  close();
  	        }
        });        

        jTextField3.setEditable(false);
        jTextField3.setText("50");
        jTextField3.setEnabled(false);

        jSlider1.setMaximum(4);
        jSlider1.setMinimum(1);
        jSlider1.setPaintLabels(true);
        jSlider1.setPaintTicks(true);
        jSlider1.setSnapToTicks(true);
        jSlider1.setValue(1);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setText("1");
        jTextField2.setEnabled(false);

        jButton4.setText("Cancel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setText("NaËÌtaù");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("poËet botov v gener·cii");

        jTextField1.setEditable(false);
        jTextField1.setText("default");
        jTextField1.setEnabled(false);

        jSlider2.setMaximum(200);
        jSlider2.setMinimum(50);
        jSlider2.setPaintLabels(true);
        jSlider2.setPaintTicks(true);
        jSlider2.setSnapToTicks(true);
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider2StateChanged(evt);
            }
        });

        jLabel1.setText("poËet r·s");

        jButton3.setText("OK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        

        jLabel3.setText("naËÌtaù NS bota pre rasu 1");

        jTextField4.setEditable(false);
        jTextField4.setText("default");
        jTextField4.setEnabled(false);

        jLabel4.setText("naËÌtaù NS bota pre rasu 2");

        jButton2.setText("NaËÌtaù");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField5.setEditable(false);
        jTextField5.setText("default");
        jTextField5.setEnabled(false);

        jLabel5.setText("naËÌtaù NS bota pre rasu 3");

        jButton5.setText("NaËÌtaù");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField6.setEditable(false);
        jTextField6.setText("default");
        jTextField6.setEnabled(false);

        jLabel6.setText("naËÌtaù NS bota pre rasu 4");

        jButton6.setText("NaËÌtaù");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jButton3)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jTextField1))))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {
        jTextField2.setText(String.valueOf(jSlider1.getValue()));
        
        // rasa 1
        /*
        jButton1.setVisible(false);
        jLabel3.setVisible(false);
        jTextField1.setVisible(false);
        */
        hideLoads();
        int heightChange = 0;  
        switch(jSlider1.getValue()) {
	    	case 4: {
	            jButton6.setVisible(true);
	            jLabel6.setVisible(true);
	            jTextField6.setVisible(true);
	            heightChange += 40;
	    	} 
	    	case 3: {
	            jButton5.setVisible(true);
	            jLabel5.setVisible(true);
	            jTextField5.setVisible(true);
	            this.setSize(430,this.getSize().height + 30);
	            heightChange += 40;
	    	} 
        	case 2: {
                jButton2.setVisible(true);
                jLabel4.setVisible(true);
                jTextField4.setVisible(true);        		
                this.setSize(430,this.getSize().height + 30);
                heightChange += 40;
        	} 
        }
        this.setSize(430,250 + heightChange);
        
    }
    public void load(String filepath, String filename, int race, JTextField textField) {
        ObjectInputStream inputStream = null;

        try {
            inputStream = new ObjectInputStream(new FileInputStream(filepath));
            Object o = inputStream.readObject();
            if (o instanceof nn.NeuralNetwork) {
            	NeuralNetwork nns[] = init.getLoadNS(); 
            	nns[race-1] = (NeuralNetwork)o;
            	init.setLoadNS(nns);
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
                    textField.setText(filename);
                    
                } 
            }  catch (IOException ex) {
            
            	}
    
           }	
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
    	close();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
    	init.getMain().setMenusEnabled(true);
    	init.setNewSim(true);
    	init.setBotsNum(jSlider2.getValue()*jSlider1.getValue());
    	init.setRaceCount(jSlider1.getValue());
    	close();
    }
    

    private void jSlider2StateChanged(javax.swing.event.ChangeEvent evt) {
        jTextField3.setText(String.valueOf(jSlider2.getValue()));
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileopen = new JFileChooser();

        int ret = fileopen.showDialog(null, "NaËÌtaù s˙bor");

        if (ret == JFileChooser.APPROVE_OPTION) {
          File file = fileopen.getSelectedFile();
          load(file.getPath(),file.getName(),1,jTextField1);          	
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileopen = new JFileChooser();

        int ret = fileopen.showDialog(null, "NaËÌtaù s˙bor");

        if (ret == JFileChooser.APPROVE_OPTION) {
          File file = fileopen.getSelectedFile();
          load(file.getPath(),file.getName(),2,jTextField4);          	
        }
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileopen = new JFileChooser();

        int ret = fileopen.showDialog(null, "NaËÌtaù s˙bor");

        if (ret == JFileChooser.APPROVE_OPTION) {
          File file = fileopen.getSelectedFile();
          load(file.getPath(),file.getName(),3,jTextField5);          	
        }
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileopen = new JFileChooser();

        int ret = fileopen.showDialog(null, "NaËÌtaù s˙bor");

        if (ret == JFileChooser.APPROVE_OPTION) {
          File file = fileopen.getSelectedFile();
          load(file.getPath(),file.getName(),4,jTextField6);          	
        }
    }

    public void setInit(Init init) {
		this.init = init;
	}
    
    public void close() {
    	this.dispose();
    	init.getMain().mySetEnabled(true);
    	init.getMain().setVisible(true);
    }



	public Init getInit() {
		return init;
	}

	// Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration
}
