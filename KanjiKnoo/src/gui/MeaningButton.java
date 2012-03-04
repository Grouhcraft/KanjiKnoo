package gui;

import java.awt.Font;

import core.Kanji;

public class MeaningButton extends AnswerButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4412802048626761907L;
	
	public MeaningButton(Kanji kanji) {
		super(kanji);
		setText(kanji.getJoinedMeanings(", "));
		setFont(new Font("Meiryo", Font.PLAIN, 12));
	}
}
