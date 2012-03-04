package core;

import gui.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import misc.Pair;

public class Dictionary {
	
	private List<Kanji> kanjis; 
	private Integer index = 0;
	@SuppressWarnings("unused")
	private Connection db = null;
	private static Dictionary _instance = null;
	
	public int getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	private Dictionary () {
		DictionaryFilesHandler files = DictionaryFilesHandler.getInstance();
		XMLDictionaryParser reader = new XMLDictionaryParser();
		
		Logger.log("prepare to read dictionary..");
		kanjis = reader.readDictionary(files);
		Logger.log("reading finished.");
		Logger.log("Kanjis readen: " + kanjis.size());
		
		try {
		//	db = DatabaseCreator.createDatabase(kanjis);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public int getRandomIndex() {
		int random = (int)(Math.random() *  kanjis.size());
		return random;
	}
	
	public Kanji getRandomKanji() {
		return kanjis.get(getRandomIndex());
	}
	
	public Kanji random() {
		index = getRandomIndex();
		return current();
	}
	
	public Kanji next() {
		if(index + 1 < kanjis.size()) {
			index++;
		} else {
			index = 0;
		}
		return current();
	}
	
	public Kanji previous () {
		if(index > 0) {
			index--;
		} else {
			index = kanjis.size() - 1;
		}
		return current();
	}
	
	public Kanji current () {
		return kanjis.get(index);
	}
	
	public List<Kanji> queryKanjis(Condition condition) throws Exception {
		return queryKanjis(new Condition[] { condition });
	}
	
	public List<Kanji> queryKanjis(Condition[] conditions) throws Exception {
		List<Kanji> kanjis = filterKanjisList(conditions[0], this.kanjis);
		for(int i=1; i<conditions.length; i++) {
			if(kanjis != null) {
				kanjis = filterKanjisList(conditions[i], kanjis);
			} else {
				continue;
			}
		}
		return kanjis;
	}
	
	/**
	 * For debug purposes
	 */
	public static void printKanjiList(List<Kanji> kanjis) {
		Logger.log("**************************");
		Logger.log("LIST OF #" + kanjis.size() + "# Kanjis:");
		if(kanjis.size() > 8 ) {
			for(int i=0; i<3; i++) {
				Logger.log("# ucs:" + kanjis.get(i).getUcs() + ", meanings: \"" + kanjis.get(i).getJoinedMeanings("|"));
			}
			Logger.log("# ... + " + (kanjis.size() - 6) + " more Kanjis. Last ones:");
			for(int i=0; i<3; i++) {
				Logger.log("# ucs:" + kanjis.get(kanjis.size()-(i+1)).getUcs() + ", meanings: \"" + kanjis.get(kanjis.size()-(i+1)).getJoinedMeanings("|"));
			}
		}
		else {
			for(Kanji kanji : kanjis) {
				Logger.log("# ucs:" + kanji.getUcs() + ", meanings: \"" + kanji.getJoinedMeanings("|"));
			}
		}
		Logger.log("**************************");
	}
	
	/**
	 * Select a number of unique randomly picked indexes in a list
	 * @param number of indexes wanted
	 * @param list
	 * @return A list containing the picked indexes
	 */
	private ArrayList<Integer> selectRandomIndexesFromList (Integer number, List<?> list) {
		ArrayList<Integer> selected = new ArrayList<Integer>();
		Integer needed = number;
		Integer available = list.size();
		Random rand = new Random();
		while (selected.size() < number) {
		   if( rand.nextDouble() < needed.doubleValue() / available.doubleValue() ) {
		      selected.add(available-1);
		      needed--;
		   }
		   available--;
		}
		
		return selected;
	}
	
	/**
	 * TODO A TESTER !!
	 * @param number
	 * @param kanjis
	 * @return
	 */
	private ArrayList<Integer> selectPondaratedRandomIndexesFromList(Integer number, List<Kanji> kanjis) {
		ArrayList<Pair<Double, Kanji>> weighedKanjis = new ArrayList<Pair<Double,Kanji>>();
		
		Double totalWeight = 0.;
		for(Kanji k : kanjis) {
			totalWeight += k.getWeight();
			weighedKanjis.add(new Pair<Double, Kanji>(totalWeight, k));
		}
		ArrayList<Integer> selected = new ArrayList<Integer>();
		Integer needed = number;
	
		while (selected.size() < number) {
			Double randDouble = Math.random() * totalWeight;
			for(Pair<Double, Kanji> p : weighedKanjis) {
				if(randDouble > p.getLeft()) {
					selected.add(kanjis.indexOf(p.getRight()));
					needed--;
			
					for(Pair<Double, Kanji> pp: weighedKanjis.subList(weighedKanjis.indexOf(p), weighedKanjis.size()-1)) {
						pp.setLeft(pp.getLeft()- p.getRight().getWeight());
					}
					
					weighedKanjis. remove(p);
					continue;
				}
			}
		}
		
		return selected;
	}
	
	@SuppressWarnings("unchecked")
	private List<Kanji> filterKanjisList(Condition criterias, List<Kanji> kanjis) throws Exception {
		ArrayList<Integer> indexList = new ArrayList<Integer>();
	
		switch (criterias.criteria) {
		case MOST_SHARED_MEANINGS_WITH_KANJI:
			ArrayList<String> meanings = ((Kanji)criterias.value).getMeanings();
			for(int i=0; i<kanjis.size(); i++) {
				if(!kanjis.get(i).equals((Kanji)criterias.value)) {
					ArrayList<String> kanjiMeanings = new ArrayList<String>(kanjis.get(i).getMeanings());
					SortedMap<Integer, Integer> orderedIndexes = new TreeMap<Integer, Integer>(new Comparator<Integer>() {
				        public int compare(Integer o1, Integer o2) {
				            return o2.compareTo(o1);
				        }
				    });
					kanjiMeanings.retainAll(meanings);
					if(kanjiMeanings.size() > 0) {
						orderedIndexes.put(i, kanjiMeanings.size());
					}
					for (Map.Entry<Integer, Integer> nextEntry : orderedIndexes.entrySet()) {
				        indexList.add(nextEntry.getKey());
				    }
				}
			}
			
			break;
		case ONE_OF_MEANINGS:
			ArrayList<String> meanings1 = (ArrayList<String>) criterias.value;
			for(int i=0; i<kanjis.size(); i++) {
				ArrayList<String> kanjiMeanings = new ArrayList<String>(kanjis.get(i).getMeanings());
				kanjiMeanings.retainAll(meanings1);
				if(kanjiMeanings.size() > 0) {
					indexList.add(i);
				}
			}
			break;
			
		case SELECT_RANDOM: 
			indexList = selectRandomIndexesFromList((Integer) criterias.value, kanjis);
			break;
		
		case SELECT_RANDOM_PONDARATED: 
			indexList = selectPondaratedRandomIndexesFromList((Integer) criterias.value, kanjis);
			break;
			
			
		case MEANING:
			for(int i=0; i<kanjis.size(); i++) {
				if(kanjis.get(i).getMeanings().contains((String)criterias.value)) {
					indexList.add(i);
				}
			}
			break;
		case LITERAL:
			for(int i=0; i<kanjis.size(); i++) {
				if(kanjis.get(i).getLiteral() == (String)criterias.value) {
					indexList.add(i);
				}
			}
			break;
		case JLPT_GREATER_OR_EQUALS:
			for(int i=0; i<kanjis.size(); i++) {
				if(kanjis.get(i).getJlpt() >= (Integer)criterias.value) {
					indexList.add(i);
				}
			}		
			break;
		case JLPT:
			for(int i=0; i<kanjis.size(); i++) {
				if(kanjis.get(i).getJlpt() == (Integer)criterias.value) {
					indexList.add(i);
				}
			}			
			break;
		case UCS:
			for(int i=0; i<kanjis.size(); i++) {
				if(kanjis.get(i).getUcs().equals((String)criterias.value)) {
					indexList.add(i);
				}
			}
			break;
		case KUNREADING:
			for(int i=0; i<kanjis.size(); i++) {
				if(kanjis.get(i).getKunReading().contains((String)criterias.value)) {
					indexList.add(i);
				}
			}
			break;
		case ONREADING:
			for(int i=0; i<kanjis.size(); i++) {
				if(kanjis.get(i).getOnReading().contains((String)criterias.value)) {
					indexList.add(i);
				}
			}
			break;
		default:
			throw new Exception("Invalid Kanji Query Criteria (value #" + criterias.criteria + ")");
		}
		if(!indexList.isEmpty()) {
			List<Kanji> kanjis1 = new ArrayList<Kanji>();
			for(Integer i : indexList) {
				kanjis1.add(kanjis.get(i));
			}
			return kanjis1;
		} else {
			return null;
		}
	}

	public int getIndexOf(Kanji findMyIndex) {
		for(int i=0; i<kanjis.size(); i++) {
			if(kanjis.get(i).equals(findMyIndex)) {
				return i;
			}
		}
		throw new IndexOutOfBoundsException();
	}

	public static Dictionary getInstance() {
		if(_instance == null) {
			_instance = new Dictionary();
		}
		return _instance;
	}
}
