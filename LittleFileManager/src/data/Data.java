package data;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;

import lang.Language;

public class Data {
	private ResourceBundle rBundle;
	private File startFolder, newFolder;
	private ArrayList<Wordpair> wortListe;
	private ArrayList<File> oldFolders, oldFiles, newFolders, newFiles;

	public Data() {
		Language langu = new Language();
		rBundle = langu.waehleSprache();
		startFolder = new File(System.getProperty("user.home"));
		newFolder = null;
		wortListe = new ArrayList<>();
		oldFolders = new ArrayList<>();
		oldFiles = new ArrayList<>();
		newFolders = new ArrayList<>();
		newFiles = new ArrayList<>();
	}

	public String getText(String key) {
		return rBundle.getString(key);
	}

	public File getStartFolder() {
		return startFolder;
	}

	public void setStartFolder(File startFolder) {
		this.startFolder = startFolder;
	}

	public File getNewFolder() {
		return newFolder;
	}

	public void setNewFolder(File newFolder) {
		this.newFolder = newFolder;
	}

	public ArrayList<Wordpair> getWortListe() {
		return wortListe;
	}
	
	public ArrayList<File> getOldFolders() {
		return oldFolders;
	}
	
	public ArrayList<File> getOldFiles() {
		return oldFiles;
	}
	
	public ArrayList<File> getNewFolders() {
		return newFolders;
	}
	
	public ArrayList<File> getNewFiles() {
		return newFiles;
	}
	
	public void clearFilesAndFolders() {
		oldFolders.clear();
		oldFiles.clear();
		newFolders.clear();
		newFiles.clear();
	}

}
