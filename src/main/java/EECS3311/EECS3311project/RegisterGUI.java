package EECS3311.EECS3311project;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterGUI {
	private JFrame registerFrame;
	private JTextField accountField;
	private JPasswordField passwordField;
	private JTextField fnameField;
	private JTextField lnameField;
	private JButton register;
	private JButton login;
	
	private Controller controller;
	
	public RegisterGUI(Controller controller) {
		this.controller = controller;
		initComponent();
		initListener();
	}
	
	public void initComponent() {
		registerFrame = new JFrame("SIGN UP");
		registerFrame.setSize(400, 300);
		accountField = new JTextField();
		passwordField = new JPasswordField();
		fnameField = new JTextField();
		lnameField = new JTextField();
		register = new JButton("SIGN UP");
		login = new JButton("SIGN IN");
		JLabel accountLabel = new JLabel("E-mail: ");
		JLabel passwordLabel = new JLabel("Password: ");
		JLabel fnameLabel = new JLabel("Firstname: ");
		JLabel lnameLabel = new JLabel("Lastname: ");
		JLabel customerLabel = new JLabel("Customer Registration", JLabel.CENTER);
		customerLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		registerFrame.setLayout(gridBagLayout);
		registerFrame.add(customerLabel);
		registerFrame.add(accountLabel);
		registerFrame.add(accountField);
		registerFrame.add(passwordLabel);
		registerFrame.add(passwordField);
		registerFrame.add(fnameLabel);
		registerFrame.add(fnameField);
		registerFrame.add(lnameLabel);
		registerFrame.add(lnameField);
		registerFrame.add(register);
		registerFrame.add(login);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridwidth=6;
		constraints.gridheight=4;
		constraints.insets = new Insets(5, 5, 25, 5);
        gridBagLayout.setConstraints(customerLabel, constraints);
		constraints.gridx=1;
		constraints.gridy=4;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		constraints.insets = new Insets(5, 5, 5, 5);
        gridBagLayout.setConstraints(accountLabel, constraints);
		constraints.gridx=2;
		constraints.gridy=4;
		constraints.gridwidth=3;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(accountField, constraints);
		constraints.gridx=1;
		constraints.gridy=5;
		constraints.gridwidth=1;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(passwordLabel, constraints);
		constraints.gridx=2;
		constraints.gridy=5;
		constraints.gridwidth=3;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(passwordField, constraints);
		constraints.gridx=1;
		constraints.gridy=6;
		constraints.gridwidth=1;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(fnameLabel, constraints);
		constraints.gridx=2;
		constraints.gridy=6;
		constraints.gridwidth=3;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(fnameField, constraints);
		constraints.gridx=1;
		constraints.gridy=7;
		constraints.gridwidth=1;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(lnameLabel, constraints);
		constraints.gridx=2;
		constraints.gridy=7;
		constraints.gridwidth=3;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(lnameField, constraints);
		constraints.gridx=2;
		constraints.gridy=8;
		constraints.gridwidth=2;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(register, constraints);
		constraints.gridx=4;
		constraints.gridy=8;
		constraints.gridwidth=1;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(login, constraints);
        registerFrame.setVisible(true);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void initListener() {
		register.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String account = accountField.getText();
				String password = new String(passwordField.getPassword());
				String fname = new String(fnameField.getText());
				String lname = new String(lnameField.getText());
				if (account.trim().equals("") | password.trim().equals("") | fname.trim().equals("") | lname.trim().equals("")) {
					JOptionPane.showMessageDialog(registerFrame, "Empty property is not allowed!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					boolean result = controller.register(account, password, fname, lname, 3);
					if (result) {
						JOptionPane.showMessageDialog(registerFrame, "New customer is registered");
						registerFrame.dispose();
						new LoginGUI(controller);
					} else {
						JOptionPane.showMessageDialog(registerFrame, "Register new customer failed", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		login.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				registerFrame.dispose();
				new LoginGUI(controller);
			}
		});
	}
}
