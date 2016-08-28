package copyReplace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;

import data.Data;

public class FileOperations {
	Data data;
	
	public FileOperations(Data data) {
		this.data = data;
	}

	public void listAllFiles(File topOrdner) {
		File[] filesInFolder = topOrdner.listFiles();
		
		for (File file : filesInFolder) {
			if (file.isFile()) {
				data.getOldFiles().add(file);
			} else if (file.isDirectory()) {
				data.getOldFolders().add(file);
				listAllFiles(file);
			}
		}
	}

	public void checkFilenamesUnique() {
		ArrayList<File> filelist = new ArrayList<>(data.getNewFiles());
		HashSet<File> list = new HashSet<>();
		data.getNewFiles().clear();
		
		for (File file : filelist) {
			boolean nichtDrinnen = true;
			if (list.add(file)) {
				nichtDrinnen = false;
				data.getNewFiles().add(file);
			} else {
				File dok = file;
				int durchlauf = 1;
				while (nichtDrinnen) {
					dok = makeNameUnique(file, durchlauf);
					if (list.add(dok)) {
						nichtDrinnen = false;
						data.getNewFiles().add(dok);
					}
					durchlauf++;
				}
			}
		}
	}

	private File makeNameUnique(File file, int durchlauf) {
		File parent = file.getParentFile();
		String extension = getExtension(file.getName());
		String name = file.getName().replace(extension, "");
		return new File(parent + "\\" + name + durchlauf + extension);
	}

	public String getExtension(String filename) {
		return filename.substring(filename.lastIndexOf('.'));
	}
	
	public boolean wildCardMatch(String text, String pattern) {
		String[] parts = pattern.split("\\*");
		for (String part : parts) {
			int pos = text.indexOf(part);
			// Card not in the text
			if (pos == -1) {
				return false;
			}
			text = text.substring(pos + part.length());
		}
		return true;
	}

	public void copyFile(File source, File dest) {
		try {
			if (!dest.exists()) {
				Files.copy(source.toPath(), dest.toPath());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
