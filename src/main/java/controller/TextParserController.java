package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import logic.FileWorker;
import logic.TextSiteParser;
import model.TableTextModel;
import model.URLTabelModel;
import resources.FileType;
import view.TextParserFrame;

public class TextParserController {
	private TextParserFrame textParserFrame;

	private JScrollPane scrollPaneURLs;
	private JTable urlTable;

	private JButton loadTargetURLsButton;

	private JTable tableTexts;
	private JTextArea textAreaCurrentText;

	private JButton loadParsedMapButton;

	private List<String> websiteList = null;
	private Map<String, ArrayList<String>> parseMap = null;
	private Map<String, Map<String, String>> textMap;

	public TextParserController() {
		initComponents();
		initListeners();
	}

	public void showTextParserFrame() {
		updateInfo();
		textParserFrame.setVisible(true);
	}

	private void updateInfo() {
		// TODO Auto-generated method stub
	}

	private void initComponents() {

		textParserFrame = new TextParserFrame();

		scrollPaneURLs = textParserFrame.getScrollPaneURLs();
		urlTable = textParserFrame.getUrlTable();

		loadTargetURLsButton = textParserFrame.getLoadTargetURLsButton();

		tableTexts = textParserFrame.getTableTexts();
		textAreaCurrentText = textParserFrame.getTextAreaCurrentText();

		loadParsedMapButton = textParserFrame.getLoadParsedMapButton();

	}

	private void initListeners() {
		loadTargetURLsButton.addActionListener(new loadTargetURLsButtonActionListener());
		loadParsedMapButton.addActionListener(new loadParsedMapButtonActionListener());

		urlTable.addMouseListener(new urlTableMousListener());
		tableTexts.addMouseListener(new tableTextsMouseListener());

	}

	private void setURLTabelModel() {
		URLTabelModel model = new URLTabelModel(textMap);
		urlTable.setModel(model);

		System.out.println("Model Set");
	}

	private void setTableTextModel(String targetSite) {

		TableTextModel model = new TableTextModel(textMap.get(targetSite));

		tableTexts.setModel(model);

		System.out.println("Model Set");
	}

	private void loadURLs() {

		// loading from tmp dir
		FileWorker worker = new FileWorker();
		websiteList = (List<String>) worker.readObjectTmp(FileType.SITE_LIST);

		if (parseMap != null) {
			TextSiteParser parser = new TextSiteParser();

			parser.setTargetSiteList(websiteList);
			parser.parseTextList();
			textMap = parser.getParsedTextMap();

			setURLTabelModel();
		} else {
			JOptionPane.showMessageDialog(null, "Loading Error. Repeat operation", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void loadParsedMap() {

		// loading from tmp dir
		FileWorker worker = new FileWorker();
		parseMap = (Map<String, ArrayList<String>>) worker.readObjectTmp(FileType.PARSED_SITE_MAP);

		if (parseMap != null) {
			TextSiteParser parser = new TextSiteParser();

			parser.setTargetSiteMap(parseMap);
			parser.parseTextMap();

			textMap = parser.getParsedTextMap();

			setURLTabelModel();
		} else {
			JOptionPane.showMessageDialog(null, "Loading Error. Repeat operation", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private class loadTargetURLsButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			loadURLs();
		}

	}

	private class loadParsedMapButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			loadParsedMap();

		}

	}

	private class urlTableMousListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = urlTable.getSelectedRow();
			String targetSite = (String) urlTable.getValueAt(row, 0);

			setTableTextModel(targetSite);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

	}

	private class tableTextsMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = tableTexts.getSelectedRow();

			String text = (String) tableTexts.getValueAt(row, 0);

			textAreaCurrentText.setText(text);

		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}
}
