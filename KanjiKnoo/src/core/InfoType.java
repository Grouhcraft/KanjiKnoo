package core;

public enum InfoType {
	KANJI("Kanji", 1),
	MEANING("Meaning", 2),
	KUNREADING("Kun Reading", 3),
	ONREADING("On Reading", 4);
	
	private final String label;
	private final Integer index;
	
	InfoType(String str, Integer index) {
		this.label = str;
		this.index = index;
	}

	public String getLabel() {
		return label;
	}
	
	public Integer toInteger() {
		return index;
	}
}
