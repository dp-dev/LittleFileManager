package de.studware.main;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Screen extends JFrame {
	public JButton btSearch, btCreate, btDelete, btStart, btAdd;
	public JProgressBar progressBar;
	public JTextField tfFolder, tfSearchWord, tfReplaceWord, tfFolderNameNew;
	public JLabel lbCopyInfo, lbFolderInfo;
	
	DefaultListModel<String> model;
	JList<String> list;
	
	public Screen(LittleFileManager lfm) {
		try {
			Image image = ImageIO.read(this.getClass().getResource("/png/logo.png"));
			setIconImage(image);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setTitle("File Rename Manager");
		setSize(510, 240);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		// Tab Navigation
		JTabbedPane tabNav = new JTabbedPane();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		panel1.setLayout(null);
		panel2.setLayout(null);
		tabNav.addTab(lfm.data.getText("navtitle1"), null, panel1, lfm.data.getText("navdesc1"));
		tabNav.addTab(lfm.data.getText("navtitle2"), null, panel2, lfm.data.getText("navdesc2"));

		// PANEL 1
		JLabel lbPath = new JLabel(lfm.data.getText("lbPath"));
		lbPath.setBounds(10, 10, 360, 22);
		panel1.add(lbPath);
		tfFolder = new JTextField(lfm.data.getStartFolder().getPath());
		tfFolder.setBounds(10, 30, 375, 22);
		tfFolder.setEditable(false);
		panel1.add(tfFolder);
		tfFolder.addActionListener(lfm);

		btSearch = new JButton(lfm.data.getText("btSearch"));
		btSearch.setBounds(390, 30, 100, 21);
		panel1.add(btSearch);
		btSearch.addActionListener(lfm);

		// Folder Creation
		JLabel lbFolderName = new JLabel(lfm.data.getText("lbFolderName"));
		lbFolderName.setBounds(10, 60, 360, 22);
		panel1.add(lbFolderName);
		tfFolderNameNew = new JTextField("");
		tfFolderNameNew.setBounds(10, 80, 375, 22);
		panel1.add(tfFolderNameNew);
		tfFolderNameNew.addActionListener(lfm);
		btCreate = new JButton(lfm.data.getText("btCreate"));
		btCreate.setBounds(390, 80, 100, 21);
		panel1.add(btCreate);
		btCreate.addActionListener(lfm);

		lbFolderInfo = new JLabel(lfm.data.getText("lbFolderInfo"), SwingConstants.CENTER);
		lbFolderInfo.setBounds(10, 120, 480, 25);
		panel1.add(lbFolderInfo);

		// PANEL 2
		// Search word
		JLabel lbSearchName = new JLabel(lfm.data.getText("lbSearchName"));
		lbSearchName.setBounds(10, 10, 180, 22);
		panel2.add(lbSearchName);
		tfSearchWord = new JTextField("");
		tfSearchWord.setBounds(10, 30, 180, 22);
		panel2.add(tfSearchWord);
		tfSearchWord.addActionListener(lfm);

		// Replacement word
		JLabel lbReplaceName = new JLabel(lfm.data.getText("lbReplaceName"));
		lbReplaceName.setBounds(200, 10, 180, 22);
		panel2.add(lbReplaceName);
		tfReplaceWord = new JTextField("");
		tfReplaceWord.setBounds(200, 30, 180, 22);
		panel2.add(tfReplaceWord);
		tfReplaceWord.addActionListener(lfm);

		model = new DefaultListModel<String>();
		list = new JList<String>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds(10, 60, 370, 60);
		panel2.add(scroll);

		btAdd = new JButton(lfm.data.getText("btAdd"));
		btAdd.setBounds(390, 30, 100, 21);
		panel2.add(btAdd);
		btAdd.addActionListener(lfm);

		btDelete = new JButton(lfm.data.getText("btDelete"));
		btDelete.setBounds(390, 60, 100, 21);
		panel2.add(btDelete);
		btDelete.addActionListener(lfm);

		btStart = new JButton(lfm.data.getText("btStart"));
		btStart.setBounds(390, 125, 100, 21);
		panel2.add(btStart);
		btStart.addActionListener(lfm);

		// Copy Information
		lbCopyInfo = new JLabel(lfm.data.getText("lbCopyInfo"), SwingConstants.CENTER);
		lbCopyInfo.setBounds(10, 125, 370, 25);
		panel2.add(lbCopyInfo);

		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setBounds(10, 155, 480, 25);
		panel2.add(progressBar);

		add(tabNav);
		setResizable(false);
		setVisible(true);
	}
}
