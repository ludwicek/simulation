package graph;
import java.awt.Color;

//import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class LineGraph extends JFrame {

	private static final long serialVersionUID = -359555606443866977L;
	private JFreeChart chart;
	private ChartPanel chartPanel = new ChartPanel(chart);
	private double [][] dataSet;
	private String [] types;
	private String [] names;
	private String title;
	private String xLabel;
	private String yLabel;
	private Color colors[];
	
	private int lines;
	private int points;
	//private Random generator = new Random();

	public LineGraph(String[] names, String title, String xDesc, String yDesc ,Color colors[], int lines) {
		this.names = names;
		this.title = title;
		this.xLabel = xDesc;
		this.yLabel = yDesc;
		this.colors = colors;
		this.lines = lines;
		updateGraph(new double[][] {}, new String[] {}, 0);
		
		initGUI();
	}
	
	public void updateGraph(double[][] values, String[] types, int points) {
		setPoints(points);
		setDataSet(values);
		setTypes(types);
		updateChart();
	}
   
    private CategoryDataset createDataset() {

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int i, j;
        double [][] data = getDataSet();
        String [] type = getTypes();
        int jump = (points / 30) + 1;
        for (i = 0; i < lines; i++){
        	if (data.length == 0) break;
        	for (j = 0; j < points; j+=jump) {
        			
        			dataset.addValue(data[i][j], names[i] , type[j]);
        	}
        }
        //System.out.println(dataset.);
        return dataset;               
    }
 
    public void chartErzeugen(){
       chart = createChart(createDataset());
    }
   
    public void updateChart(){
    	this.chart = createChart(createDataset());
        this.chart.setBackgroundPaint(new Color(220,220,220));
        //this.chartPanel.setBackground(Color.lightGray);
        //this.chart.fireChartChanged();
        this.chartPanel.setChart(this.chart);
        this.chartPanel.updateUI();
        chartPanel.repaint();
    }   

    private JFreeChart createChart(final CategoryDataset dataset) {
       final JFreeChart chart = ChartFactory.createLineChart(
            getTitle(),       // chart title
            getxLabel(),                    // domain axis label
            getyLabel(),                   // range axis label
            dataset,                   // data
            PlotOrientation.VERTICAL,  // orientation
            true,                      // include legend
            false,                      // tooltips
            false                      // urls
        );

        chart.setBackgroundPaint(Color.white);
        

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);
        
        CategoryItemRenderer renderer = plot.getRenderer();
        int i = 0;
        for (Color color: colors) {
		    renderer.setSeriesPaint(i, color);
		    i++;
        }
        plot.setRenderer(renderer);
        
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //rangeAxis.setRange(0,300);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        return chart;
    }
   
    public void initGUI(){
       
        chart.setBackgroundPaint(new Color(220,220,220));
        chartPanel.setBackground(Color.lightGray);
        chartPanel.setPreferredSize(new java.awt.Dimension(750, 410));
        setContentPane(chartPanel);
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //setVisible(true);
    }   

	public double[][] getDataSet() {
		return dataSet;
	}

	public void setDataSet(double[][] dataSet) {
		this.dataSet = dataSet;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String[] types) {
		this.types = types;
	}

	public String [] getNames() {
		return names;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	public void setxLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public String getxLabel() {
		return xLabel;
	}
	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	public String getyLabel() {
		return yLabel;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}