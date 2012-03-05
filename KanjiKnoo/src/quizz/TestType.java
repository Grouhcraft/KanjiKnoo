package quizz;

/**
 * The different types of kanji Quiz
 * @author knoodrake
 * @deprecated
 */
public enum TestType {
	/**
	 * Guess the correct Kanji among proposed ones, regarding of the given meaning(s)
	 */
	KANJI_FROM_MEANINGS,
	
	/**
	 * Guess the correct Meaning(s) among those proposed, regarding of the given Kanji 
	 */
	MEANINGS_FROM_KANJI,
	
	/**
	 * Guess the correct Kanji among those proposed, regarding of the given readings
	 */
	KANJI_FROM_READINGS,
	
	/**
	 * Guess the correct reading of the given Kanji from those proposed
	 */
	READING_FROM_KANJI,
	
	/**
	 * Mixes both guess of Kanji and guess of Meaning
	 * @see TestType#KANJI_FROM_MEANINGS
	 * @see TestType#MEANINGS_FROM_KANJI
	 */
	MIXED_KANJI_AND_MEANING,
	
	/**
	 * Mixes both guess of Kanji and guess of their readings
	 * @see TestType#KANJI_FROM_READINGS
	 * @see TestType#READING_FROM_KANJI
	 */
	MIXED_KANJI_AND_READING,
	
	/**
	 * Mixes all available quiz types
	 * @see TestType#KANJI_FROM_MEANINGS
	 * @see TestType#KANJI_FROM_READINGS
	 * @see TestType#MEANINGS_FROM_KANJI
	 * @see TestType#READING_FROM_KANJI
	 */
	MIXED_ALL,
}
