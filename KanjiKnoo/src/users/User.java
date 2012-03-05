package users;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import tools.Logger;

import core.Kanji;

import dictionary.Condition;
import dictionary.Criterias;
import dictionary.Dictionary;

/**
 * Using to read, store, access and manage the user stuff, 
 * currently, it is used to store the username along with kanjis weights 
 */
public class User extends Object {
 
	private Integer id;
	private String name;
	private String filePath;
	public HashMap<Kanji, Double> kanjis = new HashMap<Kanji, Double>();
	
	protected double wrongAnswerWeight = 0.2;
	protected double goodAnswerWeight = 0.2;
	
	private String serialize() {
		String s = "";
		s = "[name:" + name + ","
				+ "id:" + id + ","
				+ "kanjis:{";
		for(Entry<Kanji, Double> e : kanjis.entrySet()) {
			s += ((Kanji)e.getKey()).getUcs() + ":" + e.getValue() + ","; 
		}
		s = s.substring(0, s.length()) + "}]";
		return s;
	}
	
	public void saveToDisk(String path) throws IOException {
		FileOutputStream fo = new FileOutputStream(path);
		fo.write(serialize().getBytes());
	}
	
	public static User readFromDisk(String path) throws IOException {
		StringBuilder text = new StringBuilder();
		Scanner scanner = new Scanner(new FileInputStream(path));
		while (scanner.hasNextLine()) {
			text.append(scanner.nextLine() + "\n");
		}
		scanner.close();
		User u = deserialize(text.toString());
		u.setFilePath(path);
		return u;
	}
	
	private String getFilePath() {
		return filePath;
	}
	
	private void setFilePath(String path) {
		filePath = path;
	}

	private static User deserialize(String serialized) {
		User user = new User();
		
		int start = serialized.indexOf("name:");
		int end = serialized.indexOf(",", start + 5);
		user.setName(serialized.substring(start + 5 ,end));
		Logger.log("reading user's name: \"" + user.name + "\"");
				
		start = serialized.indexOf("id:");
		end = serialized.indexOf(",", start + 3 );
		user.setId(Integer.parseInt(serialized.substring(start + 3,end)));
		Logger.log("reading user's id: \"" + user.id + "\"");
		
		start = serialized.indexOf("kanjis:{");
		end = serialized.indexOf("}", start + 8);
		Logger.log("reading user's serialized kanjisList: \"" + serialized.substring(start + 8, end) + "\"");
		String serializedKanjis = serialized.substring(start + 8, end);
		
		if(!serializedKanjis.isEmpty()) {
			for(String sKanji : serializedKanjis.split(",")) {
				String[] splittedKanji = sKanji.split(":");
				if(splittedKanji.length  == 2 ) {
					try {
						user.kanjis.put(
								(Dictionary.getInstance().queryKanjis(new Condition(Criterias.UCS, splittedKanji[0]))).get(0), 
								Double.parseDouble(splittedKanji[1])
								);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		return user;
	}
	
	public void correctAnswer(Kanji kanji) {
		Double value = (kanjis.get(kanji) != null) 
				? kanjis.get(kanji) + goodAnswerWeight 
				: goodAnswerWeight; 
		kanjis.put(kanji, value);
	}
	
	public void wrongAnswer(Kanji kanji) {
		Double value = (kanjis.get(kanji) != null) 
				? kanjis.get(kanji) - wrongAnswerWeight 
				: -wrongAnswerWeight; 
		kanjis.put(kanji, value);
	}
	
	public double getKanjiWeight(Kanji kanji) {
		if(kanjis.get(kanji) != null) {
			Logger.log("kanji " + kanji.getUcs() + " does have a weight of " + kanjis.get(kanji));
			return kanjis.get(kanji);
		} else {
			Logger.log("kanji " + kanji.getUcs() + " does not have any weight");
			return 0.;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
	
	@Override
	public String toString() {
		return name;
	}
	
	public Integer toInteger() {
		return id;
	}

	public void save() throws Exception {
		saveToDisk(getFilePath());
	}
}
