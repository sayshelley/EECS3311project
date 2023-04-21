package EECS3311.EECS3311project;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LoginGUI {
	private JFrame loginFrame;
	private JTextField accountField;
	private JPasswordField passwordField;
	private JButton login;
	private JButton register;
	private JRadioButton admin;
	private JRadioButton authority;
	private JRadioButton customer;
	
	private Controller controller;
	
	public LoginGUI(Controller controller) {
		this.controller = controller;
		initComponent();
		initListener();
	}
	
	public void initComponent() {
		loginFrame = new JFrame("SIGN IN");
		loginFrame.setSize(400, 300);
		accountField = new JTextField();
		passwordField = new JPasswordField();
		login = new JButton("SIGN IN");
		register = new JButton("SIGN UP");
		JLabel accountLabel = new JLabel("Account: ");
		JLabel passwordLabel = new JLabel("Password: ");
		JLabel welcomeLabel = new JLabel("Welcome to Parking System", JLabel.CENTER);
		welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		admin = new JRadioButton("admin");
		authority = new JRadioButton("authority");
		customer = new JRadioButton("customer");
		ButtonGroup group = new ButtonGroup();
		group.add(admin);
		group.add(authority);
		group.add(customer);
		customer.setSelected(true);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		loginFrame.setLayout(gridBagLayout);
		loginFrame.add(welcomeLabel);
		loginFrame.add(accountLabel);
		loginFrame.add(accountField);
		loginFrame.add(passwordLabel);
		loginFrame.add(passwordField);
		loginFrame.add(admin);
		loginFrame.add(authority);
		loginFrame.add(customer);
		loginFrame.add(login);
		loginFrame.add(register);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridwidth=6;
		constraints.gridheight=4;
		constraints.insets = new Insets(5, 5, 25, 5);
        gridBagLayout.setConstraints(welcomeLabel, constraints);
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
		constraints.gridx=2;
		constraints.gridy=6;
		constraints.gridwidth=1;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(admin, constraints);
		constraints.gridx=3;
		constraints.gridy=6;
		constraints.gridwidth=1;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(authority, constraints);
		constraints.gridx=4;
		constraints.gridy=6;
		constraints.gridwidth=1;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(customer, constraints);
		constraints.gridx=2;
		constraints.gridy=7;
		constraints.gridwidth=2;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(login, constraints);
		constraints.gridx=4;
		constraints.gridy=7;
		constraints.gridwidth=1;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(register, constraints);
        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void initListener() {
		login.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String account = accountField.getText();
				String password = new String(passwordField.getPassword());
				boolean result = controller.login(account, password, admin.isSelected() ? 1 : (authority.isSelected() ? 2 : 3));
				if (result) {
					loginFrame.dispose();
					if (admin.isSelected()) {
						new AdminGUI(controller);
					} else if (customer.isSelected()) {
						new CustomerGUI(account, controller);
					} else if (authority.isSelected()) {
						new OfficerGUI(account, controller);
					}
				} else {
					JOptionPane.showMessageDialog(loginFrame, "Sign in failed", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		register.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				loginFrame.dispose();
				new RegisterGUI(controller);
			}
		});
	}
}
