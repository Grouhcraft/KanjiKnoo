package main;

import gov.noaa.pmel.sgt.dm.SGTData;
import gov.noaa.pmel.sgt.dm.SGTMetaData;
import gov.noaa.pmel.sgt.dm.SimpleLine;
import gov.noaa.pmel.sgt.swing.JPlotLayout;
import gov.noaa.pmel.util.Point2D;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.SampleModel;

public class GraphWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private int[] channels = {1,2};
	private int subSampling = 100;
	private JPlotLayout graphALayout;
	private JPlotLayout graphBLayout;
	private String dataFile = "F:\\BCICIV_1_asc\\BCICIV_eval_ds1a_cnt.txt";
	private int channelsCount = 59;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphWindow frame = new GraphWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void updateGraphs() { 
		updateGraphs(false);
	}
	
	public void updateGraphs(Boolean force) {
		if(force || !((SGTData)graphALayout.getData().firstElement()).getId().equals(
				getDataId(dataFile, subSampling, channels[0])) ) {
			graphALayout.setBatch(true);
			
			graphALayout.clear();
			SGTData dataA = readTheData(dataFile, subSampling, channels[0]);
			graphALayout.addData(dataA);
			
			graphALayout.setBatch(false);
		}
		
		if(force || !((SGTData)graphBLayout.getData().firstElement()).getId().equals(
				getDataId(dataFile, subSampling, channels[1])) ) {
			graphBLayout.setBatch(true);
			
			graphBLayout.clear();
			SGTData dataB = readTheData(dataFile, subSampling, channels[1]);
			graphBLayout.addData(dataB);
			
			graphBLayout.setBatch(false);
		}
 	}

	/**
	 * Create the frame.
	 */
	public GraphWindow() {
		setTitle("EEG Viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		graphALayout = new JPlotLayout(false, false, false, false, "A", null, false);
		graphBLayout = new JPlotLayout(false, false, false, false, "B", null, false);
		graphALayout.setTitles("Ch.A", "", "");
		graphBLayout.setTitles("Ch.B", "", "");
		
		updateGraphs(true);
				
		setContentPane(contentPane);
		JButton btnPrevA = new JButton("<< Prev Ch.");
		JButton btnNextA = new JButton("Next Ch. >>");		
		JButton btnPrevB = new JButton("<< Prev. Ch.");
		JButton btnNextB = new JButton("Next Ch. >>");
		btnPrevA.addActionListener(this);
		btnNextA.addActionListener(this);
		btnPrevB.addActionListener(this);
		btnNextB.addActionListener(this);
		btnPrevA.setActionCommand("preva");
		btnNextA.setActionCommand("nexta");
		btnPrevB.setActionCommand("prevb");
		btnNextB.setActionCommand("nextb");
		
		JButton btnPlus = new JButton("+");
		JButton btnLess = new JButton("-");
		btnPlus.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnLess.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnPlus.addActionListener(this);
		btnLess.addActionListener(this);
		btnPlus.setActionCommand("increase_sampling");
		btnLess.setActionCommand("decrease_sampling");
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnPrevA, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnNextA, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
							.addGap(18))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(graphALayout, GroupLayout.PREFERRED_SIZE, 362,  GroupLayout.PREFERRED_SIZE)
							.addGap(22)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnPrevB, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnNextB, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
							.addGap(18))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(graphBLayout, GroupLayout.PREFERRED_SIZE, 362, GroupLayout.PREFERRED_SIZE)
							.addGap(22)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnPlus, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLess, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(11)
							.addComponent(btnPlus, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnLess, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
						.addComponent(graphBLayout, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
						.addComponent(graphALayout, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
					.addGap(24)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPrevA)
						.addComponent(btnPrevB)
						.addComponent(btnNextA)
						.addComponent(btnNextB))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}

	private SGTData readTheData(String file, int subsamplingFactor, int channel) {
		BufferedReader in = null;
		String line = null;
		int x,y;
		Point2D.Double p = null;
		ArrayList<Point2D> list = new ArrayList<Point2D>();
		
	    try {
			in = new BufferedReader(new FileReader(file));
			line = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    int i = 0;
	    while(line != null) {
	    	x = i; 
	    	y = Integer.parseInt(line.split("\t")[channel]);
	    	p = new Point2D.Double(x, y);
	    	list.add(p);
	    	
	    	i++;
	    	try {
	    		for(int ii=0; ii < (subsamplingFactor > 1 ? subsamplingFactor-1 : 1); ii++ ) {
	    			line = in.readLine();
	    		}
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    double xArr[] = new double[list.size()];
	    double yArr[] = new double[list.size()];
	    Iterator<Point2D> it = list.iterator();
	    i = 0;
	    while(it.hasNext()) {
	    	p = (Point2D.Double) it.next();
	    	xArr[i] = p.x;
	    	yArr[i] = p.y;
	    	i++;
	    }
	    
	    SimpleLine data = new SimpleLine(xArr, yArr, null);
	    data.setId(getDataId(file,subsamplingFactor, channel));
	    data.setXMetaData(new SGTMetaData("Time", "1000 / " + subsamplingFactor + " Hz", false, false));
	    data.setYMetaData(new SGTMetaData("Potential", "µV", false, false));
	    
		return data;
	}
	
	private String getDataId(String file, int sampling, int channel) {
		return file + sampling + "_" + channel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("preva")) {
			if(channels[0] > 1) {
				channels[0]--;
			}
		} else if (e.getActionCommand().equals("nexta")) {
			if(channels[0] < channelsCount) {
				channels[0]++;
			}
		} else if (e.getActionCommand().equals("prevb")) {
			if(channels[1] > 1) {
				channels[1]--;
			}
		} else if (e.getActionCommand().equals("nextb")) {
			if(channels[0] < channelsCount) {
				channels[1]++;
			}
		} else if (e.getActionCommand().equals("decrease_sampling")) {
				subSampling *= 10;
		} else if (e.getActionCommand().equals("increase_sampling")) {
			if(subSampling >= 10) {
				subSampling /= 10;
			}
		}
		updateGraphs();
	}
}
