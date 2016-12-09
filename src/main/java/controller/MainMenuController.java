package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.lang3.math.NumberUtils;

import logic.ArchiveOrgSiteParser;
import logic.CSVFileParser;
import logic.FileParser;
import logic.ScreenshotSaver;
import logic.ScreenshotSaverSelenium;
import logic.SiteParserExtend;
import resources.PropertyHolder;
import view.MainMenuFrame;

public class MainMenuController {

	private MainMenuFrame mainMenu;

	private JTextField driverTextField;
	private JButton driverPathButton;

	private JTextField importFileTextField;
	private JButton importFilePathButton;

	private JTextField exportFolderTextField;
	private JButton exportFolderPathButton;

	private JButton loadSiteListButton;
	private JButton parseButton;
	private JButton screenshotsSaveButton;

	private JLabel loadingLabel;
	private JLabel websitesLabel;
	private JLabel nodesLabel;

	private JTextField maxLinksTextField;
	private JButton setParseConfigButton;
	private JTextField startYearTextField;

	private List<String> websiteList = null;
	private Map<String, ArrayList<String>> parseMap = null;

	public MainMenuController() {
		initComponents();
		initListeners();
	}

	public void showManeMenu() {
		updateInfo();
		mainMenu.setVisible(true);
	}

	private void initComponents() {

		mainMenu = new MainMenuFrame();

		driverTextField = mainMenu.getDriverTextField();
		driverPathButton = mainMenu.getDriverPathButton();

		importFileTextField = mainMenu.getImportFileTextField();
		importFilePathButton = mainMenu.getImportFilePathButton();

		exportFolderTextField = mainMenu.getExportFolderTextField();
		exportFolderPathButton = mainMenu.getExportFolderPathButton();

		loadSiteListButton = mainMenu.getLoadSiteListButton();
		parseButton = mainMenu.getParseButton();
		screenshotsSaveButton = mainMenu.getScreenshotsSaveButton();

		loadingLabel = mainMenu.getLoadingLabel();
		websitesLabel = mainMenu.getWebsitesLabel();
		nodesLabel = mainMenu.getNodesLabel();

		maxLinksTextField = mainMenu.getMaxLinksTextField();
		setParseConfigButton = mainMenu.getSetParseConfigButton();
		startYearTextField = mainMenu.getStartYearTextField();

	}

	private void initListeners() {
		driverPathButton.addActionListener(new driverPathButtonActionListener());
		importFilePathButton.addActionListener(new importFilePathButtonActionListener());
		exportFolderPathButton.addActionListener(new exportFolderPathButtonActionListener());

		loadSiteListButton.addActionListener(new loadSiteListButtonActionListener());
		parseButton.addActionListener(new parseButtonActionListener());
		screenshotsSaveButton.addActionListener(new screenshotsSaveButtonActionListener());

		setParseConfigButton.addActionListener(new setParseConfigButtonActionListener());
	}

	private void loadImportFile() {

		FileParser fileParser = new CSVFileParser();
		websiteList = null;
		String path = importFileTextField.getText().replaceAll("\\\\", "\\\\\\\\");

		try {

			websiteList = fileParser.readFile(path);
			loadingLabel.setText("<html><font color='green'>Loding websites success</font></html>");
			websitesLabel.setText(String.valueOf(websiteList.size()));

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Loading Error. Please restart programm!", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void parseSiteList() {

		SiteParserExtend parser = new ArchiveOrgSiteParser();

		for (String item : websiteList) {

			parser.setTargetSite(item);
			parser.parse();

		}

		parseMap = parser.getTargetSiteMap();

		int nodeCount = 0;
		for (Map.Entry<String, ArrayList<String>> item : parseMap.entrySet()) {
			System.out.println("_______________");
			for (String string : item.getValue()) {
				System.out.println(string);
			}
			nodeCount += item.getValue().size();
		}
		nodesLabel.setText(String.valueOf(nodeCount));
		loadingLabel.setText("<html><font color='blue'>Pars loading success!</font></html>");

	}

	private void startScreenshotSave() {

		String saveDirPath = exportFolderTextField.getText().replaceAll("\\\\", "\\\\\\\\");

		ScreenshotSaver saver = new ScreenshotSaverSelenium(saveDirPath);

		saver.start(parseMap);

		loadingLabel.setText("<html><font color='red'>Operation success!</font></html>");
		websitesLabel.setText("0");
		nodesLabel.setText("0");
		importFileTextField.setText("");

	}

	private void updateInfo() {

		driverTextField.setText(PropertyHolder.getPathDriver());
		exportFolderTextField.setText(PropertyHolder.getPathExportFolder());
		maxLinksTextField.setText(PropertyHolder.getParseMaxLinkPerYear());
		startYearTextField.setText(PropertyHolder.getParseStarYear());

	}

	private void setDriverPath() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("driver.exe", "exe");

		fileChooser.setFileFilter(filter);

		int answer = fileChooser.showDialog(null, "Choose");

		if (answer == JFileChooser.APPROVE_OPTION) {

			PropertyHolder.setPathDriver(fileChooser.getSelectedFile().getAbsolutePath());

			updateInfo();
		}
	}

	private void setExportFilePath() {
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);

		int answer = fileChooser.showDialog(null, "Choose");

		if (answer == JFileChooser.APPROVE_OPTION) {

			PropertyHolder.setPathExportFolder(fileChooser.getSelectedFile().getAbsolutePath());

			updateInfo();

		}
	}

	private void setParseConfig() {

		String maxLink = maxLinksTextField.getText();
		String startYear = startYearTextField.getText();

		if (NumberUtils.isDigits(maxLink)) {
			if (NumberUtils.isDigits(startYear)) {

				PropertyHolder.setParseMaxLinkPerYear(maxLink);
				PropertyHolder.setParseStarYear(startYear);

			}

		} else {
			JOptionPane.showMessageDialog(null, "Fields mast be Numeric", "Error input type", JOptionPane.ERROR_MESSAGE);
		}

		updateInfo();

	}

	private class driverPathButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			setDriverPath();

		}
	}

	private class importFilePathButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.csv", "csv");

			fileChooser.setFileFilter(filter);

			int answer = fileChooser.showDialog(null, "Choose");

			if (answer == JFileChooser.APPROVE_OPTION) {

				importFileTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());

			}

		}
	}

	private class exportFolderPathButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			setExportFilePath();

		}
	}

	private class loadSiteListButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (!driverTextField.getText().equals("")) {

				if (!importFileTextField.getText().equals("")) {

					if (!exportFolderTextField.getText().equals("")) {

						loadImportFile();
						parseButton.setEnabled(true);

					} else {
						JOptionPane.showMessageDialog(null, "Select directory!  That will store ScreenShots", "Error", JOptionPane.ERROR_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null, "Select import file!  *.csv", "Error", JOptionPane.ERROR_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(null, "Select Driver!", "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	private class parseButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (websiteList != null) {
				parseSiteList();
				screenshotsSaveButton.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(null, "No websites in list!", "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	private class screenshotsSaveButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			startScreenshotSave();

		}
	}

	private class setParseConfigButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			setParseConfig();

		}

	}

}
