package copyReplace;

import java.io.File;

import data.Data;
import data.Wordpair;
import de.studware.main.Screen;

public class CopyAndReplace extends Thread {
	Screen screen;
	Data data;
	FileOperations fileOperation;
	
	public CopyAndReplace(Screen screen, Data data) {
		this.screen = screen;
		this.data = data;
		fileOperation = new FileOperations(data);
	}
	
	@Override
	public void run() {
		int copied = 0;
		screen.progressBar.setValue(0);
		data.clearFilesAndFolders();
		fileOperation.listAllFiles(data.getStartFolder().getAbsoluteFile());
		screen.progressBar.setMaximum(data.getOldFolders().size() + data.getOldFiles().size());
		
		// Folders
		changeFolderPaths();
		for (int i = 0; i < data.getNewFolders().size(); i++) {
			fileOperation.copyFile(data.getOldFolders().get(i), data.getNewFolders().get(i));
			copied++;
			if ((i % 3) == 0) {
				screen.progressBar.setValue(copied);
			}
		}

		// Files
		changeFilePaths();
		for (int i = 0; i < data.getNewFiles().size(); i++) {
			fileOperation.copyFile(data.getOldFiles().get(i), data.getNewFiles().get(i));
			copied++;
			if ((i % 3) == 0) {
				screen.progressBar.setValue(copied);
			}
		}

		screen.progressBar.setValue(copied);
		screen.btStart.setEnabled(true);
		screen.btCreate.setEnabled(true);
		screen.lbCopyInfo.setText(data.getText("lbCopyInfoDone"));
		screen.tfFolderNameNew.setText("");
		screen.lbFolderInfo.setText(data.getText("lbFolderInfo"));
		data.setNewFolder(null);
	}
	
	private void changeFolderPaths() {
		for (File folder : data.getOldFolders()) {
			data.getNewFolders().add(applyFolderChanges(folder));
		}
	}

	private File applyFolderChanges(File folder) {
		String parent = folder.getParent();
		boolean changedParent = false;
		for (int i = 0; i < data.getNewFolders().size(); i++) {
			if (parent.equals(data.getOldFolders().get(i).getAbsolutePath())) {
				parent = data.getNewFolders().get(i).getAbsolutePath();
				changedParent = true;
			}
		}
		if (!changedParent) {
			parent = parent.replace(data.getStartFolder().getAbsolutePath(), data.getNewFolder().getAbsolutePath());
		}
		return new File(parent + "\\" + applyNameChange(folder.getName(), false));
	}

	private void changeFilePaths() {
		for (File file : data.getOldFiles()) {
			data.getNewFiles().add(applyFileChanges(file));
		}
		fileOperation.checkFilenamesUnique();
	}

	private File applyFileChanges(File file) {
		String parent = file.getParent();
		boolean changedParent = false;
		for (int i = 0; i < data.getNewFolders().size(); i++) {
			if (parent.equals(data.getOldFolders().get(i).getAbsolutePath())) {
				parent = data.getNewFolders().get(i).getAbsolutePath();
				changedParent = true;
			}
		}
		if (!changedParent) {
			parent = parent.replace(data.getStartFolder().getAbsolutePath(), data.getNewFolder().getAbsolutePath());
		}
		return new File(parent + "\\" + applyNameChange(file.getName(), true));
	}
	
	private String applyNameChange(String filename, boolean isFile) {
		String extension = null;
		String name = null;
		if (isFile) {
			extension = fileOperation.getExtension(filename);
			name = filename.replace(extension, "");
		} else {
			name = filename;
		}
		
		// Check for all changes
		for (Wordpair wortpaar : data.getWortListe()) {
			String altesWort = wortpaar.getWortAlt();
			String neuesWort = wortpaar.getWortNeu();
			
			// Check different conditions with wildcard
			if (altesWort.equals("*")) {
				name = neuesWort;
			} else if (altesWort.contains("*")) {
				if (fileOperation.wildCardMatch(name, altesWort)) {
					String[] teile = altesWort.split("\\*");
					String[] neueTeile = neuesWort.split("\\*");
					String neu = "";
					for (int i = 0; i < teile.length; i++) {
						teile[i] = teile[i].replace("(", "[(]").replace(")", "[)]");
						int ende = name.indexOf(teile[i]) + teile[i].length();
						name = name.replaceFirst(teile[i], neueTeile[i]);
						neu = neu + name.substring(0, ende);
						name = name.substring(ende);
					}
					name = neu + name;
				}
				
			} else if (name.contains(altesWort)) {
				name = name.replace(altesWort, neuesWort);
			}
		}
		if (isFile) {
			return name.replaceAll("\\s+", " ") + extension;
		} else {
			return name.replaceAll("\\s+", " ");
		}
	}
}
