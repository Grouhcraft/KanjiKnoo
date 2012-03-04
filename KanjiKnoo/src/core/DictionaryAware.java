package core;

import javax.swing.JLabel;

public abstract class DictionaryAware {
	
	protected abstract void update();
	protected abstract JLabel getKanjiLabel();
	
	protected DictionaryAware() {
	}
	
	protected abstract void initialize();
	
	/**
	 * Set the current Kanji of dictionary
	 */
	public void updateKanjiLabel () {
		getKanjiLabel().setText(Dictionary.getInstance().current().toString());
	}
	
	/**
	 * set the given kanji
	 * @param k kanji to show
	 */
	public void setKanji (Kanji k) {
		getKanjiLabel().setText(k.toString());
	}
	
}
