package core;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

import quizz.SettingsWindow;
import quizz.TestWindow;
import tools.Logger;

import dictionary.Condition;
import dictionary.Criterias;
import dictionary.Dictionary;

public class MainWindow {
	protected JFrame frame;	
	private JLabel kanjiLabel;
	private JLabel meaningsLabel;
	private JLabel kunReadingLabel;
	private JLabel onReadingLabel;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton;
	private JButton btnLauchTestWindow;
	private JButton btnSettings;
	private static MainWindow _instance = null; 
	
	private Dictionary dict() {
		return Dictionary.getInstance();
	}
	
	public static void initializeDone() {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
				_instance.frame.setVisible(true);
				_instance.update();
		    }
		});
	}
	
	protected MainWindow() {
		super();
		initialize();
		Settings.initialize();
		frame.setVisible(false);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dictionary.getInstance();
					_instance = new MainWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Update GUI with current Kanji
	 */
	public void update() {
		kanjiLabel.setText(dict().current().getLiteral());
		meaningsLabel.setText(dict().current().getJoinedMeanings(", "));
		kunReadingLabel.setText("「" + dict().current().getJoinedKunReadings("�? ") + "�?");
		onReadingLabel.setText("「" + dict().current().getJoinedOnReadings("�? ") + "�?");
		Logger.log("ucs: " + dict().current().getUcs());
		Logger.log("JLPT Level: " + dict().current().getJlpt());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {	
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 371);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		meaningsLabel = new JLabel("New label");
		meaningsLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		kunReadingLabel = new JLabel("New label");
		onReadingLabel = new JLabel("New label");
		kanjiLabel = new JLabel("New label");
		kanjiLabel.setHorizontalAlignment(SwingConstants.CENTER);
		kanjiLabel.setFont(new Font("Meiryo", Font.PLAIN, 70));
		
		JButton btnPrev = new JButton("Prev");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dict().previous();
				update();
			}
		});
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dict().next();
				update();
			}
		});
		
		meaningsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		kunReadingLabel.setFont(new Font("Meiryo UI", Font.BOLD, 14));
		kunReadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		onReadingLabel.setFont(new Font("Meiryo UI", Font.BOLD, 14));
		onReadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblNewLabel = new JLabel("Kun");
		
		lblNewLabel_1 = new JLabel("Means");
		
		lblNewLabel_2 = new JLabel("On");
		
		JButton btnRandom = new JButton("Random");
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dict().random();
				update();
			}
		});
		
		btnNewButton = new JButton("test");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					List<Kanji> ks = dict().queryKanjis(
							new Condition(Criterias.MOST_SHARED_MEANINGS_WITH_KANJI, dict().current())
							);
					
					if(ks != null) {
						Logger.log("found " + ks.size() + " kanjis which matches criterias");
						dict().setIndex(dict().getIndexOf(ks.get(0)));
						update();
					} else {
						Logger.log("No kanjis with similar meanings found (surprising huh ?)");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnLauchTestWindow = new JButton("Lauch test window");
		btnLauchTestWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TestWindow();
			}
		});
		
		btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SettingsWindow();
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(kanjiLabel, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
								.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
								.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(onReadingLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(meaningsLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(kunReadingLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnPrev, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSettings, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
								.addComponent(btnRandom, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnLauchTestWindow, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnNext, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(kanjiLabel, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(kunReadingLabel, GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(meaningsLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(onReadingLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNext)
						.addComponent(btnPrev)
						.addComponent(btnRandom))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnLauchTestWindow)
						.addComponent(btnSettings))
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}

	protected JLabel getKanjiLabel() {
		return kanjiLabel;
	}
}
