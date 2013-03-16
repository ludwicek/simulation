package engine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import init.Init;

public class InfoFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private Init init;
	public InfoFrame(Init init) {
        setInit(init);
        initComponents();
    }
	

	public void updateTable() {
        TableModel data = jTable1.getModel();
        int count = ((DefaultTableModel)data).getRowCount();
        for (int j = 0; j < count; j++) {
        	((DefaultTableModel)data).removeRow(0);
        }
        for (Bot bot : getInit().getBots()) {
        	((DefaultTableModel)data).addRow(new Number[] {bot.getId(), bot.getRace(), Init.getFitness(bot), bot.getFoodInstancesCarried() }); 
        }
		
	}

    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable () {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int colIndex) {
        		  return false; //Disallow the editing of any cell
        		  }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
  	      public void windowClosing(WindowEvent e) {
  	    	  close();
  	        }
        });
        jTable1.setAutoCreateRowSorter(true);
        jScrollPane1.setViewportView(jTable1);
        jTable1.setEnabled(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
        );
        
        TableModel data = new DefaultTableModel() {  
			private static final long serialVersionUID = -8827796095982129114L;
			@SuppressWarnings("rawtypes")
			Class[] types = new Class [] {  
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class  
            };  
            @SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {  
                return types [columnIndex];  
            }  
        };
        ((DefaultTableModel)data).setColumnCount(5);
        ((DefaultTableModel)data).setColumnIdentifiers(new Object[] {"ID", "Typ", "Aktuálna fitness", "Prenesené jedlo"});  
        jTable1.setModel(data);

        updateTable();
        jTable1.addMouseListener(new MouseAdapter() {
        	   public void mouseClicked(MouseEvent e) {
        	      if (e.getClickCount() == 2) {
        	         JTable target = (JTable)e.getSource();
        	         int row = jTable1.convertRowIndexToModel(target.getSelectedRow());
        	         //System.out.println(row);
        	         
        	         int botID = ((Number)jTable1.getModel().getValueAt(row, 0)).intValue();
        	         //System.out.println(botID);
        	         
        	         if (getInit().getBot(botID).showInfo()) {
        	        	 for (Infoable info : getInit().getInfoables()) {
        	        		 info.setSelected(false);
        	        	 }
        	        	 getInit().getBot(botID).setSelected(true);
        	        	 try {
							getInit().getMain().getInfoPanel().setVisible(true);
							getInit().getMain().getInfoPanel().drawInfo(getInit().getBot(botID));
						} catch (CloneNotSupportedException e1) {
							e1.printStackTrace();
						}
						setVisible(false);
						close();
        	         }
        	         
       	         }
        	   }
        	});
        //System.out.println(jTable1.getColumnClass(0));

        pack();
    }

    public void setInit(Init init) {
		this.init = init;
	}
	private void close() {
	    getInit().getMain().setVisible(true);
	    getInit().getMain().setEnabled(true);
	    getInit().getMain().setRunning(true);	
	}
	
	public Init getInit() {
		return init;
	}

	private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
}