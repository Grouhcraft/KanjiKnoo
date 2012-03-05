package quizz;

import java.awt.Font;

import core.Kanji;

public class KanjiButton extends AnswerButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 516558746122465012L;

	public KanjiButton(Kanji kanji) {
		super(kanji);
		setText(kanji.toString());
		setFont(new Font("Meiryo", Font.PLAIN, 22));
	}
}
