package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import interfaces.FileParser;
import interfaces.ScreenshotSaver;
import interfaces.SiteParserExtend;
import logic.ArchiveOrgSiteParser;
import logic.CSVFileParser;
import logic.FileWorker;
import logic.ScreenshotSaverSelenium;
import logic.TextSiteParser;
import resources.FileType;
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

	private JButton parseTextButton;
	private JButton optionsButton;

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

		parseTextButton = mainMenu.getParseTextButton();
		optionsButton = mainMenu.getOptionsButton();

	}

	private void initListeners() {
		driverPathButton.addActionListener(new driverPathButtonActionListener());
		importFilePathButton.addActionListener(new importFilePathButtonActionListener());
		exportFolderPathButton.addActionListener(new exportFolderPathButtonActionListener());

		loadSiteListButton.addActionListener(new loadSiteListButtonActionListener());
		parseButton.addActionListener(new parseButtonActionListener());
		screenshotsSaveButton.addActionListener(new screenshotsSaveButtonActionListener());

		parseTextButton.addActionListener(new parseTextButtonActionListener());
		optionsButton.addActionListener(new optionButtonActionListener());
	}

	private void loadImportFile() {

		FileParser fileParser = new CSVFileParser();
		websiteList = null;
		String path = importFileTextField.getText().replaceAll("\\\\", "\\\\\\\\");

		try {

			websiteList = fileParser.readFile(path);

			// save tmp dir
			FileWorker worker = new FileWorker();
			if (worker.writeObjectTmp(FileType.SITE_LIST, websiteList)) {
				System.out.println("good");
			}
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

		// saving to tmp dir
		FileWorker worker = new FileWorker();
		if (worker.writeObjectTmp(FileType.PARSED_SITE_MAP, parseMap)) {
			System.out.println("good");
		}

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

	private void parseText() {

		TextParserController controller2 = new TextParserController();
		controller2.showTextParserFrame();

		boolean param = false;

		if (param) {
			TextSiteParser parser = new TextSiteParser();

			parser.setTargetSiteList(websiteList);
			parser.parseTextList();
			Map<String, Map<String, String>> parsedTextMap = parser.getParsedTextMap();

			for (Map.Entry<String, Map<String, String>> entry : parsedTextMap.entrySet()) {

				System.out.println("--------------------------");
				System.out.println(entry.getKey());

				for (Map.Entry<String, String> text : entry.getValue().entrySet()) {

					System.out.print(" - ");
					System.out.println(text.getKey());

				}

			}
		}
	}

	private void setOptions() {

		JComponent[] components = new JComponent[15];

		JLabel labelMaxLinkPerYear = new JLabel("Колличество ссылок за год:");
		JTextField textFieldMaxLinkPerYear = new JTextField();
		textFieldMaxLinkPerYear.setToolTipText("Для Archive.org");
		textFieldMaxLinkPerYear.setText(String.valueOf(PropertyHolder.getParseMaxLinkPerYear()));

		JLabel labelStartYear = new JLabel("Год старта парсинга:");
		JTextField textFieldStartYear = new JTextField();
		textFieldStartYear.setToolTipText("Для Archive.org");
		textFieldStartYear.setText(String.valueOf(PropertyHolder.getParseStartYear()));

		JLabel labelSeparator = new JLabel("------------------------------------------------------------");

		JLabel labelMinTextLength = new JLabel("Минимальный размер текста:");
		JTextField textFieldMinTextLength = new JTextField();
		textFieldMinTextLength.setToolTipText("Колличество символов в одном тексте.");
		textFieldMinTextLength.setText(String.valueOf(PropertyHolder.getParseMinTextLength()));

		JLabel labelUniqueValueWaiting = new JLabel("Время ожидания отклика:");
		JTextField textFieldUniqueValueWaiting = new JTextField();
		textFieldUniqueValueWaiting.setToolTipText("Значение в милисикундах. Для Text.ru");
		textFieldUniqueValueWaiting.setText(String.valueOf(PropertyHolder.getParseUniqueValueWaiting()));

		JLabel labelSeparator2 = new JLabel("------------------------------------------------------------");

		JLabel labelTextRuLogin = new JLabel("Логин для Text.ru:");
		JTextField textFieldTextRuLogin = new JTextField();
		textFieldTextRuLogin.setText(String.valueOf(PropertyHolder.getParseTextRuLogin()));

		JLabel labelTextRuPassword = new JLabel("Пароль для Text.ru:");
		JTextField textFieldTextRuPassword = new JTextField();
		textFieldTextRuPassword.setText(String.valueOf(PropertyHolder.getParseTextRuPassword()));

		JLabel labelStatus = new JLabel("------------------------------------------------------------");

		JButton buttonCheck = new JButton("Change");
		buttonCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (textFieldMaxLinkPerYear.getText() != String.valueOf(PropertyHolder.getParseMaxLinkPerYear())) {

					// is field digit
					if (textFieldMaxLinkPerYear.getText().matches("[-+]?\\d+")) {
						PropertyHolder.setParseMaxLinkPerYear(textFieldMaxLinkPerYear.getText());

					} else {
						JOptionPane.showMessageDialog(null, "Field Max Link Per Year, mast be Numeric", "Error input type", JOptionPane.ERROR_MESSAGE);
						textFieldMaxLinkPerYear.setText(String.valueOf(PropertyHolder.getParseMaxLinkPerYear()));
						textFieldMaxLinkPerYear.grabFocus();
						return;
					}

				}

				if (textFieldStartYear.getText() != String.valueOf(PropertyHolder.getParseStartYear())) {

					// is field digit
					if (textFieldStartYear.getText().matches("[-+]?\\d+")) {
						PropertyHolder.setParseStarYear(textFieldStartYear.getText());

					} else {
						JOptionPane.showMessageDialog(null, "Field Start Year, mast be Numeric", "Error input type", JOptionPane.ERROR_MESSAGE);
						textFieldStartYear.setText(String.valueOf(PropertyHolder.getParseStartYear()));
						textFieldStartYear.grabFocus();
						return;
					}

				}

				if (textFieldMinTextLength.getText() != String.valueOf(PropertyHolder.getParseMinTextLength())) {

					// is field digit
					if (textFieldMinTextLength.getText().matches("[-+]?\\d+")) {
						PropertyHolder.setParseMinTextLength(textFieldMinTextLength.getText());

					} else {
						JOptionPane.showMessageDialog(null, "Field Minimum text length, mast be Numeric", "Error input type", JOptionPane.ERROR_MESSAGE);
						textFieldMinTextLength.setText(String.valueOf(PropertyHolder.getParseMinTextLength()));
						textFieldMinTextLength.grabFocus();
						return;
					}

				}

				if (textFieldUniqueValueWaiting.getText() != String.valueOf(PropertyHolder.getParseUniqueValueWaiting())) {

					// is field digit
					if (textFieldUniqueValueWaiting.getText().matches("[-+]?\\d+")) {
						PropertyHolder.setParseUniqueValueWaiting(textFieldUniqueValueWaiting.getText());

					} else {
						JOptionPane.showMessageDialog(null, "Field waiting time, mast be Numeric", "Error input type", JOptionPane.ERROR_MESSAGE);
						textFieldUniqueValueWaiting.setText(String.valueOf(PropertyHolder.getParseUniqueValueWaiting()));
						textFieldUniqueValueWaiting.grabFocus();
						return;
					}

				}

				PropertyHolder.setParseTextRuLogin(textFieldTextRuLogin.getText());
				PropertyHolder.setParseTextRuPassword(textFieldTextRuPassword.getText());

				labelStatus.setText("<html><font color='green'>Properties successfully changed!</font></html>");

			}
		});

		components[0] = labelMaxLinkPerYear;
		components[1] = textFieldMaxLinkPerYear;
		components[2] = labelStartYear;
		components[3] = textFieldStartYear;
		components[4] = labelSeparator;
		components[5] = labelMinTextLength;
		components[6] = textFieldMinTextLength;
		components[7] = labelUniqueValueWaiting;
		components[8] = textFieldUniqueValueWaiting;
		components[9] = labelSeparator2;
		components[10] = labelTextRuLogin;
		components[11] = textFieldTextRuLogin;
		components[12] = labelTextRuPassword;
		components[13] = textFieldTextRuPassword;
		components[14] = labelStatus;

		JOptionPane.showOptionDialog(null, components, "Change properties?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] { buttonCheck }, null);

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

	private class parseTextButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			parseText();

		}

	}

	private class optionButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setOptions();

		}

	}
}
