package gui;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import core.Condition;
import core.Criterias;
import core.Dictionary;
import core.DictionaryAware;
import core.InfoType;
import core.Kanji;
import core.Settings;
import core.UserManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestWindow extends DictionaryAware implements AnswerChoosenEventListener {	
	private JLabel lastKanjiLabel;
	private JLabel lastMeaningLabel;
	private JLabel lastReadingLabel;
	private JPanel panel;
	protected JFrame frame;
	private Kanji kanjiToGuess;
	private HashMap<InfoType, JLabel> proposedLabels = new HashMap<InfoType, JLabel>();
	
	
	protected TestWindow() {
		super();
		
		if(UserManager.getUser() == null) {
			SelectUser selectUser = new SelectUser(frame);
			UserManager.setUser(selectUser.getSelectedUser());
			Logger.log("user selected: " + UserManager.getUser().getName());
		}
		
		initialize();		
		frame.setVisible(true);
		nextQuestion();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	protected void initialize() {
		Logger.log((Settings.UsePondaration ? "using" : "not using") + " pondarated random");
		
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					UserManager.getUser().save();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		frame.setResizable(false);
		frame.setTitle("Test..");
		frame.setBounds(100, 100, 611, 451);
		
		// Create the "question" Labels
		JLabel proposedKanji = new JLabel("Kanji");
		JLabel proposedMeaning = new JLabel("Meaning");
		JLabel proposedKunReading = new JLabel("On Reading");
		JLabel proposedOnReading = new JLabel("Kun Reading");
		
		// Add in the hashmap
		proposedLabels.put(InfoType.KANJI, proposedKanji);
		proposedLabels.put(InfoType.MEANING, proposedMeaning);
		proposedLabels.put(InfoType.ONREADING, proposedOnReading); 
		proposedLabels.put(InfoType.KUNREADING, proposedKunReading); 
		
		// Set Centered
		proposedKanji.setHorizontalAlignment(SwingConstants.CENTER);
		proposedMeaning.setHorizontalAlignment(SwingConstants.CENTER);
		proposedOnReading.setHorizontalAlignment(SwingConstants.CENTER);
		proposedKunReading.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Set Fonts
		proposedKanji.setFont(new Font("Meiryo", Font.PLAIN, 70));
		proposedMeaning.setFont(new Font("Meiryo", Font.PLAIN, 18));
		proposedOnReading.setFont(new Font("Meiryo", Font.PLAIN, 22));
		proposedKunReading.setFont(new Font("Meiryo", Font.PLAIN, 22));
		
		// Set default Values (from Settings)
		for(Entry<InfoType, Boolean> entry : Settings.InTestShow.entrySet()) {
			proposedLabels.get(entry.getKey()).setVisible(entry.getValue());
			proposedLabels.get(entry.getKey()).setEnabled(entry.getValue());
		}
		
		JLabel lblNewLabel_1 = new JLabel("Consignes");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		AnswerButton noAnswerBtn = new AnswerButton("I don't know the answer :'(");
		noAnswerBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		noAnswerBtn.addEventListener(this);
		
		JButton skipBtn = new JButton("Skip question without impacting score/weight (cheating !)");
		skipBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextQuestion();
			}
		});
		skipBtn.setFont(new Font("Tahoma", Font.ITALIC, 11));
		
		panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JButton button = new JButton("?");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Logger.log("Kanji infos not implemented yet");
			}
		});
		
		JButton btnP = new JButton("P");
		btnP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Logger.log("pause/timer not implemented yet");
			}
		});
		
		lastKanjiLabel = new JLabel("previous kanjis");
		lastKanjiLabel.setFont(new Font("Meiryo UI", Font.PLAIN, 20));
		lastKanjiLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lastMeaningLabel = new JLabel("previous meaning");
		lastMeaningLabel.setFont(new Font("Meiryo UI", Font.PLAIN, 13));
		lastMeaningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		lastReadingLabel = new JLabel("lastReading");
		lastReadingLabel.setFont(new Font("Meiryo UI", Font.PLAIN, 12));
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 575, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(proposedMeaning, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(proposedKunReading, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
										.addComponent(proposedOnReading, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addComponent(proposedKanji, GroupLayout.PREFERRED_SIZE, 161, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnP, 0, 0, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(noAnswerBtn, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(skipBtn, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lastKanjiLabel, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lastMeaningLabel, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lastReadingLabel, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(proposedOnReading, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(proposedKunReading, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(proposedMeaning, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(button)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnP))
						.addComponent(proposedKanji, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
					.addGap(0)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lastReadingLabel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lastKanjiLabel, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
							.addComponent(lastMeaningLabel, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(noAnswerBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(skipBtn))
					.addContainerGap())
		);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		frame.getContentPane().setLayout(groupLayout);
	}
	
	@Override
	protected JLabel getKanjiLabel() {
		return proposedLabels.get(InfoType.KANJI);
	}

	public void addAnswers(List<Kanji> kanjis) {
		switch (Settings.AsResponsePropose) {
		case MEANING:
			for(Kanji kanji : kanjis) {
				MeaningButton b = new MeaningButton(kanji);
				b.addEventListener(this);
				panel.add(b);
			}	
			break;
		case KANJI:
			for(Kanji kanji : kanjis) {
				KanjiButton b = new KanjiButton(kanji);
				b.addEventListener(this);
				panel.add(b);
			}	
			break;
		case ONREADING:
		case KUNREADING:
		default:
			throw new AssertionError("Yet unimplemented Test type");
		}
	}
	
	@Override
	protected void update() {
		proposedLabels.get(InfoType.KANJI).setText(kanjiToGuess.toString());
		proposedLabels.get(InfoType.MEANING).setText(kanjiToGuess.getJoinedMeanings(", "));
		proposedLabels.get(InfoType.ONREADING).setText(kanjiToGuess.getJoinedOnReadings(", "));
		proposedLabels.get(InfoType.KUNREADING).setText(kanjiToGuess.getJoinedKunReadings(", "));
		panel.repaint();
	}
	
	public void setKanji(Kanji kanji) {
		kanjiToGuess = kanji;
		update();
	}

	public void nextQuestion() {
		for(Component button : panel.getComponents()) {
			panel.remove(button);
		}
		try {
			Integer nbOfKanjis = 4; 
			Condition conditionRandom = Settings.UsePondaration
					? new Condition(Criterias.SELECT_RANDOM, nbOfKanjis)
					: new Condition(Criterias.SELECT_RANDOM_PONDARATED, nbOfKanjis)
					;
			
			List<Kanji> ks = Dictionary.getInstance().queryKanjis(new Condition[] {
					new Condition(Criterias.JLPT_GREATER_OR_EQUALS, Settings.getMaxJLPT()),
					conditionRandom
			});
			if(ks != null) {				
				setKanji(ks.get((int)Math.floor( Math.random() * nbOfKanjis )));
				addAnswers(ks);
			} else {
				Logger.log("No Kanji found");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		update();		
	}

	@Override
	public void handleAnswerChoosenEvent(EventObject e) {
		Kanji choosenKanji = ((AnswerButton)e.getSource()).getKanji();
		
		lastKanjiLabel.setText(kanjiToGuess.toString());
		lastMeaningLabel.setText(kanjiToGuess.getJoinedMeanings(", "));
		lastReadingLabel.setText("Kun: " + kanjiToGuess.getJoinedKunReadings(", ") 
				+ " - On: " + kanjiToGuess.getJoinedOnReadings(", "));
		
		Color goodColor = Color.BLUE;
		Color badColor = Color.RED;
		
		if(kanjiToGuess.equals(choosenKanji)) {
			UserManager.getUser().correctAnswer(kanjiToGuess);
			
			lastKanjiLabel.setForeground(goodColor);
			lastMeaningLabel.setForeground(goodColor);
			lastReadingLabel.setForeground(goodColor);
		} else {
			UserManager.getUser().wrongAnswer(kanjiToGuess);
			if(choosenKanji != null) {
				UserManager.getUser().wrongAnswer(choosenKanji);
			}
			
			lastKanjiLabel.setForeground(badColor);
			lastMeaningLabel.setForeground(badColor);
			lastReadingLabel.setForeground(badColor);
		}
		
		nextQuestion();
	}
}
