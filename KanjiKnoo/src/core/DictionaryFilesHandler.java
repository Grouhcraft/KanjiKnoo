package core;

import gui.Logger;

import java.util.zip.GZIPInputStream;

public class DictionaryFilesHandler {
	private static DictionaryFilesHandler instance = null;
	private GZIPInputStream dictionary;
	
	public static DictionaryFilesHandler getInstance() {
		if(instance == null) {
			instance = new DictionaryFilesHandler();
		}
		return instance;
	}
	
	private DictionaryFilesHandler() {
		try {
			Logger.log("opening dictionary file.."); //$NON-NLS-1$
			setDictionary(new GZIPInputStream(this.getClass().getResourceAsStream("/ressources/kanjidic2.xml.gz")));
			Logger.log("Done."); //$NON-NLS-1$
		} catch (Exception e) {
			Logger.log("Unable to open the dictionary file. Thrown excpetion msg: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	public GZIPInputStream getDictionary() {
		return dictionary;
	}

	public void setDictionary(GZIPInputStream dictionary) {
		this.dictionary = dictionary;
	}
}
