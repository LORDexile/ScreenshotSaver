package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import resources.Constants;

public class MainMenuFrame extends JFrame {

	private JPanel mainPanel;

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

	/**
	 * Create the application.
	 */
	public MainMenuFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setTitle(Constants.PROGRAM_TITLE);
		setBounds(100, 100, 538, 301);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);

		JLabel labelPathDriver = new JLabel("Path to driver:");
		labelPathDriver.setBounds(10, 11, 464, 14);
		mainPanel.add(labelPathDriver);

		driverTextField = new JTextField();
		driverTextField.setEditable(false);
		driverTextField.setBounds(10, 25, 384, 20);
		mainPanel.add(driverTextField);
		driverTextField.setColumns(10);

		driverPathButton = new JButton("Browse");
		driverPathButton.setBounds(406, 25, 112, 21);
		mainPanel.add(driverPathButton);

		JLabel labelImportFile = new JLabel("Path to input file. (.csv)");
		labelImportFile.setBounds(10, 47, 464, 14);
		mainPanel.add(labelImportFile);

		importFileTextField = new JTextField();
		importFileTextField.setEditable(false);
		importFileTextField.setBounds(10, 65, 384, 20);
		mainPanel.add(importFileTextField);
		importFileTextField.setColumns(10);

		importFilePathButton = new JButton("Browse");
		importFilePathButton.setBounds(406, 65, 112, 20);
		mainPanel.add(importFilePathButton);

		JLabel labelExport = new JLabel("Path to export folder.(save folder)");
		labelExport.setBounds(10, 86, 464, 14);
		mainPanel.add(labelExport);

		exportFolderTextField = new JTextField();
		exportFolderTextField.setEditable(false);
		exportFolderTextField.setBounds(10, 106, 384, 20);
		mainPanel.add(exportFolderTextField);
		exportFolderTextField.setColumns(10);

		exportFolderPathButton = new JButton("Browse");
		exportFolderPathButton.setBounds(406, 106, 112, 20);
		mainPanel.add(exportFolderPathButton);

		loadSiteListButton = new JButton("Load site list");
		loadSiteListButton.setBounds(406, 138, 112, 20);
		mainPanel.add(loadSiteListButton);

		parseButton = new JButton("Start Prase");
		parseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		parseButton.setEnabled(false);
		parseButton.setBounds(406, 203, 111, 20);
		mainPanel.add(parseButton);

		screenshotsSaveButton = new JButton("Start Saving");
		screenshotsSaveButton.setEnabled(false);
		screenshotsSaveButton.setBounds(406, 235, 112, 20);
		mainPanel.add(screenshotsSaveButton);

		loadingLabel = new JLabel("Loading:");
		loadingLabel.setBounds(10, 174, 354, 14);
		mainPanel.add(loadingLabel);

		JLabel labelWebsites = new JLabel("Websites:");
		labelWebsites.setBounds(10, 206, 70, 14);
		mainPanel.add(labelWebsites);

		websitesLabel = new JLabel("0");
		websitesLabel.setBounds(90, 206, 286, 14);
		mainPanel.add(websitesLabel);

		JLabel labelNodes = new JLabel("Nodes:");
		labelNodes.setBounds(10, 238, 46, 14);
		mainPanel.add(labelNodes);

		nodesLabel = new JLabel("0");
		nodesLabel.setBounds(90, 238, 286, 14);
		mainPanel.add(nodesLabel);

		parseTextButton = new JButton("Parse Text");
		parseTextButton.setBounds(406, 167, 112, 28);
		mainPanel.add(parseTextButton);

		optionsButton = new JButton("Options");
		optionsButton.setBounds(6, 138, 90, 28);
		mainPanel.add(optionsButton);

	}

	public JTextField getDriverTextField() {
		return driverTextField;
	}

	public JButton getDriverPathButton() {
		return driverPathButton;
	}

	public JTextField getImportFileTextField() {
		return importFileTextField;
	}

	public JButton getImportFilePathButton() {
		return importFilePathButton;
	}

	public JTextField getExportFolderTextField() {
		return exportFolderTextField;
	}

	public JButton getExportFolderPathButton() {
		return exportFolderPathButton;
	}

	public JButton getLoadSiteListButton() {
		return loadSiteListButton;
	}

	public JButton getParseButton() {
		return parseButton;
	}

	public JButton getScreenshotsSaveButton() {
		return screenshotsSaveButton;
	}

	public JLabel getLoadingLabel() {
		return loadingLabel;
	}

	public JLabel getWebsitesLabel() {
		return websitesLabel;
	}

	public JLabel getNodesLabel() {
		return nodesLabel;
	}

	public JButton getParseTextButton() {
		return parseTextButton;
	}

	public JButton getOptionsButton() {
		return optionsButton;
	}

}
