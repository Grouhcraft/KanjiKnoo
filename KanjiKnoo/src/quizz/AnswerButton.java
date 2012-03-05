package quizz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import core.Kanji;

public class AnswerButton extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 912936788016413721L;
	
	protected List<AnswerChoosenEventListener> _listeners = new ArrayList<AnswerChoosenEventListener>();
	protected Kanji kanji;
	
	protected int maxLength = 50;

	
	public synchronized void addEventListener(
			AnswerChoosenEventListener listener) {
		_listeners.add(listener);
	}

	public synchronized void removeEventListener(
			AnswerChoosenEventListener listener) {
		_listeners.remove(listener);
	}

	protected synchronized void fireEvent() {
		AnswerChoosenEvent event = new AnswerChoosenEvent(this);
		Iterator<AnswerChoosenEventListener> i = _listeners.iterator();
		while (i.hasNext()) {
			i.next().handleAnswerChoosenEvent(event);
		}
	}
	
	public AnswerButton(Kanji kanji) {
		super();
		this.kanji = kanji;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEvent();
			}
		});
	}
	public AnswerButton(String string) {
		super();
		setText(string);
		this.kanji = null;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEvent();
			}
		});
	}

	public Kanji getKanji() {
		return kanji;
	}

	public void setKanji(Kanji kanji) {
		this.kanji = kanji;
	}
	

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
		setText(getText());
	}
	
	@Override
	public void setText(String text) {
		super.setText(text);
		if(getText().length() > maxLength) {
			super.setText(getText().substring(0, maxLength - 3) + "...");
		}
	}
}
