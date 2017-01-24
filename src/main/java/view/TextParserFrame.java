package view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import resources.Constants;

public class TextParserFrame extends JFrame {
	private JPanel menuPanel;
	private JPanel contentPanel;

	private JScrollPane scrollPaneURLs;
	private JScrollPane scrollPaneTexts;

	private JButton loadTargetURLsButton;
	private JTable urlTable;
	private JTable tableTexts;
	private JTextArea textAreaCurrentText;
	private JButton btnNewButton;

	JButton loadParsedMapButton;

	public TextParserFrame() {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("TextParser " + Constants.PRODUCT_VERSION);
		setBounds(100, 100, 800, 500);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		menuPanel = new JPanel();
		contentPanel = new JPanel();
		getContentPane().add(menuPanel, BorderLayout.NORTH);
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		loadTargetURLsButton = new JButton("Load URLs");
		menuPanel.add(loadTargetURLsButton);

		loadParsedMapButton = new JButton("Load ParsedMap(Archive.org)");
		menuPanel.add(loadParsedMapButton);

		scrollPaneURLs = new JScrollPane();

		scrollPaneTexts = new JScrollPane();

		btnNewButton = new JButton("New button");

		textAreaCurrentText = new JTextArea();
		textAreaCurrentText.setLineWrap(true);
		textAreaCurrentText.setFont(new Font("Tahoma", Font.PLAIN, 12));

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup().addComponent(scrollPaneURLs, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPaneTexts, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(textAreaCurrentText, GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE))
						.addComponent(btnNewButton)).addContainerGap()));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING,
										gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(scrollPaneTexts, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE).addComponent(textAreaCurrentText, GroupLayout.PREFERRED_SIZE, 376,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(scrollPaneURLs, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNewButton).addContainerGap()));

		tableTexts = new JTable();
		scrollPaneTexts.setViewportView(tableTexts);

		urlTable = new JTable();
		scrollPaneURLs.setViewportView(urlTable);
		contentPanel.setLayout(gl_contentPanel);
	}

	public JScrollPane getScrollPaneURLs() {
		return scrollPaneURLs;
	}

	public JTable getUrlTable() {
		return urlTable;
	}

	public JButton getLoadTargetURLsButton() {
		return loadTargetURLsButton;
	}

	public JScrollPane getScrollPaneTexts() {
		return scrollPaneTexts;
	}

	public JTable getTableTexts() {
		return tableTexts;
	}

	public JTextArea getTextAreaCurrentText() {
		return textAreaCurrentText;
	}

	public JButton getLoadParsedMapButton() {
		return loadParsedMapButton;
	}

}
