package core;

import gui.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
	private static User user = null;
	
	public static void createUser(String name) {
		try {
			//new File(M.essage("UserManager.usersFilesPath") + name + M.essage("UserManager.usersFilesExtension")).createNewFile();
			Logger.log(Settings.getAppDatasPath() +  "\\" + name + M.essage("UserManager.usersFilesExtension"));
			new File(Settings.getAppDatasPath() +  "\\" + name + M.essage("UserManager.usersFilesExtension")).createNewFile();
			
			User newUser = new User();
			Integer id = (int) (Math.random() * 100000);
			newUser.setId(id);	
			newUser.setName(name);
			
			//newUser.saveToDisk(M.essage("UserManager.usersFilesPath") + name + M.essage("UserManager.usersFilesExtension"));
			newUser.saveToDisk(Settings.getAppDatasPath() +  "\\" + name + M.essage("UserManager.usersFilesExtension"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		Logger.log("setting user \"" + user.getName() + "\"");
		UserManager.user = user;
	}

	public static List<String> getUsersList() {
		ArrayList<String> users= new ArrayList<String>();
		
		//File folder = new File(M.essage("UserManager.usersFilesPath"));
		File folder = new File(Settings.getAppDatasPath());
		
		if(folder.exists()) { 
		File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if(listOfFiles[i].isFile()) {
					if(listOfFiles[i].getName().endsWith(M.essage("UserManager.usersFilesExtension"))) {
						String fname = listOfFiles[i].getName(); 
						users.add(fname.substring(0, fname.length() - M.essage("UserManager.usersFilesExtension").length()));
					}
				}
			}
		}
		return users;
	}

	public static User loadUser(String userName) throws Exception {
		//return User.readFromDisk(M.essage("UserManager.usersFilesPath") + userName + M.essage("UserManager.usersFilesExtension"));
		return User.readFromDisk(Settings.getAppDatasPath() + "\\" + userName + M.essage("UserManager.usersFilesExtension"));
	}
}
