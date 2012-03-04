package core;
/**
 * Criterias used by {@link #Condition}
 * @see Dictionary#queryKanjis(Condition)
 */
public enum Criterias {
	
	/**
	 * Find every Kanjis matching the given meaning String
	 * @see DictionaryCore#queryKanjis(Condition) 
	 */
	MEANING,
	
	/**
	 * Find every Kanjis matching the given <i>kun</i> reading
	 * @see DictionaryCore#queryKanjis(Condition)
	 */
	KUNREADING,
	
	/**
	 * Find every Kanjis matching the given <i>on</i> reading
	 * @see DictionaryCore#queryKanjis(Condition)
	 */	
	ONREADING,
	
	/**
	 * Find every (but, in fact, only one since it's used as identifier) Kanjis
	 * matching the given ucs codepoint string
	 * @see DictionaryCore#queryKanjis(Condition)
	 */
	UCS,
	
	/**
	 * Find every (but, in fact, only one since it represents the character itself)
	 * Kanjis matching the given literal string
	 * @see DictionaryCore#queryKanjis(Condition)
	 */
	LITERAL,
	
	/**
	 * Find every Kanjis which matches at least one of the meanings given as an <type>ArrayList<String></type>
	 * @see DictionaryCore#queryKanjis(Condition)
	 */
	ONE_OF_MEANINGS,
	
	/**
	 * Find every Kanjis which matches at least one of the meanings of the given {@link #Kanji}'s 
	 * meanings, ordering the result by the number of shared meanings with the given <class>Kanji</class>
	 * @see DictionaryCore#queryKanjis(Condition)
	 */
	MOST_SHARED_MEANINGS_WITH_KANJI,
	
	/**
	 * Find every Kanjis matching the given <i>JLPT</i> level
	 * @see DictionaryCore#queryKanjis(Condition)
	 */	
	JLPT,
	
	JLPT_GREATER_OR_EQUALS,
	
	SELECT_RANDOM,
	
	SELECT_RANDOM_PONDARATED,
		
	//TODO implements theses criteria
	STROKES_COUNT,
	STROKES_COUNT_BETWEEN,
	ONE_OF_KUNREADING,
	ONE_OF_ONREADING,  
	
	
	
}
