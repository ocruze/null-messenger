package window.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
<<<<<<< HEAD:src/view/GUI.java
import javax.swing.JLabel;
import javax.swing.JPanel;
=======
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import window.presenter.IPresenter;
import window.presenter.PresenterConnection;

import java.awt.Color;
import java.awt.Button;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
>>>>>>> devWindow:src/window/view/ViewConnection.java
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
<<<<<<< HEAD:src/view/GUI.java
import javax.swing.border.EmptyBorder;
=======
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
>>>>>>> devWindow:src/window/view/ViewConnection.java

public class ViewConnection extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JTextField ipField;
	private JPasswordField passwordField;
<<<<<<< HEAD:src/view/GUI.java
	private JPasswordField passwordField_1;

	int xx, xy;
=======
	private JTextField portField;
	public PresenterConnection presenter;
	
	public enum FieldType{
		USERNAME,PASSWORD,IP,PORT
	}
	
	int xx,xy;
>>>>>>> devWindow:src/window/view/ViewConnection.java

	/**
	 * Launch the application.
	 */
	/**
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewConnection frame = new ViewConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} **/

	public ViewConnection() {
		init();
		this.setUndecorated(true);
		this.setVisible(true);
	}
<<<<<<< HEAD:src/view/GUI.java

	public GUI() {
=======
	
	public void setPresenter(PresenterConnection presenter) 
	{
	        this.presenter = presenter;
	}
	public void init() {
>>>>>>> devWindow:src/window/view/ViewConnection.java
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
<<<<<<< HEAD:src/view/GUI.java
				int y = arg0.getYOnScreen();
				GUI.this.setLocation(x - xx, y - xy);
=======
	            int y = arg0.getYOnScreen();
	            ViewConnection.this.setLocation(x - xx, y - xy);  
>>>>>>> devWindow:src/window/view/ViewConnection.java
			}
		});
		label.setBounds(-38, 0, 420, 290);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(ViewConnection.class.getResource("/window/image/Chatrooms.png")));
		panel.add(label);

		JLabel lblWeGotYou = new JLabel(" TO CHAT ROOM");
		lblWeGotYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeGotYou.setForeground(new Color(240, 248, 255));
		lblWeGotYou.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblWeGotYou.setBounds(57, 343, 223, 27);
		panel.add(lblWeGotYou);
<<<<<<< HEAD:src/view/GUI.java

		Button button = new Button("SignUp");
		button.setForeground(Color.WHITE);
		button.setBackground(Color.BLUE);
		button.setBounds(395, 363, 283, 36);
		contentPane.add(button);

		textField = new JTextField();
		textField.setBounds(395, 83, 283, 36);
		contentPane.add(textField);
		textField.setColumns(10);

=======
		
		Button loginButton = new Button("Login");
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(Color.BLUE);
		loginButton.setBounds(395, 363, 283, 36);
		loginButton.addActionListener((ActionEvent e) ->
        {
        	try {
        	 	presenter.setPassword(getValueField(passwordField, FieldType.PASSWORD));
            	presenter.setIp(getValueField(ipField, FieldType.IP));
            	presenter.setUsername(getValueField(usernameField, FieldType.USERNAME));
            	presenter.setPort(Integer.parseInt(getValueField(portField, FieldType.PORT)));
                presenter.login();
        	} catch (Exception e2) {
        		JOptionPane message = new JOptionPane();
        		message.showMessageDialog(null, "Une erreur s'est produite", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
        });
		contentPane.add(loginButton);
		
		usernameField = new JTextField();
		usernameField.setBounds(395, 83, 283, 36);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
>>>>>>> devWindow:src/window/view/ViewConnection.java
		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setBounds(395, 58, 181, 14);
		contentPane.add(lblUsername);

		JLabel adresse = new JLabel("IP ADDRESSE");
		adresse.setBounds(395, 132, 210, 14);
		contentPane.add(adresse);
<<<<<<< HEAD:src/view/GUI.java

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(395, 157, 283, 36);
		contentPane.add(textField_1);

=======
		
		ipField = new JTextField();
		ipField.setColumns(10);
		ipField.setBounds(395, 157, 283, 36);
		contentPane.add(ipField);
		
>>>>>>> devWindow:src/window/view/ViewConnection.java
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(395, 204, 96, 14);
		contentPane.add(lblPassword);

		JLabel port = new JLabel("PORT");
		port.setBounds(395, 275, 133, 14);
		contentPane.add(port);

		passwordField = new JPasswordField();
		passwordField.setBounds(395, 229, 283, 36);
		contentPane.add(passwordField);
<<<<<<< HEAD:src/view/GUI.java

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(395, 293, 283, 36);
		contentPane.add(passwordField_1);

=======
		
		portField = new JTextField();
		portField.setBounds(395, 293, 283, 36);
		contentPane.add(portField);
		
>>>>>>> devWindow:src/window/view/ViewConnection.java
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
	
	
	public String getValueField(JTextField field, FieldType type) throws Exception {
		//String value = "";
		
		JOptionPane message = new JOptionPane();
		Pattern p =  Pattern.compile("");
		switch(type) {
		case PORT :
			p = Pattern.compile("[0-9]+") ; 
			break;
		case IP:
			p = Pattern.compile("[0-9]+([.][0-9]+)*") ; 
			break;
		}
		Matcher m = p.matcher(field.getText()); 

		if(!m.matches() && !type.equals(FieldType.USERNAME) && !type.equals(FieldType.PASSWORD)) {
			message.showMessageDialog(null, "Le champ "+ type.name().toLowerCase()+" ne repecte pas le critère demandé", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	
		if(!field.getText().equals("") && !(field instanceof JPasswordField)) {
			
			return field.getText();
		}
		else if(type.equals(FieldType.PASSWORD)) {
			JPasswordField passwordField = (JPasswordField)field;
			if(!String.valueOf(passwordField.getPassword()).equals("")) {
				return String.valueOf(passwordField.getPassword());
			}
		}
		else {
			message.showMessageDialog(null, "Le champ "+ type.name().toLowerCase()+" n'est pas renseigné", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
		return null;

	}
}