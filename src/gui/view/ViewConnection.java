package gui.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import gui.presenter.PresenterConnection;

public class ViewConnection extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JTextField ipField;
	private JPasswordField passwordField;
	private JTextField portField;
	public PresenterConnection presenter;

	public enum FieldType {
		USERNAME, PASSWORD, IP, PORT
	}

	private int xx, xy;

	public ViewConnection() {
		init();
		this.setUndecorated(true);
		this.setVisible(true);
	}

	public void setPresenter(PresenterConnection presenter) {
		this.presenter = presenter;
	}

	public void init() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 729, 476);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 346, 490);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setForeground(new Color(240, 248, 255));
		lblNewLabel.setBounds(139, 305, 84, 27);
		panel.add(lblNewLabel);

		JLabel label = new JLabel("");

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				xx = e.getX();
				xy = e.getY();
			}
		});
		label.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {

				int x = arg0.getXOnScreen();
				int y = arg0.getYOnScreen();
				ViewConnection.this.setLocation(x - xx, y - xy);
			}
		});
		label.setBounds(-38, 0, 420, 290);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(ViewConnection.class.getResource("/img/Chatrooms.png")));
		panel.add(label);

		JLabel lblWeGotYou = new JLabel(" TO CHAT ROOM");
		lblWeGotYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeGotYou.setForeground(new Color(240, 248, 255));
		lblWeGotYou.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblWeGotYou.setBounds(57, 343, 223, 27);
		panel.add(lblWeGotYou);

		Button loginButton = new Button("Login");
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(Color.BLUE);
		loginButton.setBounds(395, 363, 283, 36);
		loginButton.addActionListener((ActionEvent e) -> {
			try {
				presenter.setPassword(getValueField(passwordField, FieldType.PASSWORD));
				presenter.setHostName(getValueField(ipField, FieldType.IP));
				presenter.setUsername(getValueField(usernameField, FieldType.USERNAME));
				presenter.setPort(Integer.parseInt(getValueField(portField, FieldType.PORT)));
				presenter.login();
			} catch (Exception e2) {
				JOptionPane message = new JOptionPane();
				message.showMessageDialog(null, "Une erreur s'est produite : " + e2.getMessage(), "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		contentPane.add(loginButton);

		Button registerButton = new Button("Register");
		registerButton.setForeground(Color.WHITE);
		registerButton.setBackground(Color.GRAY);
		registerButton.setBounds(395, 403, 283, 36);
		registerButton.addActionListener((ActionEvent e) -> {
			try {
				presenter.setPassword(getValueField(passwordField, FieldType.PASSWORD));
				// presenter.setIp(getValueField(ipField, FieldType.IP));
				presenter.setUsername(getValueField(usernameField, FieldType.USERNAME));
				// presenter.setPort(Integer.parseInt(getValueField(portField,
				// FieldType.PORT)));
				presenter.register();
			} catch (Exception e2) {
				JOptionPane message = new JOptionPane();
				message.showMessageDialog(null, "Une erreur s'est produite : " + e2.getMessage(), "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		contentPane.add(registerButton);

		usernameField = new JTextField();
		usernameField.setBounds(395, 83, 283, 36);
		contentPane.add(usernameField);
		usernameField.setColumns(10);

		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setBounds(395, 58, 181, 14);
		contentPane.add(lblUsername);

		JLabel adresse = new JLabel("IP ADDRESSE");
		adresse.setBounds(395, 132, 210, 14);
		contentPane.add(adresse);

		ipField = new JTextField();
		ipField.setColumns(10);
		ipField.setBounds(395, 157, 283, 36);
		contentPane.add(ipField);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(395, 204, 96, 14);
		contentPane.add(lblPassword);

		JLabel port = new JLabel("PORT");
		port.setBounds(395, 275, 133, 14);
		contentPane.add(port);

		passwordField = new JPasswordField();
		passwordField.setBounds(395, 229, 283, 36);
		contentPane.add(passwordField);

		portField = new JTextField();
		portField.setBounds(395, 293, 283, 36);
		contentPane.add(portField);

		JLabel lbl_close = new JLabel("X");
		lbl_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				System.exit(0);
			}
		});
		lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_close.setForeground(Color.BLUE);
		lbl_close.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_close.setBounds(691, 0, 37, 27);
		contentPane.add(lbl_close);
	}
	public void prepareWindowChanged() {
		//this.setUndecorated(false);
		this.setVisible(false);
	}
	public void needRegister() {
		JOptionPane message = new JOptionPane();
		message.showMessageDialog(null, "Please register before signing in", "Info", JOptionPane.WARNING_MESSAGE);
	}

	public String getValueField(JTextField field, FieldType type) throws Exception {
		// String value = "";

		JOptionPane message = new JOptionPane();
		Pattern p = Pattern.compile("");
		switch (type) {
		case PORT:
			p = Pattern.compile("[0-9]+");
			break;
		/**
		 * case IP: p = Pattern.compile("[0-9]+([.][0-9]+)*") ; break;
		 **/
		}
		Matcher m = p.matcher(field.getText());

		if (!m.matches() && !type.equals(FieldType.USERNAME) && !type.equals(FieldType.PASSWORD)
				&& !type.equals(FieldType.IP)) {
			message.showMessageDialog(null,
					"Le champ " + type.name().toLowerCase() + " ne repecte pas le critère demandé", "Erreur",
					JOptionPane.ERROR_MESSAGE);
		}

		if (!field.getText().equals("") && !(field instanceof JPasswordField)) {

			return field.getText();
		} else if (type.equals(FieldType.PASSWORD)) {
			JPasswordField passwordField = (JPasswordField) field;
			if (!String.valueOf(passwordField.getPassword()).equals("")) {
				return String.valueOf(passwordField.getPassword());
			}
		} else {
			message.showMessageDialog(null, "Le champ " + type.name().toLowerCase() + " n'est pas renseigné", "Erreur",
					JOptionPane.ERROR_MESSAGE);
		}
		return null;

	}

}