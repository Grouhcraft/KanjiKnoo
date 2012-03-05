package quizz;

import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import core.InfoType;
import core.Settings;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class SettingsWindow {

	private JFrame frmSettings;

	/**
	 * Create the application.
	 */
	public SettingsWindow() {
		initialize();
		frmSettings.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSettings = new JFrame();
		frmSettings.setTitle("Settings");
		frmSettings.setBounds(100, 100, 356, 498);
		
		JLabel lblNumberOfQuestions = new JLabel("Number of Questions per test");
		
		final JCheckBox cbJLPT5 = new JCheckBox("JLPT 5");
		if(Settings.JLPT5) {
			cbJLPT5.setSelected(true);
		}
		cbJLPT5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.JLPT5 = cbJLPT5.isSelected();
			}
		});
		
		final JCheckBox cbJLPT4 = new JCheckBox("JLPT 4");
		if(Settings.JLPT4) {
			cbJLPT4.setSelected(true);
		}
		cbJLPT4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.JLPT4 = cbJLPT4.isSelected();
			}
		});
		
		final JCheckBox cbJLPT2 = new JCheckBox("JLPT 2");
		if(Settings.JLPT2) {
			cbJLPT2.setSelected(true);
		}
		cbJLPT2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.JLPT2 = cbJLPT2.isSelected();
			}
		});
		
		final JCheckBox cbJLPT1 = new JCheckBox("JLPT 1");
		if(Settings.JLPT1) {
			cbJLPT1.setSelected(true);
		}
		cbJLPT1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.JLPT1 = cbJLPT1.isSelected();
			}
		});
		
		final JCheckBox cbJLPT0 = new JCheckBox("non-JLPT");
		cbJLPT0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.JLPT0 = cbJLPT0.isSelected();
			}
		});
		if(Settings.JLPT0) {
			cbJLPT0.setSelected(true);
		}
		
		final JCheckBox allowHarderKanjis = new JCheckBox("Allow Kanjis of harder JLPT levels in proposals");
		if(Settings.AllowToProposeHarderKanjis) {
			allowHarderKanjis.setSelected(true);
		}
		allowHarderKanjis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.AllowToProposeHarderKanjis = allowHarderKanjis.isSelected();
			}
		});
		
		final JCheckBox allowEasierKanjis = new JCheckBox("Allow Kanjis of easier JLPT levels in proposals");
		if(Settings.AllowToProposeEasierKanjis) {
			allowEasierKanjis.setSelected(true);
		}
		allowEasierKanjis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.AllowToProposeEasierKanjis = allowEasierKanjis.isSelected();
			}
		});
		
		final JCheckBox chckbxUsePondaratesRandom = new JCheckBox("Use pondarated random");
		if(Settings.UsePondaration) {
			chckbxUsePondaratesRandom.setSelected(true);
		}
		chckbxUsePondaratesRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.UsePondaration = chckbxUsePondaratesRandom.isSelected();
			}
		});
		
		final JCheckBox chckbxTryToTrap = new JCheckBox("Try to trap with the proposals");
		if(Settings.AllowTrap) {
			chckbxTryToTrap.setSelected(true);
		}
		chckbxTryToTrap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.AllowTrap = chckbxTryToTrap.isSelected();
			}
		});
		
		final JCheckBox chckbxIncludingRandomMeanings = new JCheckBox("including random meanings and false kanjis");
		if(Settings.AllowFalseAnswers) {
			chckbxIncludingRandomMeanings.setSelected(true);
		}
		chckbxIncludingRandomMeanings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.AllowFalseAnswers = chckbxIncludingRandomMeanings.isSelected();
			}
		});
		
		JLabel lblTimer = new JLabel("Timer (0 = unlimited)");
		
		final JSpinner timerSpinner = new JSpinner();
		timerSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Settings.Timer = (Integer) timerSpinner.getValue();
			}
		});
		timerSpinner.setValue(Settings.Timer);
		
		final JSpinner nbOfQuestionsSpinner = new JSpinner();
		nbOfQuestionsSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Settings.QuestionsPerTest = (Integer) nbOfQuestionsSpinner.getValue();
			}
		});
		nbOfQuestionsSpinner.setValue(Settings.QuestionsPerTest);
		
		final JComboBox whatToAnswerCombo = new JComboBox();
		
		JPanel proposalsPanel = new JPanel();
		
		JLabel lblChooseBetweenProposed = new JLabel("Choose between proposed:");
		
		JLabel lblWhatToShow = new JLabel("What to show as question ?");
		GroupLayout groupLayout = new GroupLayout(frmSettings.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(proposalsPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(cbJLPT1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbJLPT2))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(lblTimer)
							.addGap(18)
							.addComponent(timerSpinner, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(cbJLPT4)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cbJLPT5))
						.addComponent(cbJLPT0, Alignment.LEADING)
						.addComponent(allowHarderKanjis, Alignment.LEADING)
						.addComponent(allowEasierKanjis, Alignment.LEADING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(chckbxUsePondaratesRandom))
						.addComponent(chckbxTryToTrap, Alignment.LEADING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(lblNumberOfQuestions)
							.addGap(18)
							.addComponent(nbOfQuestionsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(14)
							.addComponent(chckbxIncludingRandomMeanings))
						.addComponent(lblChooseBetweenProposed, Alignment.LEADING)
						.addComponent(lblWhatToShow, Alignment.LEADING)
						.addComponent(whatToAnswerCombo, Alignment.LEADING, 0, 274, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(cbJLPT0)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbJLPT1)
						.addComponent(cbJLPT2))
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(cbJLPT4)
								.addComponent(cbJLPT5)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(26)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTimer)
								.addComponent(timerSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumberOfQuestions)
						.addComponent(nbOfQuestionsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(24)
					.addComponent(allowHarderKanjis)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(allowEasierKanjis)
					.addGap(6)
					.addComponent(chckbxUsePondaratesRandom)
					.addGap(6)
					.addComponent(chckbxTryToTrap)
					.addGap(6)
					.addComponent(chckbxIncludingRandomMeanings)
					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
					.addComponent(lblChooseBetweenProposed)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(whatToAnswerCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addComponent(lblWhatToShow)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(proposalsPanel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		proposalsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		for(InfoType info : InfoType.values()) {
			JCheckBox cb = new JCheckBox(info.getLabel());
			cb.setName(info.toString());
			proposalsPanel.add(cb);			
			cb.setSelected(Settings.InTestShow.get(info));
			cb.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					try {
						JCheckBox changedCb = ((JCheckBox)arg0.getSource());
						InfoType changedInfo = InfoType.valueOf(changedCb.getName());
						Settings.InTestShow.put(changedInfo, changedCb.isSelected());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			whatToAnswerCombo.addItem(info);
			whatToAnswerCombo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Settings.AsResponsePropose = (InfoType) whatToAnswerCombo.getSelectedItem();
				}
			});
			whatToAnswerCombo.setSelectedItem(Settings.AsResponsePropose);
		}
		
		frmSettings.getContentPane().setLayout(groupLayout);
	}
}
