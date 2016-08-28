package de.studware.main;

import java.io.File;

import javax.swing.JFileChooser;

public class FolderCreationAndPath {

	public Boolean makeFolder(String name, String path) {
		File folder = new File(path + name);
		if (!folder.exists()) {
			folder.mkdir();
			return true;
		} else {
			return false;
		}
	}

	public File chooseStartDirectory(File currentDirectory) {
		int fileChosen;
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(currentDirectory);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChosen = chooser.showOpenDialog(null);
		if ((chooser.getSelectedFile() != null) && (fileChosen == JFileChooser.APPROVE_OPTION)) {
			return chooser.getSelectedFile();
		} else {
			return null;
		}
	}
}
