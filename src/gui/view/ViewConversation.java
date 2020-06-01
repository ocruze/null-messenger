package gui.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
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
import model.client.UserSession;
import model.entity.Conversation;
import model.entity.Message;
import model.entity.User;

public class ViewConversation extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private JFrame this;
	private JTextField convNameField;
	private PresenterConversation presenter;
	private JList<User> jListUser;
	private JList<Conversation> jListConversation;
	private JList<Message> jListMessage;
	private JTextArea messageTxt;

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
		super();
		setResizable(false);
		initialize();
		this.setVisible(true);
	}

	public void setPresenter(PresenterConversation presenterConversation) {
		this.presenter = presenterConversation;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// frame = new JFrame();
		this.setBounds(100, 100, 1360, 641);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 236, 330, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		this.getContentPane().setLayout(gridBagLayout);

		JPanel newConvPanel = new JPanel();
		GridBagConstraints gbc_leftPanel = new GridBagConstraints();
		gbc_leftPanel.anchor = GridBagConstraints.WEST;
		gbc_leftPanel.insets = new Insets(0, 0, 0, 5);
		gbc_leftPanel.fill = GridBagConstraints.VERTICAL;
		gbc_leftPanel.gridx = 0;
		gbc_leftPanel.gridy = 0;
		this.getContentPane().add(newConvPanel, gbc_leftPanel);

		JScrollPane usersScrollPane = new JScrollPane();

		JLabel goupeName = new JLabel("Conversation Name :");

		convNameField = new JTextField();
		convNameField.setColumns(10);

		JLabel labelConvCreation = new JLabel("Create conversation");
		labelConvCreation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelConvCreation.setVerifyInputWhenFocusTarget(false);

		JButton btnCreateConv = new JButton("Create");
		GroupLayout gl_leftPanel = new GroupLayout(newConvPanel);
		gl_leftPanel.setHorizontalGroup(gl_leftPanel.createParallelGroup(Alignment.TRAILING).addGroup(gl_leftPanel
				.createSequentialGroup()
				.addGroup(gl_leftPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_leftPanel.createSequentialGroup().addGap(26).addComponent(usersScrollPane,
								GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_leftPanel.createSequentialGroup().addGap(25)
								.addGroup(gl_leftPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(goupeName, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
										.addComponent(convNameField, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))))
				.addGap(42))
				.addGroup(Alignment.LEADING,
						gl_leftPanel.createSequentialGroup().addGap(53)
								.addComponent(labelConvCreation, GroupLayout.PREFERRED_SIZE, 173,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(81, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING,
						gl_leftPanel
								.createSequentialGroup().addGap(77).addComponent(btnCreateConv,
										GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(92, Short.MAX_VALUE)));
		gl_leftPanel.setVerticalGroup(gl_leftPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_leftPanel.createSequentialGroup()
						.addComponent(labelConvCreation, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addGap(11).addComponent(goupeName, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addGap(5)
						.addComponent(convNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(34)
						.addComponent(usersScrollPane, GroupLayout.PREFERRED_SIZE, 372, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(btnCreateConv).addContainerGap(41, Short.MAX_VALUE)));
		btnCreateConv.addActionListener((ActionEvent e) -> {
			presenter.setListParticipantsNewConv(jListUser.getSelectedValuesList());
			presenter.createConversation();
		});

		jListUser = new JList<>();
		usersScrollPane.setViewportView(jListUser);
		newConvPanel.setLayout(gl_leftPanel);

		JPanel conversationPanel = new JPanel();
		GridBagConstraints gbc_centerPanel = new GridBagConstraints();
		gbc_centerPanel.insets = new Insets(0, 0, 0, 5);
		gbc_centerPanel.fill = GridBagConstraints.BOTH;
		gbc_centerPanel.gridx = 1;
		gbc_centerPanel.gridy = 0;
		this.getContentPane().add(conversationPanel, gbc_centerPanel);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[] { 0, 0 };
		gbl_centerPanel.rowHeights = new int[] { 0, 0 };
		gbl_centerPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_centerPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		conversationPanel.setLayout(gbl_centerPanel);

		JScrollPane conversationScrollPane = new JScrollPane();
		GridBagConstraints gbc_centerScrollPane = new GridBagConstraints();
		gbc_centerScrollPane.fill = GridBagConstraints.BOTH;
		gbc_centerScrollPane.gridx = 0;
		gbc_centerScrollPane.gridy = 0;
		conversationPanel.add(conversationScrollPane, gbc_centerScrollPane);

		jListConversation = new JList<>();
		jListConversation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Conversation conv = jListConversation.getSelectedValue();
				
				if (conv != null && conv.getMessages() != null && conv.getMessages().size() != 0) {
					loadMessages(conv.getMessages());
					UserSession.setConversationId(conv.getIdConversation());
				}else {
					UserSession.unsetConversationId();
				}
			}

		});
		conversationScrollPane.setViewportView(jListConversation);

		JPanel messagePanel = new JPanel();
		GridBagConstraints gbc_rightPanel = new GridBagConstraints();
		gbc_rightPanel.fill = GridBagConstraints.BOTH;
		gbc_rightPanel.gridx = 2;
		gbc_rightPanel.gridy = 0;
		this.getContentPane().add(messagePanel, gbc_rightPanel);
		GridBagLayout gbl_rightPanel = new GridBagLayout();
		gbl_rightPanel.columnWidths = new int[] { 0, 0 };
		gbl_rightPanel.rowHeights = new int[] { 453, 0, 0 };
		gbl_rightPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_rightPanel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		messagePanel.setLayout(gbl_rightPanel);

		JScrollPane messagesScrollPane = new JScrollPane();
		GridBagConstraints gbc_messagesScrollPane = new GridBagConstraints();
		gbc_messagesScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_messagesScrollPane.fill = GridBagConstraints.BOTH;
		gbc_messagesScrollPane.gridx = 0;
		gbc_messagesScrollPane.gridy = 0;
		messagePanel.add(messagesScrollPane, gbc_messagesScrollPane);

		jListMessage = new JList<>();
		messagesScrollPane.setViewportView(jListMessage);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		messagePanel.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		messageTxt = new JTextArea();
		messageTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});
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
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendMessage();
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.BOTH;
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 0;
		panel.add(btnSend, gbc_btnSend);

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLogin = new JMenuItem("Login");
		mnFile.add(mntmLogin);

		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mnFile.add(mntmDisconnect);
	}

	public void loadUsers(List<User> listUser) {
		// System.out.println("im here");
		jListUser.setListData(new Vector<User>(listUser));
		this.validate();
		this.repaint();
	}

	public void loadConversations(List<Conversation> listConversation) {
		if (listConversation == null || listConversation.size() == 0)
			return;
		Vector<Conversation> convVector = new Vector<Conversation>(listConversation);
		jListConversation.removeAll();
		jListConversation.setListData(convVector);
		
		jListConversation.setCellRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = -2663637240222087200L;

//			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (renderer instanceof JLabel && value instanceof Conversation) {
					Conversation conv = (Conversation) value;

					List<User> participants = conv.getParticipants();
					participants.removeIf(x -> x == null || x.getUsername().equals(UserSession.getConnectedUsername()));

					String label = "";

					if (participants.size() > 0) {

						label = participants.get(0).getUsername();

						for (int i = 1; i < participants.size(); i++) {
							label += ", " + participants.get(i).getUsername();
						}
					}

					((JLabel) renderer).setText(label);
				}
				return renderer;
			}
		});
		
		if(UserSession.getConversationId() != -1) {
			Optional<Conversation> conv = convVector
					.stream()
					.filter(c -> c.getIdConversation() == UserSession.getConversationId())
					.findFirst();
			jListConversation.setSelectedIndex(convVector.indexOf(conv.get()));
		}

		this.validate();
		this.repaint();
	}
	
	
	
	public void loadMessages(List<Message> listMessage) {
		jListMessage.removeAll();
		jListMessage.setListData(new Vector<Message>(listMessage));
		
		jListMessage.setCellRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = -2663637240222087200L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (renderer instanceof JLabel && value instanceof Message) {
					Message message = (Message) value;

					String label = "[" + message.getSender().getUsername() + "] : " + message.getContent();

					((JLabel) renderer).setText(label);
				}
				return renderer;
			}
		});

		this.validate();
		this.repaint();
	}

	private void sendMessage() {
		String message = messageTxt.getText();
		messageTxt.setText("");
		
		int convId = -1;

		if (jListConversation.getSelectedValue() != null) {
			convId = UserSession.getConversationId();
		}else {
			convId = jListConversation.getSelectedValue().getIdConversation();
		}
		
		presenter.sendMessage(message, convId);
	}
	
	

}
