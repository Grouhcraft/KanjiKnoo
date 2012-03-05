package dictionary;
/**
 * Types of dictionaries used for Kanji entries 
 * and kanjis radicals entries.<br/>
 * {@link DictionaryType#classical} is the most used, often 
 * followed by the two <i>nelson</i>.<br/>
 * Indexes for those entries are Integers
 * @author knoodrake
 *
 */
public enum DictionaryType {
	nelson_c,
	nelson_n,
	halpern_njecd,
	halpern_kkld,
	heisig,
	gakken,
	oneill_names,
	oneill_kk,
	henshall,
	sh_kk,
	sakade,
	jf_cards,
	henshall3,
	tutt_cards,
	crowley,
	kanji_in_context,
	busy_people,
	kodansha_compact,
	maniette,
	classical,
}
