package de.studware.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import copyReplace.CopyAndReplace;
import data.Data;
import data.Wordpair;

public class LittleFileManager implements ActionListener {
	Screen screen;
	FolderCreationAndPath folderCreation;
	Data data;

	public static void main(String[] args) {
		new LittleFileManager();
	}
	
	public LittleFileManager() {
		data = new Data();
		folderCreation = new FolderCreationAndPath();
		screen = new Screen(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == screen.btSearch) {
			File path = folderCreation.chooseStartDirectory(data.getStartFolder());
			if (path != null) {
				data.setStartFolder(path);
				screen.tfFolder.setText(data.getStartFolder().getPath());
			}
		}
		
		if (e.getSource() == screen.btCreate) {
			if (screen.tfFolderNameNew.getText().isEmpty()) {
				JOptionPane.showMessageDialog(new JFrame(), data.getText("ErrNoFolderDesc"), data.getText("ErrNoFolderTitle"), JOptionPane.ERROR_MESSAGE);
			} else if (screen.tfFolderNameNew.getText().length() > 240) {
				JOptionPane.showMessageDialog(new JFrame(), data.getText("ErrFolderToLongDesc"), data.getText("ErrFolderToLongTitle"), JOptionPane.ERROR_MESSAGE);
			} else if (!screen.tfFolderNameNew.getText().matches("(^.*[ÄäÜüÖößA-Za-z0-9-+_()'@.,&$%!=])")) {
				JOptionPane.showMessageDialog(new JFrame(), data.getText("ErrFolderCharaDesc"), data.getText("ErrFolderCharaTitle"), JOptionPane.ERROR_MESSAGE);
			} else {
				boolean success;
				File folder = data.getStartFolder().getParentFile();
				
				if (folder != null) {
					success = folderCreation.makeFolder(screen.tfFolderNameNew.getText(), folder.getPath() + "//");
					data.setNewFolder(new File(folder.getPath() + "//" + screen.tfFolderNameNew.getText()));
				} else {
					// If C:\\ is the last possible instance
					success = folderCreation.makeFolder(screen.tfFolderNameNew.getText(), data.getStartFolder().getPath());
					data.setNewFolder(new File(data.getStartFolder().getPath() + screen.tfFolderNameNew.getText()));
				}
				if (success == false) {
					// if there was an error during the creation of the folder
					if (JOptionPane.showConfirmDialog(null, data.getText("ErrFolderExistsDesc"), data.getText("ErrFolderExistsTitle"), JOptionPane.ERROR_MESSAGE,
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						screen.lbFolderInfo.setText(data.getText("lbFolderInfoTook"));
					} else {
						screen.tfFolderNameNew.setText("");
						data.setNewFolder(null);
						screen.lbFolderInfo.setText(data.getText("lbFolderInfoNew"));
					}
				} else {
					screen.lbFolderInfo.setText(data.getText("lbFolderInfoCreated"));
				}
			}
		}
		if (e.getSource() == screen.btAdd) {
			if (!screen.tfReplaceWord.getText().equals("*")) {
				if (!screen.tfSearchWord.getText().isEmpty()) {
					if (!screen.tfSearchWord.getText().contains("*") && !screen.tfReplaceWord.getText().contains("*")) {
						addWordpair();
					} else {
						if ((countWildcards(screen.tfSearchWord.getText()) == countWildcards(screen.tfReplaceWord.getText())) || screen.tfSearchWord.getText().equals("*")) {
							addWordpair();
						} else {
							JOptionPane.showMessageDialog(new JFrame(), data.getText("ErrWildcardMatchDesc"), data.getText("ErrWildcardMatchTitle"), JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame(), data.getText("ErrNoExistPatDesc"), data.getText("ErrNoExistPatTitle"), JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(new JFrame(), data.getText("ErrOnlyWildcardDesc"), data.getText("ErrOnlyWildcardTitle"), JOptionPane.ERROR_MESSAGE);
			}
		}
		if (e.getSource() == screen.btDelete) {
			int index = screen.list.getSelectedIndex();
			if (index >= 0) {
				screen.model.remove(index);
				data.getWortListe().remove(index);
			}
		}
		if (e.getSource() == screen.btStart) {
			if (data.getWortListe().size() > 0) {
				if (data.getNewFolder() != null) {
					screen.btStart.setEnabled(false);
					screen.btCreate.setEnabled(false);
					screen.lbCopyInfo.setText(data.getText("lbCopyInfoProc"));
					Thread thread = new CopyAndReplace(screen, data);
					thread.start();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), data.getText("ErrNonewFolderDesc"), data.getText("ErrNonewFolderTitle"), JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(new JFrame(), data.getText("ErrNoReplacPatDesc"), data.getText("ErrNoReplacPatTitle"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private int countWildcards(String text) {
		return text.length() - text.replace("*", "").length();
	}
	
	private void addWordpair() {
		Wordpair newPair = new Wordpair(screen.tfSearchWord.getText(), screen.tfReplaceWord.getText());
		screen.tfSearchWord.setText("");
		screen.tfReplaceWord.setText("");
		data.getWortListe().add(newPair);
		screen.model.addElement(newPair.getWortAlt() + " >> " + newPair.getWortNeu());
	}
	
}
