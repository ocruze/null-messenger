package window.image;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Entity.Conversation;
import model.Entity.Message;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ChatClientGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClientGUI window = new ChatClientGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 883, 553);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 262, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JPanel leftPanel = new JPanel();
		GridBagConstraints gbc_leftPanel = new GridBagConstraints();
		gbc_leftPanel.insets = new Insets(0, 0, 0, 5);
		gbc_leftPanel.fill = GridBagConstraints.BOTH;
		gbc_leftPanel.gridx = 0;
		gbc_leftPanel.gridy = 0;
		frame.getContentPane().add(leftPanel, gbc_leftPanel);
		GridBagLayout gbl_leftPanel = new GridBagLayout();
		gbl_leftPanel.columnWidths = new int[] { 0, 0 };
		gbl_leftPanel.rowHeights = new int[] { 0, 0 };
		gbl_leftPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_leftPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		leftPanel.setLayout(gbl_leftPanel);

		JScrollPane leftScrollPane = new JScrollPane();
		GridBagConstraints gbc_leftScrollPane = new GridBagConstraints();
		gbc_leftScrollPane.fill = GridBagConstraints.BOTH;
		gbc_leftScrollPane.gridx = 0;
		gbc_leftScrollPane.gridy = 0;
		leftPanel.add(leftScrollPane, gbc_leftScrollPane);

		JList<Conversation> conversationList = new JList<>();
		leftScrollPane.setViewportView(conversationList);

		JPanel rightPanel = new JPanel();
		GridBagConstraints gbc_rightPanel = new GridBagConstraints();
		gbc_rightPanel.fill = GridBagConstraints.BOTH;
		gbc_rightPanel.gridx = 1;
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
