package core;

import gui.Logger;
import gui.MessageBox;
import gui.QuestionMessageBox;
import gui.QuestionMessageBox.ButtonTypes;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class Settings {

	public static Boolean JLPT0 = false;
	public static Boolean JLPT1 = false;
	public static Boolean JLPT2 = false;
	public static Boolean JLPT3 = true;
	public static Boolean JLPT4 = true;
	public static Boolean JLPT5 = true;
	public static Boolean AllowTrap = true;
	public static Boolean AllowFalseAnswers = true;
	public static Integer Grade = 0;
	public static Integer QuestionsPerTest = 20;
	public static Integer Timer = 30;
	public static Boolean AllowToProposeHarderKanjis = false;
	public static Boolean AllowToProposeEasierKanjis = true;
	public static Boolean UsePondaration = true;
	
	public static HashMap<InfoType, Boolean> InTestShow = new HashMap<InfoType, Boolean>();
	
	public static InfoType AsResponsePropose = InfoType.KANJI;
	
	public static void initialize() {
		for(InfoType info : InfoType.values()) {
			InTestShow.put(info, false);
		}
		InTestShow.put(InfoType.KANJI, true);
	}

	public static Integer getMaxJLPT() {
		if (JLPT0) return 0;
		if (JLPT1) return 1;
		if (JLPT2) return 2;
		if (JLPT3) return 3;
		if (JLPT4) return 4;
		if (JLPT5) return 5;
		else	
			return 0;
	}
	
	/**
	 * TODO
	 * @return path where the app stores his stuff
	 */
	public static String getAppDatasPath() {
		String cwd = UserManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			cwd = URLDecoder.decode(cwd, "UTF-8").substring(1);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Logger.log(cwd + M.essage("UserManager.configFileName"));
		if(new File(cwd + M.essage("UserManager.configFileName")).exists()) {
			Logger.log("file exists");
		} else {
			Logger.log("file does not exists, todo: ask where to create users and create one");
			if(!new File(cwd).canWrite()) {
				new MessageBox("Ok then..", "Error: The .jar have to be launched from a writable directory\n"
						+ "in order to create the configuration file. Sorry, it was the simpliest solution.");
				System.exit(0);
			} else {
				File f = new File(cwd + M.essage("UserManager.configFileName"));
				//try {
					QuestionMessageBox q = new QuestionMessageBox("Where to store users ?", cwd + "kanjiknoo_users");
					if(q.buttonPressed == ButtonTypes.OK) {
						Logger.log("ok button pressed");
					} else {
						Logger.log("none or cancel button pressed");
					}
					Logger.log("text given:" + q.getAnswerText());
					//f.createNewFile();
				//} catch (IOException e) {
				//	new MessageBox("Damned !", "Unable to create config file. Will now die.");
				//	System.exit(1);
				//}
			}
		}
		
		return "";
	}
}
