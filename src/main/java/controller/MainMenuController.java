package controller;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

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

	public MainMenuController() {
		initComponents();
		initListeners();
	}

	public void showManeMenu() {
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

	}

	private void initListeners() {
		// TODO Auto-generated method stub

	}

}
