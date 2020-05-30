package gui.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gui.presenter.PresenterConversation;
import model.entity.Conversation;
import model.entity.Message;

public class ViewConversation {

	private JFrame frame;
	private JTextField convNameField;
	private PresenterConversation presenter;

	/**
	 * Launch the application.
	 */
	/**
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { ViewConversation window = new
	 * ViewConversation(); window.frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 **/

	/**
	 * Create the application.
	 */
	public ViewConversation() {
		initialize();
		this.frame.setVisible(true);
	}

	public void setPresenter(PresenterConversation presenterConversation) {
		this.presenter = presenterConversation;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1360, 641);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 236, 330, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JPanel leftPanel = new JPanel();
		GridBagConstraints gbc_leftPanel = new GridBagConstraints();
		gbc_leftPanel.anchor = GridBagConstraints.WEST;
		gbc_leftPanel.insets = new Insets(0, 0, 0, 5);
		gbc_leftPanel.fill = GridBagConstraints.VERTICAL;
		gbc_leftPanel.gridx = 0;
		gbc_leftPanel.gridy = 0;
		frame.getContentPane().add(leftPanel, gbc_leftPanel);

		JScrollPane centerScrollPane_1 = new JScrollPane();

		JLabel goupeName = new JLabel("Conversation Name :");

		convNameField = new JTextField();
		convNameField.setColumns(10);

		JLabel labelConvCreation = new JLabel("Create conversation");
		labelConvCreation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelConvCreation.setVerifyInputWhenFocusTarget(false);

		JButton btnCreateConv = new JButton("Create");
		GroupLayout gl_leftPanel = new GroupLayout(leftPanel);
		gl_leftPanel.setHorizontalGroup(gl_leftPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_leftPanel
				.createSequentialGroup()
				.addGroup(gl_leftPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_leftPanel.createSequentialGroup().addGap(25)
								.addGroup(gl_leftPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(convNameField, GroupLayout.PREFERRED_SIZE, 169,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(goupeName, GroupLayout.PREFERRED_SIZE, 122,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_leftPanel.createSequentialGroup().addGap(26).addComponent(centerScrollPane_1,
								GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(42, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING,
						gl_leftPanel.createSequentialGroup().addContainerGap(119, Short.MAX_VALUE)
								.addComponent(btnCreateConv, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
								.addGap(111))
				.addGroup(gl_leftPanel.createSequentialGroup().addGap(83)
						.addComponent(labelConvCreation, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(97, Short.MAX_VALUE)));
		gl_leftPanel.setVerticalGroup(gl_leftPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_leftPanel.createSequentialGroup()
						.addComponent(labelConvCreation, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addGap(11).addComponent(goupeName, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addGap(5)
						.addComponent(convNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(34)
						.addComponent(centerScrollPane_1, GroupLayout.PREFERRED_SIZE, 372, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(btnCreateConv).addContainerGap(30, Short.MAX_VALUE)));
		leftPanel.setLayout(gl_leftPanel);

		JPanel centerPanel = new JPanel();
		GridBagConstraints gbc_centerPanel = new GridBagConstraints();
		gbc_centerPanel.insets = new Insets(0, 0, 0, 5);
		gbc_centerPanel.fill = GridBagConstraints.BOTH;
		gbc_centerPanel.gridx = 1;
		gbc_centerPanel.gridy = 0;
		frame.getContentPane().add(centerPanel, gbc_centerPanel);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[] { 0, 0 };
		gbl_centerPanel.rowHeights = new int[] { 0, 0 };
		gbl_centerPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_centerPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		centerPanel.setLayout(gbl_centerPanel);

		JScrollPane centerScrollPane = new JScrollPane();
		GridBagConstraints gbc_centerScrollPane = new GridBagConstraints();
		gbc_centerScrollPane.fill = GridBagConstraints.BOTH;
		gbc_centerScrollPane.gridx = 0;
		gbc_centerScrollPane.gridy = 0;
		centerPanel.add(centerScrollPane, gbc_centerScrollPane);

		JList<Conversation> conversationList = new JList<>();
		centerScrollPane.setViewportView(conversationList);

		JPanel rightPanel = new JPanel();
		GridBagConstraints gbc_rightPanel = new GridBagConstraints();
		gbc_rightPanel.fill = GridBagConstraints.BOTH;
		gbc_rightPanel.gridx = 2;
		gbc_rightPanel.gridy = 0;
		frame.getContentPane().add(rightPanel, gbc_rightPanel);
		GridBagLayout gbl_rightPanel = new GridBagLayout();
		gbl_rightPanel.columnWidths = new int[] { 0, 0 };
		gbl_rightPanel.rowHeights = new int[] { 453, 0, 0 };
		gbl_rightPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_rightPanel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		rightPanel.setLayout(gbl_rightPanel);

		JScrollPane rightScrollPane = new JScrollPane();
		GridBagConstraints gbc_rightScrollPane = new GridBagConstraints();
		gbc_rightScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_rightScrollPane.fill = GridBagConstraints.BOTH;
		gbc_rightScrollPane.gridx = 0;
		gbc_rightScrollPane.gridy = 0;
		rightPanel.add(rightScrollPane, gbc_rightScrollPane);

		JList<Message> messageList = new JList<>();
		rightScrollPane.setViewportView(messageList);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		rightPanel.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JTextArea messageTxt = new JTextArea();
		messageTxt.setMaximumSize(new Dimension(1, 15));
		messageTxt.setName("");
		messageTxt.setToolTipText("Write a message");
		GridBagConstraints gbc_messageTxt = new GridBagConstraints();
		gbc_messageTxt.insets = new Insets(0, 0, 0, 5);
		gbc_messageTxt.fill = GridBagConstraints.BOTH;
		gbc_messageTxt.gridx = 0;
		gbc_messageTxt.gridy = 0;
		panel.add(messageTxt, gbc_messageTxt);

		JButton btnSend = new JButton("Send");
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.BOTH;
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 0;
		panel.add(btnSend, gbc_btnSend);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLogin = new JMenuItem("Login");
		mnFile.add(mntmLogin);

		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mnFile.add(mntmDisconnect);
	}
}
