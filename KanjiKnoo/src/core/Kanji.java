package core;

import java.util.ArrayList;
import java.util.HashMap;

import users.UserManager;

import dictionary.DictionaryType;

/**
 * Represents a Kanji with all its informations, like the strokes count, the differents readings, .. 
 * @author knoodrake
 *
 */
public class Kanji implements Comparable<Kanji> {
	private String ucs; // main unicode ISO encoding

	private String jis208; // Main +6K kanjis
	private String jis201; // 3 pontctuations
	private String jis212; // additional kanjis
	private String jis213; // additional kanjis

	private Integer jlpt; //
	private Integer grade; //

	private Integer strokeCount; //
	private Integer frequence; //

	private String literal; //
	
	private ArrayList<String> onReading = new ArrayList<String>();
	private ArrayList<String> kunReading = new ArrayList<String>();
	private ArrayList<String> meaning = new ArrayList<String>();

	private HashMap<DictionaryType, Integer> dictRefs = new HashMap<DictionaryType, Integer>();
	private HashMap<DictionaryType, Integer> radicals = new HashMap<DictionaryType, Integer>();
	
	public ArrayList<String> getMeanings() {
		return meaning;
	}

	/**
	 * Returns a list of Kanji meanings joined by given separator
	 * @param separator
	 * @return String
	 */
	public String getJoinedMeanings(String separator) {
		if(meaning.isEmpty())
			return "";
		StringBuilder sb = new StringBuilder();
		for(String s : meaning) {			
			sb.append(separator);
			sb.append(s);
		}
		return sb.toString().substring(1);
	}
	
	/**
	 * Returns a meaning of Kanji by index
	 * @param index
	 * @return String
	 * @throws IndexOutOfBoundsException
	 */
	public String getMeaning(Integer index) throws IndexOutOfBoundsException {
		if (meaning.size() > index) {
			return meaning.get(index);
		} else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Add a meaning to the Kanji
	 * @param meaning
	 */
	public void addMeaning(String meaning) {
		this.meaning.add(meaning);
	}

	/**
	 * Returns the JLPT level of the kanji
	 * @see <a href="http://en.wikipedia.org/wiki/Japanese_Language_Proficiency_Test">JLPT on wikipedia</a>
	 * @return JLPT Level, from 0 to 5
	 */
	public Integer getJlpt() {
		return (jlpt != null) ? jlpt : 0;
	}

	/**
	 * Set the JLPT level of the kanji
	 * @see <a href="http://en.wikipedia.org/wiki/Japanese_Language_Proficiency_Test">JLPT on wikipedia</a>
	 * @param jlpt, from 0 to 5
	 */
	public void setJlpt(Integer jlpt) {
		this.jlpt = jlpt;
	}

	/**
	 * Return the scholar grade where the Kanji is learned by japanese students  
	 * @return Scholar grade
	 */
	public Integer getGrade() {
		return (grade != null) ? grade : 0;
	}

	/**
	 * Set the schoolar grade where the Kanji is learned by japanese students
	 * @param grade
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	/**
	 * Return the number of strokes needed to draw the Kanji
	 * @return strokes count
	 */
	public Integer getStrokeCount() {
		return (strokeCount != null) ? strokeCount : 0;
	}

	/**
	 * Set the number of strokes needed to draw the Kanji
	 * @param strokeCount
	 */
	public void setStrokeCount(Integer strokeCount) {
		this.strokeCount = strokeCount;
	}

	/**
	 * Returns the frequence of use of the Kanji in japanese language
	 * @return the rank
	 */
	public Integer getFrequence() {
		return (frequence != null) ? frequence : 0;
	}

	/**
	 * Set the frequence of use of the Kanji in japanese language
	 * @param frequence
	 */
	public void setFrequence(Integer frequence) {
		this.frequence = frequence;
	}

	/**
	 * Returns the Kanji character itself (in Unicode)
	 * @return the unicode character
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Set the Kanji character itself (in Unicode)
	 * @param literal the unicode character
	 */
	public void setLiteral(String literal) {
		this.literal = literal;
	}

	/**
	 * Returns the list of all <i>on</i> readings of the Kanjis
	 * @return list of the Kanji <i>on</i> readings
	 */
	public ArrayList<String> getOnReading() {
		return onReading;
	}

	/**
	 * Set the complete list of the kanjis <i>on</i> readings 
	 * @param onReading list of unicode strings, Katakana or Hiragana
	 */
	public void setOnReading(ArrayList<String> onReading) {
		this.onReading = onReading;
	}
	
	public void addOnReading(String onReading) {
		this.onReading.add(onReading);
	}

	public ArrayList<String> getKunReading() {
		return kunReading;
	}
	
	public String getJoinedKunReadings(String separator) {
		if(kunReading.isEmpty())
			return "";
		StringBuilder sb = new StringBuilder();
		for(String s : kunReading) {			
			sb.append(separator);
			sb.append(s);
		}
		return sb.toString().substring(1);
	}
	
	public String getJoinedOnReadings(String separator) {
		if(onReading.isEmpty())
			return "";
		StringBuilder sb = new StringBuilder();
		for(String s : onReading) {			
			sb.append(separator);
			sb.append(s);
		}
		return sb.toString().substring(1);
	}

	public void setKunReading(ArrayList<String> kunReading) {
		this.kunReading = kunReading;
	}
	
	public void addKunReading(String kunReading) {
		this.kunReading.add(kunReading);
	}

	public void setMeaning(ArrayList<String> meaning) {
		this.meaning = meaning;
	}

	@Override
	public String toString() {
		return literal;
	}

	public String getUcs() {
		return ucs;
	}

	public void setUcs(String ucs) {
		this.ucs = ucs;
	}

	public String getJis208() {
		return (jis208 != null ? jis208 : "");
	}

	public void setJis208(String jis208) {
		this.jis208 = jis208;
	}

	public String getJis201() {
		return (jis201 != null ? jis201 : "");
	}

	public void setJis201(String jis201) {
		this.jis201 = jis201;
	}

	public String getJis212() {
		return (jis212 != null ? jis212 : "");
	}

	public void setJis212(String jis212) {
		this.jis212 = jis212;
	}

	public String getJis213() {
		return (jis213 != null ? jis213 : "");
	}

	public void setJis213(String jis213) {
		this.jis213 = jis213;
	}

	public boolean equals(Object obj) {
		if(obj instanceof Kanji) {
			if(((Kanji)obj).getUcs().equals(getUcs())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int compareTo(Kanji arg0) {
		return (toString()).compareTo(arg0.toString());
	}

	public Integer getDictRef(DictionaryType type) {
		return getDictRefs().get(type);
	}
	
	public void addDictRef(String type, Integer ref) {
		addDictRef(DictionaryType.valueOf(type), ref);
	}
	
	public void addDictRef(DictionaryType type, Integer ref) {
		getDictRefs().put(type, ref);
	}
	
	public HashMap<DictionaryType, Integer> getDictRefs() {
		return dictRefs;
	}

	public void setDictRefs(HashMap<DictionaryType, Integer> dictRefs) {
		this.dictRefs = dictRefs;
	}

	public Integer getRadicalRef(DictionaryType type) {
		return getRadicals().get(type);
	}
	
	public void addRadical(String type, Integer ref) {
		addRadical(DictionaryType.valueOf(type), ref);
	}
	
	public void addRadical(DictionaryType type, Integer ref) {
		getRadicals().put(type, ref);
	}
	
	public HashMap<DictionaryType, Integer> getRadicals() {
		return radicals;
	}

	public void setRadicals(HashMap<DictionaryType, Integer> radicals) {
		this.radicals = radicals;
	}

	public Double getWeight() {
		return UserManager.getUser().getKanjiWeight(this);
	}
}
