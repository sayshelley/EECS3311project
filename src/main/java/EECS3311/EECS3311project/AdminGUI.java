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

public class AdminGUI {
	private JFrame AdminFrame;
	private JTextField accountField;
	private JPasswordField passwordField;
	private JTextField fnameField;
	private JTextField lnameField;
	private JButton add;
	private JButton remove;
	
	private Controller controller;
	
	public AdminGUI(Controller controller) {
		this.controller = controller;
		initComponent();
		initListener();
	}
	
	public void initComponent() {
		AdminFrame = new JFrame("Login");
		AdminFrame.setSize(400, 300);
		accountField = new JTextField();
		passwordField = new JPasswordField();
		fnameField = new JTextField();
		lnameField = new JTextField();
		add = new JButton("Add");
		remove = new JButton("Remove");
		JLabel accountLabel = new JLabel("E-mail: ");
		JLabel passwordLabel = new JLabel("Password: ");
		JLabel fnameLabel = new JLabel("Firstname: ");
		JLabel lnameLabel = new JLabel("Lastname: ");
		JLabel adminLabel = new JLabel("Administrator Management", JLabel.CENTER);
		adminLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		AdminFrame.setLayout(gridBagLayout);
		AdminFrame.add(adminLabel);
		AdminFrame.add(accountLabel);
		AdminFrame.add(accountField);
		AdminFrame.add(passwordLabel);
		AdminFrame.add(passwordField);
		AdminFrame.add(fnameLabel);
		AdminFrame.add(fnameField);
		AdminFrame.add(lnameLabel);
		AdminFrame.add(lnameField);
		AdminFrame.add(add);
		AdminFrame.add(remove);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridwidth=6;
		constraints.gridheight=4;
		constraints.insets = new Insets(5, 5, 25, 5);
        gridBagLayout.setConstraints(adminLabel, constraints);
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
        gridBagLayout.setConstraints(add, constraints);
		constraints.gridx=4;
		constraints.gridy=8;
		constraints.gridwidth=1;
		constraints.gridheight=1;
        gridBagLayout.setConstraints(remove, constraints);
        AdminFrame.setVisible(true);
        AdminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void initListener() {
		add.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String account = accountField.getText();
				String password = new String(passwordField.getPassword());
				String fname = new String(fnameField.getText());
				String lname = new String(lnameField.getText());
				if (account.trim().equals("") | password.trim().equals("") | fname.trim().equals("") | lname.trim().equals("")) {
					JOptionPane.showMessageDialog(AdminFrame, "Empty property is not allowed!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					boolean result = controller.register(account, password, fname, lname, 2);
					if (result) {
						JOptionPane.showMessageDialog(AdminFrame, "New parking enforcement officer is registered");
						accountField.setText("");
						passwordField.setText("");
						fnameField.setText("");
						lnameField.setText("");
					} else {
						JOptionPane.showMessageDialog(AdminFrame, "Register new parking enforcement officer failed", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		remove.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String account = accountField.getText();
				if (account.equals("")) {
					JOptionPane.showMessageDialog(AdminFrame, "Empty E-mail is not allowed!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					boolean result = controller.delete(account, 2);
					if (result) {
						JOptionPane.showMessageDialog(AdminFrame, "Parking enforcement officer is deleted");
						accountField.setText("");
					} else {
						JOptionPane.showMessageDialog(AdminFrame, "Delete new parking enforcement officer failed", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
}
